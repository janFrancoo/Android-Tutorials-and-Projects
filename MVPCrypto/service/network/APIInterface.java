package com.janfranco.cryptomvp.service.network;

import com.janfranco.cryptomvp.model.Data;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIInterface {

    @GET("cryptocurrency/listings/latest")
    Observable<Data> getData(@Header("X-CMC_PRO_API_KEY") String apiKey);

}
