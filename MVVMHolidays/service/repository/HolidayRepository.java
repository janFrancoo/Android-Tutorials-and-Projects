package com.janfranco.mvvmexample.service.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.janfranco.mvvmexample.HolidayApplication;
import com.janfranco.mvvmexample.service.model.Holiday;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolidayRepository {

    private static final String TAG = "HolidayRepository";

    public MutableLiveData<List<Holiday>> requestHolidays() {
        final MutableLiveData<List<Holiday>> mutableLiveData = new MutableLiveData<>();

        HolidayAPI holidayAPI = HolidayApplication.getRetrofitClient().create(HolidayAPI.class);
        holidayAPI.getHolidays().enqueue(new Callback<List<Holiday>>() {
            @Override
            public void onResponse(Call<List<Holiday>> call, Response<List<Holiday>> response) {
                if (response.isSuccessful() && response.body() != null)
                    mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Holiday>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

        return mutableLiveData;
    }

}
