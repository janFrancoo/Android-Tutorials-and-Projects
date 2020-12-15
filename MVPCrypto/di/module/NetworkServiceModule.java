package com.janfranco.cryptomvp.di.module;

import com.janfranco.cryptomvp.di.scope.ApplicationScope;
import com.janfranco.cryptomvp.service.network.APIInterface;

import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkServiceModule {

    @Provides
    @ApplicationScope
    APIInterface provideAPIInterface(Retrofit retrofit) {
        return retrofit.create(APIInterface.class);
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://pro-api.coinmarketcap.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

}
