package com.janfranco.daggerexample.models;

import android.util.Log;

import javax.inject.Inject;

public class Remote {

    private static final String TAG = "Remote";

    @Inject
    public Remote() { }
    
    public void enable() {
        Log.d(TAG, "enable: remote enabled");
    }
    
}
