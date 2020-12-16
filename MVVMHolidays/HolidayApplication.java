package com.janfranco.mvvmexample;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HolidayApplication extends Application {

    private static HolidayApplication mInstance;
    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized HolidayApplication getInstance() {
        return mInstance;
    }

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            OkHttpClient client = new okhttp3.OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://date.nager.at/api/v2/")
                    .build();
        }
        return retrofit;
    }

}
