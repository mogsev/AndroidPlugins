package com.mogsev.androidplugins.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mogsev.androidplugins.BuildConfig;
import com.mogsev.androidplugins.R;

public class CustomViewActivity extends AppCompatActivity {
    private static final String TAG = CustomViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_custom_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
