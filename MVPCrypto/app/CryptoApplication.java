package com.janfranco.cryptomvp.app;

import android.app.Activity;
import android.app.Application;

import com.janfranco.cryptomvp.di.component.ApplicationComponent;
import com.janfranco.cryptomvp.di.component.DaggerApplicationComponent;
import com.janfranco.cryptomvp.di.module.ContextModule;

public class CryptoApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        applicationComponent.injectApplication(this);
    }

    public static CryptoApplication get(Activity activity){
        return (CryptoApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
