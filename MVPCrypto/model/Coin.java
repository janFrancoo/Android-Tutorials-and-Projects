package com.janfranco.cryptomvp.model;

import com.google.gson.annotations.SerializedName;

public class Coin {
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("num_market_pairs")
    public String price;
}