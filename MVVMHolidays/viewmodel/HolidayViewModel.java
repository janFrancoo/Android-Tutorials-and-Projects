package com.janfranco.mvvmexample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.janfranco.mvvmexample.service.model.Holiday;
import com.janfranco.mvvmexample.service.repository.HolidayRepository;

import java.util.List;

public class HolidayViewModel extends ViewModel {

    private final HolidayRepository holidayRepository;
    private MutableLiveData<List<Holiday>> mutableLiveData;

    public HolidayViewModel() {
        holidayRepository = new HolidayRepository();
    }

    public LiveData<List<Holiday>> getHolidays() {
        if (mutableLiveData == null)
            mutableLiveData = holidayRepository.requestHolidays();

        return mutableLiveData;
    }

}
