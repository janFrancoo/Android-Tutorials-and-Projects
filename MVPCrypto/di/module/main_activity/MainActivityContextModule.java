package com.janfranco.cryptomvp.di.module.main_activity;

import android.content.Context;

import com.janfranco.cryptomvp.di.qualifier.ActivityContext;
import com.janfranco.cryptomvp.di.scope.ActivityScope;
import com.janfranco.cryptomvp.view.MainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityContextModule {

    public Context context;

    private final MainActivity mMainActivity;

    public MainActivityContextModule(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        context = mMainActivity;
    }

    @Provides
    @ActivityScope
    public MainActivity provideMainActivity() {
        return mMainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }

}
