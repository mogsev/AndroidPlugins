package com.mogsev.androidplugins.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mogsev.androidplugins.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author psinetron dev on 27.03.2015
 *         changed Eugene Sikaylo on 24.03.2017
 */
public class ViewPicker extends View {
    private static final String TAG = ViewPicker.class.getSimpleName();

    private OnChangeValueListener mListener;

    //Set values
    private Handler dpChangeHandler = new Handler();

    // reuse objects instead of creating new ones
    private Paint paintText = new Paint();
    private Paint shadowText = new Paint();
    private Paint lPBorders = new Paint();
    private Paint framePaint = new Paint();
    private Paint selValLightBorder = new Paint();
    private Paint selValTopBorder = new Paint();
    private Paint selValBottomBorder = new Paint();
    private Paint selectedTextPaint = new Paint();

    // colors
    private ColorHolder colorHolder;

    public int nowTopPosition = 0; //Current scroll position
    private int upMaxTopPosition = 0; //Max up scroll position
    private int maxTopPosition = 0; //Max scroll position
    private int maxValueHeight = 0; //Max of value height
    private List<ValueSize> dpValues = new ArrayList<>(); //Values
    private int canvasW = 0; //Current weight of canvas
    private int canvasH = 0; //Current height of canvas

    private int selectedValueId = 0; //Selected value id
    private boolean needAnimation = false; //animation
    private int needPosition = 0; //need to scroll position

    public int valpadding = 30; //vertical values padding

    private int scrollspeed = 0; //Impulse speed of scroll
    private boolean scrolltoup = false; //The direction of movement

    private float dpDownY = 0; //Touch down coordinates
    private float canvasDownY = 0; //canvas touch down coordinates

    private long actdownTime = 0; //Touch time

    public interface OnChangeValueListener {
        public void onEvent(int valueId);
    }

    public void setOnChangeValueListener(OnChangeValueListener eventListener) {
        mListener = eventListener;
    }

    public ViewPicker(Context context) {
        super(context);
        colorHolder = new ColorHolder(context);
    }

    public ViewPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        colorHolder = new ColorHolder(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasW = w;
        canvasH = h;
        maxValueHeight = (canvasH - (valpadding * 2)) / 2;
        nowTopPosition = 0;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            canvasDownY = motionEvent.getY();
            dpDownY = motionEvent.getY() - nowTopPosition;
            needAnimation = false;
            actdownTime = motionEvent.getEventTime();
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if ((int) (motionEvent.getY() - dpDownY) > maxTopPosition) {
                nowTopPosition = maxTopPosition;
                return true;
            }
            if ((int) (motionEvent.getY() - dpDownY) < upMaxTopPosition) {
                nowTopPosition = upMaxTopPosition;
                return true;
            }
            nowTopPosition = (int) (motionEvent.getY() - dpDownY);
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (canvasDownY > motionEvent.getY()) {
                scrolltoup = false;
            } else {
                scrolltoup = true;
            }

            if ((motionEvent.getEventTime() - actdownTime < 200) && (Math.abs(dpDownY - motionEvent.getY()) > 100)) {
                scrollspeed = (int) (1000 - (motionEvent.getEventTime() - actdownTime));
            } else {
                scrollspeed = 0;
                roundingValue();
            }
            needAnimation = true;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (dpValues.size() == 0) {
                return;
            }

            upMaxTopPosition = -(((dpValues.size() - 1) * (maxValueHeight + valpadding)));
            canvas.drawColor(Color.WHITE);

            if (needAnimation) {
                if (scrollspeed > 0) {
                    scrollspeed -= 30;
                    if (scrolltoup) {
                        int currentPos = nowTopPosition + 30;
                        if ((currentPos) > maxTopPosition) {
                            nowTopPosition = maxTopPosition;
                            scrollspeed = 0;
                            roundingValue();
                        } else {
                            nowTopPosition = currentPos;
                        }
                    }

                    if (!scrolltoup) {
                        int currentPos = nowTopPosition - 30;
                        if ((currentPos) < upMaxTopPosition) {
                            nowTopPosition = upMaxTopPosition;
                            scrollspeed = 0;
                            roundingValue();
                        } else {
                            nowTopPosition = currentPos;
                        }
                    }

                    if (scrollspeed <= 0) {
                        roundingValue();
                    }
                } else {
                    if (nowTopPosition > needPosition) {
                        nowTopPosition -= 20;
                        if (nowTopPosition < needPosition) {
                            nowTopPosition = needPosition;
                        }
                    }

                    if (nowTopPosition < needPosition) {
                        nowTopPosition += 20;
                        if (nowTopPosition > needPosition) {
                            nowTopPosition = needPosition;
                        }
                    }

                    if (nowTopPosition == needPosition) {
                        needAnimation = false;
                    }
                }
            }

            //Paste values to canvas
            for (int i = 0; i < dpValues.size() - 1; i++) {
                try {
                    float textX = (canvasW / 2) - (dpValues.get(i).dpWidth / 2);
                    float textY = ((maxValueHeight + valpadding) * i) + (valpadding + maxValueHeight) + (dpValues.get(i).dpHeight / 2) + nowTopPosition;

                    if (selectedValueId == i) {
                        paintText.setColor(colorHolder.viewPickerSelectedValue);
                        shadowText.setColor(colorHolder.viewPickerSelectedValueShadow);
                        canvas.drawText(dpValues.get(i).dpValue, textX, textY, shadowText);
                    } else {
                        paintText.setColor(colorHolder.viewPickerText);
                    }

                    paintText.setTextSize(dpValues.get(i).dpTextSize);
                    paintText.setAntiAlias(true);
                    canvas.drawText(dpValues.get(i).dpValue, textX, textY, paintText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Draw borders
            lPBorders.setColor(colorHolder.viewPickerBlackLines);
            canvas.drawLine(0, 0, 0, canvasH, lPBorders);
            canvas.drawLine(1, 0, 1, canvasH, lPBorders);
            canvas.drawLine(canvasW - 1, 0, canvasW - 1, canvasH, lPBorders);
            canvas.drawLine(canvasW - 2, 0, canvasW - 2, canvasH, lPBorders);
            canvas.drawLine(canvasW, 0, canvasW, canvasH, lPBorders);

            lPBorders.setColor(colorHolder.viewPickerGrayLines);
            canvas.drawRect(2, 0, 7, canvasH, lPBorders);
            canvas.drawRect(canvasW - 7, 0, canvasW - 2, canvasH, lPBorders);

            //Draw shadows
            framePaint.setShader(new LinearGradient(0, 0, 0, getHeight() / 5, colorHolder.viewPickerGradientStart, Color.TRANSPARENT, Shader.TileMode.CLAMP));
            canvas.drawPaint(framePaint);
            framePaint.setShader(new LinearGradient(0, getHeight(), 0, getHeight() - getHeight() / 5, colorHolder.viewPickerGradientStart, Color.TRANSPARENT, Shader.TileMode.CLAMP));
            canvas.drawPaint(framePaint);

            //Draw glass
            Path pathSelect = new Path();
            pathSelect.moveTo(0, canvasH / 2 - maxValueHeight / 2 - valpadding / 2);
            pathSelect.lineTo(canvasW, canvasH / 2 - maxValueHeight / 2 - valpadding / 2);
            pathSelect.lineTo(canvasW, canvasH / 2);
            pathSelect.lineTo(0, canvasH / 2);
            pathSelect.lineTo(0, canvasH / 2 - maxValueHeight / 2);
            Paint pathSelectPaint = new Paint();
            pathSelectPaint.setShader(new LinearGradient(0, 0, 0, maxValueHeight / 2, colorHolder.viewPickerSelectedValueLineG1, colorHolder.viewPickerSelectedValueLineG2, Shader.TileMode.CLAMP));
            canvas.drawPath(pathSelect, pathSelectPaint);

            pathSelect = new Path();
            pathSelect.moveTo(0, canvasH / 2);
            pathSelect.lineTo(canvasW, canvasH / 2);
            pathSelect.lineTo(canvasW, canvasH / 2 + maxValueHeight / 2 + valpadding / 2);
            pathSelect.lineTo(0, canvasH / 2 + maxValueHeight / 2 + valpadding / 2);
            pathSelect.lineTo(0, canvasH / 2);
            pathSelectPaint = new Paint();
            pathSelectPaint.setShader(new LinearGradient(0, 0, 0, maxValueHeight / 2, colorHolder.viewPickerSelectedValueLineG3, colorHolder.viewPickerSelectedValueLineG4, Shader.TileMode.CLAMP));
            canvas.drawPath(pathSelect, pathSelectPaint);

            //Draw glass shadow
            selValLightBorder.setColor(colorHolder.viewPickerSelectedValueBorder);
            selValTopBorder.setColor(colorHolder.viewPickerSelectedBorderTop);
            selValBottomBorder.setColor(colorHolder.viewPickerSelectedBorderBottom);
            canvas.drawLine(0, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, canvasW, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, selValLightBorder);
            canvas.drawLine(0, canvasH / 2 - maxValueHeight / 2 - valpadding / 2 + 1, canvasW, canvasH / 2 - maxValueHeight / 2 - valpadding / 2 + 1, selValTopBorder);
            canvas.drawLine(0, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, canvasW, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);
            canvas.drawLine(0, canvasH / 2 + maxValueHeight / 2 + valpadding / 2 - 1, canvasW, canvasH / 2 + maxValueHeight / 2 + valpadding / 2 - 1, selValBottomBorder);
            canvas.drawLine(0, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, 0, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);
            canvas.drawLine(1, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, 1, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);
            canvas.drawLine(canvasW - 1, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, canvasW - 1, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);
            canvas.drawLine(canvasW - 2, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, canvasW - 2, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);
            canvas.drawLine(canvasW, canvasH / 2 - maxValueHeight / 2 - valpadding / 2, canvasW, canvasH / 2 + maxValueHeight / 2 + valpadding / 2, selValLightBorder);

            //Draw selected value
            selectedTextPaint.setColor(colorHolder.viewPickerSelectedValue);
            Paint shadowText = new Paint();
            shadowText.setColor(colorHolder.viewPickerSelectedValueShadow);
            shadowText.setTextSize(dpValues.get(selectedValueId).dpTextSize);
            shadowText.setAntiAlias(true);
            canvas.drawText(dpValues.get(selectedValueId).dpValue, (canvasW / 2) - (dpValues.get(selectedValueId).dpWidth / 2), ((maxValueHeight + valpadding) * selectedValueId) + (valpadding + maxValueHeight) + (dpValues.get(selectedValueId).dpHeight / 2) + nowTopPosition + 2, shadowText);
            selectedTextPaint.setTextSize(dpValues.get(selectedValueId).dpTextSize);
            selectedTextPaint.setAntiAlias(true);
            canvas.drawText(dpValues.get(selectedValueId).dpValue, (canvasW / 2) - (dpValues.get(selectedValueId).dpWidth / 2), ((maxValueHeight + valpadding) * selectedValueId) + (valpadding + maxValueHeight) + (dpValues.get(selectedValueId).dpHeight / 2) + nowTopPosition, selectedTextPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //FPS to canvas
        this.postInvalidateDelayed(1000 / 50);
    }


    //Rounding values
    private void roundingValue() {
        Log.d("Rounding", "ROUNDING VALUE!");
        needPosition = (((nowTopPosition - maxTopPosition - (maxValueHeight / 2)) / (maxValueHeight + valpadding))) * (maxValueHeight + valpadding) + maxTopPosition;
        selectedValueId = Math.abs(((needPosition - valpadding - (maxValueHeight / 2)) / (maxValueHeight + valpadding)));
        try {
            Log.e("SELECTED VALUE", dpValues.get(selectedValueId).dpValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onSelected(selectedValueId);
    }

    //Return value
    public String getValue() {
        try {
            return dpValues.get(selectedValueId).dpValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void changetToValue(final int valueId) {
        selectedValueId = valueId;

        dpChangeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (canvasW == 0 || canvasH == 0) {
                    dpChangeHandler.postDelayed(this, 100);
                } else {
                    maxValueHeight = (canvasH - (valpadding * 2)) / 2;
                    needPosition = -((maxValueHeight + valpadding) * (valueId));
                    needAnimation = true;
                }
            }
        }, 100);

        onSelected(selectedValueId);
    }


    //On value selected Event
    protected void onSelected(int selectedId) {
        if (mListener != null) {
            mListener.onEvent(selectedId);
        }
    }


    //Return the value id
    public int getValueId() {
        try {
            return selectedValueId;
        } catch (Exception e) {
        }
        return -1;
    }


    private Handler dpHandler = new Handler();

    //Set values
    public void setValues(final String[] newvalues) {
        if (canvasW == 0 || canvasH == 0) {
            dpHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (canvasW == 0 || canvasH == 0) {
                        dpHandler.postDelayed(this, 100);
                    } else {
                        dpValues.clear();
                        for (int i = 0; i < newvalues.length; i++) {
                            dpValues.add(new ValueSize(newvalues[i], canvasW, canvasH));
                        }
                    }
                }
            }, 100);
        }
        dpValues.clear();
        for (int i = 0; i < newvalues.length; i++) {
            dpValues.add(new ValueSize(newvalues[i], canvasW, canvasH));
        }
    }

    private static class ValueSize {
        private static final String TAG = ValueSize.class.getSimpleName();

        public int dpWidth = 0;
        public int dpHeight = 0;
        public String dpValue = "";
        public int dpTextSize = 0;
        public int valpadding = 30;
        public int valinnerLeftpadding = 20;

        public ValueSize(String val, int canvasW, int canvasH) {
            try {
                int maxTextHeight = (canvasH - (valpadding * 2)) / 2;
                boolean sizeOK = false;
                dpValue = val;
                while (!sizeOK) {
                    Rect textBounds = new Rect();
                    Paint textPaint = new Paint();
                    dpTextSize++;
                    textPaint.setTextSize(dpTextSize);
                    textPaint.getTextBounds(val, 0, val.length(), textBounds);
                    if (textBounds.width() <= canvasW - (valinnerLeftpadding * 2) && textBounds.height() <= maxTextHeight) {
                        dpWidth = textBounds.width();
                        dpHeight = textBounds.height();
                    } else {
                        sizeOK = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ColorHolder {
        private static final String TAG = ColorHolder.class.getSimpleName();

        private int viewPickerSelectedValue;
        private int viewPickerSelectedValueShadow;
        private int viewPickerSelectedValueBorder;
        private int viewPickerSelectedBorderTop;
        private int viewPickerSelectedBorderBottom;
        private int viewPickerText;
        private int viewPickerBlackLines;
        private int viewPickerGrayLines;
        private int viewPickerGradientStart;
        private int viewPickerSelectedValueLineG1;
        private int viewPickerSelectedValueLineG2;
        private int viewPickerSelectedValueLineG3;
        private int viewPickerSelectedValueLineG4;

        public ColorHolder(@NonNull final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            viewPickerSelectedValue = ContextCompat.getColor(context, R.color.viewPickerSelectedValue);
            viewPickerSelectedValueShadow = ContextCompat.getColor(context, R.color.viewPickerSelectedValueShadow);
            viewPickerSelectedValueBorder = ContextCompat.getColor(context, R.color.viewPickerSelectedValueBorder);
            viewPickerSelectedBorderTop = ContextCompat.getColor(context, R.color.viewPickerSelectedBorderTop);
            viewPickerSelectedBorderBottom = ContextCompat.getColor(context, R.color.viewPickerSelectedBorderBottom);
            viewPickerText = ContextCompat.getColor(context, R.color.viewPickerText);
            viewPickerBlackLines = ContextCompat.getColor(context, R.color.viewPickerBlackLines);
            viewPickerGrayLines = ContextCompat.getColor(context, R.color.viewPickerGrayLines);
            viewPickerGradientStart = ContextCompat.getColor(context, R.color.viewPickerGradientStart);
            viewPickerSelectedValueLineG1 = ContextCompat.getColor(context, R.color.viewPickerSelectedValueLineG1);
            viewPickerSelectedValueLineG2 = ContextCompat.getColor(context, R.color.viewPickerSelectedValueLineG2);
            viewPickerSelectedValueLineG3 = ContextCompat.getColor(context, R.color.viewPickerSelectedValueLineG3);
            viewPickerSelectedValueLineG4 = ContextCompat.getColor(context, R.color.viewPickerSelectedValueLineG4);
        }
    }
}


