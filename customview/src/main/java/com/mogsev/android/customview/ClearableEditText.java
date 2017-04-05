/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mogsev.android.customview;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mogsev.android.customview.textwatcher.CleanTextWatcher;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class ClearableEditText extends RelativeLayout {
    private static final String TAG = ClearableEditText.class.getSimpleName();

    private static final String BUNDLE_TEXT = "bundle_text";

    private EditText mEditTextClearable;
    private ImageButton mImageButtonClearable;

    public ClearableEditText(Context context) {
        super(context);
        initView();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initAttrs(attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initAttrs(attrs);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (BuildConfig.DEBUG) Log.d(TAG, "onSaveInstanceState");
        Bundle bundle = new Bundle();
        String str = mEditTextClearable.getText().toString();
        ClearableEditTextState state = new ClearableEditTextState(super.onSaveInstanceState(), str);
        bundle.putParcelable(ClearableEditTextState.STATE, state);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onRestoreInstanceState");
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            ClearableEditTextState localState = (ClearableEditTextState) bundle.getParcelable(ClearableEditTextState.STATE);
            String str = localState.getText();
            mEditTextClearable.setText(str);
            super.onRestoreInstanceState(localState.getSuperState());
            return;
        }
        // Stops a bug with the wrong state being passed to the super
        super.onRestoreInstanceState(BaseSavedState.EMPTY_STATE);
    }

    /**
     * Initialize view elements
     */
    private void initView() {
        Log.i(TAG, "initView");
        // view's state is enabled
        setSaveEnabled(true);

        // Set layout transition
        setLayoutTransition(new LayoutTransition());

        // create ImageButton
        mImageButtonClearable = new ImageButton(getContext());
        mImageButtonClearable.setPadding(
                getResources().getDimensionPixelSize(R.dimen.padding_dp_8),
                getResources().getDimensionPixelSize(R.dimen.padding_dp_8),
                getResources().getDimensionPixelSize(R.dimen.padding_dp_8),
                getResources().getDimensionPixelSize(R.dimen.padding_dp_8));
        mImageButtonClearable.setId(R.id.image_button_clearable);
        mImageButtonClearable.setImageResource(R.drawable.ic_clean_edit_text_gray_24dp);
        mImageButtonClearable.setVisibility(GONE);
        int attrs[] = new int[]{R.attr.actionBarItemBackground};
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs);
        int background = typedArray.getResourceId(0, 0);
        mImageButtonClearable.setBackgroundResource(background);
        typedArray.recycle();
        LayoutParams ibLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ibLayoutParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        ibLayoutParams.addRule(CENTER_VERTICAL, TRUE);
        ibLayoutParams.setMargins(4, 0, 0, 0);
        addView(mImageButtonClearable, ibLayoutParams);

        // create EditText
        mEditTextClearable = new EditText(getContext());
        mEditTextClearable.setMaxLines(1);
        mEditTextClearable.setInputType(InputType.TYPE_CLASS_TEXT);
        mEditTextClearable.setImeOptions(EditorInfo.IME_ACTION_DONE);
        LayoutParams etLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        etLayoutParams.addRule(LEFT_OF, mImageButtonClearable.getId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            etLayoutParams.addRule(START_OF, mImageButtonClearable.getId());
        }
        addView(mEditTextClearable, etLayoutParams);

        // set TextWatcher
        mEditTextClearable.addTextChangedListener(new CleanTextWatcher(mEditTextClearable, mImageButtonClearable));
    }

    private void initViewFromLayout() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.clearable_edit_text, this, true);
        mEditTextClearable = (EditText) findViewById(R.id.edit_text_clearable);
        mImageButtonClearable = (ImageButton) findViewById(R.id.image_button_clearable);

        // set TextWatcher
        mEditTextClearable.addTextChangedListener(new CleanTextWatcher(mEditTextClearable, mImageButtonClearable));
    }

    /**
     * Initialize attribute
     *
     * @param attrs AttributeSet
     */
    private void initAttrs(@NonNull AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ClearableEditText, 0, 0);
        try {
            String hint = typedArray.getString(R.styleable.ClearableEditText_android_hint);
            mEditTextClearable.setHint(hint);
        } finally {
            typedArray.recycle();
        }
    }

    public Editable getText() {
        return mEditTextClearable.getText();
    }

    public void setText(CharSequence cs) {
        mEditTextClearable.setText(cs);
    }

    protected static class ClearableEditTextState extends BaseSavedState {
        protected static final String STATE = "ClearableEditText.STATE";

        private String mText;

        public ClearableEditTextState(Parcelable superState, String str) {
            super(superState);
            mText = str;
        }

        public String getText(){
            return mText;
        }
    }
}
