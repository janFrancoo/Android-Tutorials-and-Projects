package com.janfranco.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeImg(View view){
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        imageView.setImageResource(R.drawable.maceo);
    }
}
