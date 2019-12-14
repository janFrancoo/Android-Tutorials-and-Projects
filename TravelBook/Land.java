package com.janfranco.travelbook;

import androidx.annotation.NonNull;

public class Land {

    int id;
    String name;
    double latitude, longitude;

    public Land(int id, String name, double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
