package com.mogsev.androidplugins.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mogsev.androidplugins.R;

/**
 * Created by Eugene Sikaylo on 24.03.2017
 */
public class CustomView extends View {
    private static final String TAG = CustomView.class.getSimpleName();

    private Paint paint = new Paint();

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PageIndicatorView);
        int count = typedArray.getInt(R.styleable.PageIndicatorView_count, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;

        int radius = y;

        paint.setColor(Color.WHITE);

        canvas.drawCircle(x, y, radius, paint);
    }
}
