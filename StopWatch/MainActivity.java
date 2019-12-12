package com.janfranco.tutorialrunnablehandler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int time;
    Handler handler;
    Runnable runnable;
    TextView textView;
    Button startButton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = 0;
        handler = new Handler();
        textView = findViewById(R.id.textView);
        startButton = findViewById(R.id.button);
        stopButton = findViewById(R.id.button2);
    }

    public void start(View view){

        runnable = new Runnable() {
            @Override
            public void run() {
                time++;
                textView.setText("Time = " + time);
                handler.postDelayed(runnable, 1000);
            }
        };

        handler.post(runnable);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    public void stop(View view){
        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        handler.removeCallbacks(runnable);
        time = 0;
        textView.setText("Time = " + time);
    }
}
