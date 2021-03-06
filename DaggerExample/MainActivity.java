package com.janfranco.daggerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.janfranco.daggerexample.components.CarComponent;
import com.janfranco.daggerexample.components.DaggerCarComponent;
import com.janfranco.daggerexample.models.Car;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarComponent component = DaggerCarComponent.create();
        component.inject(this);
        car.drive();
    }

}
