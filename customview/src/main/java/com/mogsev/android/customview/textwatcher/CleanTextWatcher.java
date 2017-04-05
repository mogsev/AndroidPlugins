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

package com.mogsev.android.customview.textwatcher;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mogsev.android.customview.BuildConfig;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class CleanTextWatcher implements TextWatcher {
    private static final String TAG = CleanTextWatcher.class.getSimpleName();

    private EditText mEditText;
    private ImageButton mImageButton;

    public CleanTextWatcher(@NonNull EditText editText, @NonNull ImageButton imageButton) {
        if (editText == null) {
            throw new NullPointerException("EditText cannot be null");
        }
        if (imageButton == null) {
            throw new NullPointerException("ImageButton cannot be null");
        }
        mEditText = editText;
        mImageButton = imageButton;

        // Initialize
        initView();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onTextChanged: " + start + " / " + before + " / " + count);
        }
        mImageButton.setVisibility(start == 0 && count == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * Initialize
     */
    private void initView() {
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.getText().clear();
            }
        });
    }
}
