package com.janfranco.canvasdrawexample;

import android.graphics.Path;

public class CustomPath extends Path {

    private int strokeWidth;

    public CustomPath() {
        super();

        strokeWidth = 0;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

}
