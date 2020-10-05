package com.janfranco.canvasdrawexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {

    private CustomPath drawPath;
    private Paint drawPaint;
    private Canvas canvas;
    private ArrayList<CustomPath> paths;
    private int strokeWidth;

    public DrawingView(Context context, AttributeSet attributes) {
        super(context, attributes);

        init();
    }

    private void init() {
        paths = new ArrayList<>();
        drawPath = new CustomPath();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(0);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (CustomPath path : paths) {
            drawPaint.setStrokeWidth(path.getStrokeWidth());
            canvas.drawPath(path, drawPaint);
        }

        canvas.drawPath(drawPath, drawPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                canvas.drawPath(drawPath, drawPaint);
                drawPath.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(eventX, eventY);
                canvas.drawPath(drawPath, drawPaint);
                drawPath.setStrokeWidth(strokeWidth);
                paths.add(drawPath);
                drawPath = new CustomPath();
            default:
                 return false;
        }

        invalidate();
        return true;
    }

    public void undo() {
        if (paths.isEmpty())
            return;

        paths.remove(paths.size() - 1);
        invalidate();
    }

    public float getStrokeWidth() {
        return drawPaint.getStrokeWidth();
    }

    public void setStrokeWidth(int strokeWidth) {
        drawPaint.setStrokeWidth(strokeWidth);
        this.strokeWidth = strokeWidth;
    }

    public void setColor(int color) {
        drawPaint.setColor(color);
    }

}
