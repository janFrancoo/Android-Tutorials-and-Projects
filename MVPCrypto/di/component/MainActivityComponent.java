package com.janfranco.cryptomvp.di.component;

import android.content.Context;

import com.janfranco.cryptomvp.di.module.CoinListAdapterModule;
import com.janfranco.cryptomvp.di.module.main_activity.MainActivityMVPModule;
import com.janfranco.cryptomvp.di.qualifier.ActivityContext;
import com.janfranco.cryptomvp.di.scope.ActivityScope;
import com.janfranco.cryptomvp.view.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {MainActivityMVPModule.class, CoinListAdapterModule.class})
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();

    void injectActivity(MainActivity mainActivity);
}
