package com.janfranco.daggertutorial.di.module;

import android.app.Activity;
import android.content.Context;

import com.janfranco.daggertutorial.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

}
