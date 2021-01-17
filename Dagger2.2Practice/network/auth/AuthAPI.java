package com.janfranco.daggerpractice.network.auth;

import com.janfranco.daggerpractice.models.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthAPI {
    @GET("users/{id}")
    Flowable<User> getUser(@Path("id") int id);
}
