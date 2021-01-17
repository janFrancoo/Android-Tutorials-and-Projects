package com.janfranco.daggerpractice.network.main;

import com.janfranco.daggerpractice.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainAPI {
    @GET("posts")
    Flowable<List<Post>> getPostsFromUser(@Query("userId") int id);
}
