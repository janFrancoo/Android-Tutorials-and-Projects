package com.janfranco.counter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.label);
        new CountDownTimer(10000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("Counter = " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                textView.setText("Finished!");
                Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }
}
