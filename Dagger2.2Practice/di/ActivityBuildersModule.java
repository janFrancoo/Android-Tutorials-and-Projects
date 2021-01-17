package com.janfranco.daggerpractice.di;

import com.janfranco.daggerpractice.di.auth.AuthModule;
import com.janfranco.daggerpractice.di.auth.AuthScope;
import com.janfranco.daggerpractice.di.auth.AuthViewModelsModule;
import com.janfranco.daggerpractice.di.main.MainFragmentBuildersModule;
import com.janfranco.daggerpractice.di.main.MainModule;
import com.janfranco.daggerpractice.di.main.MainScope;
import com.janfranco.daggerpractice.di.main.MainViewModelsModule;
import com.janfranco.daggerpractice.ui.auth.AuthActivity;
import com.janfranco.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
