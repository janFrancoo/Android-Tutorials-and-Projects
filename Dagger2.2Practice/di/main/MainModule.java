package com.janfranco.daggerpractice.di.main;

import com.janfranco.daggerpractice.network.main.MainAPI;
import com.janfranco.daggerpractice.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @MainScope
    @Provides
    static PostRecyclerAdapter provideAdapter() {
        return new PostRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainAPI provideMainAPI(Retrofit retrofit) {
        return retrofit.create(MainAPI.class);
    }
}
