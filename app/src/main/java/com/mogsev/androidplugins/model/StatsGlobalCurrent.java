package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class StatsGlobalCurrent {
    private static final String TAG = StatsGlobalCurrent.class.getSimpleName();

    @Expose
    @SerializedName("result")
    private Stats result;

    @Expose
    @SerializedName("method")
    private String method;

    public StatsGlobalCurrent() {

    }

    public Stats getResult() {
        return result;
    }

    public String getMethod() {
        return method;
    }
}
