package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class ProviderStatsOfBTCAddr {

    public static final String RESULT = "result";
    public static final String METHOD = "method";

    @Expose
    @SerializedName(RESULT)
    private Result result;

    @Expose
    @SerializedName(METHOD)
    private String method;

    private static class Result {

        public static final String STATS = "stats";
        public static final String PAYMENTS = "payments";
        public static final String ADDR = "addr";

        @Expose
        @SerializedName(STATS)
        private List<Stats> stats;

        @Expose
        @SerializedName(PAYMENTS)
        private List<Payment> payments;

        @Expose
        @SerializedName(ADDR)
        private String addr;

        public Result() {

        }

        @Override
        public String toString() {
            return "Result{" +
                    "stats=" + stats +
                    ", payments=" + payments +
                    ", addr='" + addr + '\'' +
                    '}';
        }

        private static class Stats {

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

            public Stats() {

            }

            @Override
            public String toString() {
                return "Stats{" +
                        "balance='" + balance + '\'' +
                        ", rejectedSpeed='" + rejectedSpeed + '\'' +
                        ", algo=" + algo +
                        ", acceptedString='" + acceptedString + '\'' +
                        '}';
            }
        }

        private static class Payment {

            public static final String AMOUNT = "amount";
            public static final String FEE = "fee";
            public static final String TXID = "TXID";
            public static final String TIME = "time";

            @Expose
            @SerializedName(AMOUNT)
            private String amount;

            @Expose
            @SerializedName(FEE)
            private String fee;

            @Expose
            @SerializedName(TXID)
            private String txid;

            @Expose
            @SerializedName(TIME)
            private String time;

            public Payment() {

            }

            @Override
            public String toString() {
                return "Payment{" +
                        "amount='" + amount + '\'' +
                        ", fee='" + fee + '\'' +
                        ", txid='" + txid + '\'' +
                        ", time='" + time + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ProviderStatsOfBTCAddr{" +
                "result=" + result +
                ", method='" + method + '\'' +
                '}';
    }
}
