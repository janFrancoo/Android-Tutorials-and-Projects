package com.janfranco.mvvmexample.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.janfranco.mvvmexample.R;
import com.janfranco.mvvmexample.service.model.Holiday;
import com.janfranco.mvvmexample.viewmodel.HolidayViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: called");

        HolidayViewModel holidayViewModel = new HolidayViewModel();
        holidayViewModel.getHolidays().observe(this, holidays -> {
            if (holidays != null && !holidays.isEmpty())
                for (Holiday holiday : holidays)
                    Log.d(TAG, "onCreate: " + holiday.getName() + " " + holiday.getDate());
        });
    }

}
