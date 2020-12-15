package com.janfranco.cryptomvp.di.module.main_activity;

import com.janfranco.cryptomvp.app.contract.MainActivityContract;
import com.janfranco.cryptomvp.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityMVPModule {

    private final MainActivityContract.View mView;

    public MainActivityMVPModule(MainActivityContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    MainActivityContract.View provideView() {
        return mView;
    }

}
