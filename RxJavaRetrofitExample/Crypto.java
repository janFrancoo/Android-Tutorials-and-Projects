package com.janfranco.rxjavaretrofitexample;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Crypto {

    @SerializedName("ticker")
    public Ticker ticker;

    @SerializedName("timestamp")
    public Integer timestamp;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("error")
    public String error;

    public static class Ticker {
        @SerializedName("base")
        public String base;

        @SerializedName("target")
        public String target;

        @SerializedName("price")
        public String price;

        @SerializedName("volume")
        public String volume;

        @SerializedName("change")
        public String change;

        @SerializedName("markets")
        public List<Market> markets = null;
    }

    public static class Market {
        @SerializedName("market")
        public String market;

        @SerializedName("price")
        public String price;

        @SerializedName("volume")
        public Float volume;

        public String coinName;
    }

}
