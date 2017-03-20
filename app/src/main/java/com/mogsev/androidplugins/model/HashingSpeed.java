package com.mogsev.androidplugins.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Eugene Sikaylo (mogsev@gmail.com)
 */
public class HashingSpeed {
    private static final String TAG = HashingSpeed.class.getSimpleName();

    @Expose
    @SerializedName("profitability_above_ltc")
    private String profitabilityAboveLtc;


    @Expose
    @SerializedName("price")
    private String price;

    @Expose
    @SerializedName("profitability_ltc")
    private String profitabilityLtc;

    @Expose
    @SerializedName("algo")
    @Algorithm.Type
    private int algo;

    @Expose
    @SerializedName("speed")
    private String speed;

    public HashingSpeed() {

    }

    public String getProfitabilityAboveLtc() {
        return profitabilityAboveLtc;
    }

    public String getPrice() {
        return price;
    }

    public String getProfitabilityLtc() {
        return profitabilityLtc;
    }

    @Algorithm.Type
    public int getAlgo() {
        return algo;
    }

    @Algorithm.Name
    public String getAlgorithmName() {
        return Algorithm.getName(this.algo);
    }

    public String getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashingSpeed that = (HashingSpeed) o;

        if (algo != that.algo) return false;
        if (profitabilityAboveLtc != null ? !profitabilityAboveLtc.equals(that.profitabilityAboveLtc) : that.profitabilityAboveLtc != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (profitabilityLtc != null ? !profitabilityLtc.equals(that.profitabilityLtc) : that.profitabilityLtc != null)
            return false;
        return speed != null ? speed.equals(that.speed) : that.speed == null;

    }

    @Override
    public int hashCode() {
        int result = profitabilityAboveLtc != null ? profitabilityAboveLtc.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (profitabilityLtc != null ? profitabilityLtc.hashCode() : 0);
        result = 31 * result + algo;
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HashingSpeed{" +
                "profitabilityAboveLtc='" + profitabilityAboveLtc + '\'' +
                ", price='" + price + '\'' +
                ", profitabilityLtc='" + profitabilityLtc + '\'' +
                ", algo='" + algo + '\'' +
                ", speed='" + speed + '\'' +
                '}';
    }
}
