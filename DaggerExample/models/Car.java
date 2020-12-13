package com.janfranco.daggerexample.models;

import android.util.Log;

import javax.inject.Inject;

public class Car {

    private static final String TAG = "Car";

    private final Engine engine;
    private final Remote remote;

    @Inject
    public Car(Engine engine, Wheels wheels, Remote remote) {
        this.engine = engine;
        this.remote = remote;
    }

    public void drive() {
        remote.enable();
        engine.start();
        Log.d(TAG, "drive: driving...");
    }

}
