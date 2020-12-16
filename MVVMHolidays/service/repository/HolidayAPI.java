package com.janfranco.mvvmexample.service.repository;

import com.janfranco.mvvmexample.service.model.Holiday;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HolidayAPI {

    @GET("PublicHolidays/2019/us")
    Call<List<Holiday>> getHolidays();

}
