package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class ProviderStats {

    public static final String BALANCE = "balance";
    public static final String REJECTED_SPEED = "rejected_speed";
    public static final String ALGO = "algo";
    public static final String ACCEPTED_SPEED = "accepted_speed";

    @Expose
    @SerializedName(BALANCE)
    private String balance;

    @Expose
    @SerializedName(REJECTED_SPEED)
    private String rejectedSpeed;

    @Algorithm.Type
    @Expose
    @SerializedName(ALGO)
    private int algo;

    @Expose
    @SerializedName(ACCEPTED_SPEED)
    private String acceptedString;

    public ProviderStats() {

    }

    public String getBalance() {
        return balance;
    }

    public String getRejectedSpeed() {
        return rejectedSpeed;
    }

    public int getAlgo() {
        return algo;
    }

    public String getAcceptedString() {
        return acceptedString;
    }
}
