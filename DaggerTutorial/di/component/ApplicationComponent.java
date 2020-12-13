package com.janfranco.daggertutorial.di.component;

import android.app.Application;
import android.content.Context;

import com.janfranco.daggertutorial.DemoApplication;
import com.janfranco.daggertutorial.data.DataManager;
import com.janfranco.daggertutorial.data.DbHelper;
import com.janfranco.daggertutorial.data.SharedPreferencesHelper;
import com.janfranco.daggertutorial.di.ApplicationContext;
import com.janfranco.daggertutorial.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(DemoApplication demoApplication);

    @ApplicationContext
    Context getContext();
    Application getApplication();
    DataManager getDataManager();
    SharedPreferencesHelper getPreferencesHelper();
    DbHelper getDbHelper();
}
