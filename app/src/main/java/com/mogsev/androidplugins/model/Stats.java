package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class Stats {
    private static final String TAG = Stats.class.getSimpleName();

    @Expose
    @SerializedName("stats")
    private List<HashingSpeed> stats;

    public Stats() {

    }

    public List<HashingSpeed> getStats() {
        return stats;
    }
}
