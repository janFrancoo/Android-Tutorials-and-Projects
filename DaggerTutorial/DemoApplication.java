package com.janfranco.daggertutorial;

import android.app.Application;
import android.content.Context;

import com.janfranco.daggertutorial.data.DataManager;
import com.janfranco.daggertutorial.di.component.ApplicationComponent;
import com.janfranco.daggertutorial.di.component.DaggerApplicationComponent;
import com.janfranco.daggertutorial.di.module.ApplicationModule;

import javax.inject.Inject;

public class DemoApplication extends Application {

    protected ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent(){
        return applicationComponent;
    }

}
