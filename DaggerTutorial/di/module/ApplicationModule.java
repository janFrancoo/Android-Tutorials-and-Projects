package com.janfranco.daggertutorial.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.janfranco.daggertutorial.di.ApplicationContext;
import com.janfranco.daggertutorial.di.DbInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DbInfo
    String provideDatabaseName() {
        return "janfranco-dagger.db";
    }

    @Provides
    @DbInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return mApplication.getSharedPreferences("dagger-demo", Context.MODE_PRIVATE);
    }

}
