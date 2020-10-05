package com.janfranco.canvasdrawexample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    private int MODE_DRAW = 0;
    private int MODE_ERASE = 1;
    private int currentMode = MODE_DRAW;
    private int strokeWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        drawingView = findViewById(R.id.drawingView);

        SeekBar seekbar = findViewById(R.id.thicknessSB);
        seekbar.setMax(10);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingView.setStrokeWidth(progress);
                strokeWidth = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        final Button changeModeButton = findViewById(R.id.changeModeButton);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMode == MODE_DRAW) {
                    changeModeButton.setText(R.string.erase);
                    currentMode = MODE_ERASE;
                    drawingView.setColor(Color.WHITE);
                    drawingView.setStrokeWidth((int) drawingView.getStrokeWidth() * 3);
                } else {
                    changeModeButton.setText(R.string.draw);
                    currentMode = MODE_DRAW;
                    drawingView.setColor(Color.BLACK);
                    drawingView.setStrokeWidth(strokeWidth);
                }
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        Button colorPickButton = findViewById(R.id.colorPickButton);
        colorPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerPopUp();
            }
        });

        Button undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.undo();
            }
        });
    }

    private void openColorPickerPopUp() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View popUpView = inflater.inflate(R.layout.pop_up_color_pick, null);

        ColorPickerView colorPickerView = popUpView.findViewById(R.id.color_picker_view);
        colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int selectedColor) {
                drawingView.setColor(selectedColor);
            }
        });

        final PopupWindow popUp = new PopupWindow(popUpView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popUp.showAtLocation(popUpView, Gravity.START, 16, 0);
    }

    private void saveImage() {
        int width = drawingView.getWidth();
        int height = drawingView.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawingView.draw(canvas);

        // Send bitmap to server
    }

}
