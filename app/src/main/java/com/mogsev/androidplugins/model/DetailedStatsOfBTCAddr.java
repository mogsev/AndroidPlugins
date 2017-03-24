package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class DetailedStatsOfBTCAddr {

    public static final String METHOD = "method";
    public static final String RESULT = "result";

    @Expose
    @SerializedName(METHOD)
    private String method;

    @Expose
    @SerializedName(RESULT)
    private Result result;

    public DetailedStatsOfBTCAddr() {

    }

    private static class Result {

        public static final String ADDR = "addr";
        public static final String CURRENT = "current";

        @Expose
        @SerializedName(ADDR)
        private String addr;

        @Expose
        @SerializedName(CURRENT)
        private Current current;

        public Result() {

        }

        private static class Current {

            public static final String ALGO = "algo";
            public static final String NAME = "name";
            public static final String SUFFIX = "suffix";
            public static final String PROFITABILITY = "profitability";
            public static final String DATA = "data";

            @Expose
            @SerializedName(ALGO)
            private int algo;

            @Expose
            @SerializedName(NAME)
            private String name;

            @Expose
            @SerializedName(SUFFIX)
            private String suffix;

            @Expose
            @SerializedName(PROFITABILITY)
            private String profitability;

            @Expose
            @SerializedName(DATA)
            private Data data;

            public Current() {

            }

            /**
             * speed object can contain following fields:
             * a (accepted), rt (rejected target), rs (rejected stale),
             * rd (rejected duplicate) and ro (rejected other)
             * if fields are not present, speed is 0
             */
            private static class Data {

                public static final String ACCEPTED = "a";
                public static final String REJECTED_TARGET = "rt";
                public static final String REJECTED_STALE = "rs";
                public static final String REJECTED_DUPLICATE = "rd";
                public static final String REJECTED_OTHER = "ro";

                @Expose
                @SerializedName(ACCEPTED)
                private String accept;

                @Expose
                @SerializedName(REJECTED_TARGET)
                private String rejectedTarget;

                @Expose
                @SerializedName(REJECTED_STALE)
                private String rejectedStale;

                @Expose
                @SerializedName(REJECTED_DUPLICATE)
                private String rejectedDuplicate;

                @Expose
                @SerializedName(REJECTED_OTHER)
                private String rejectedOther;

                public Data() {

                }
            }
        }
    }
}
