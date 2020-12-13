package com.janfranco.daggertutorial.di.component;

import com.janfranco.daggertutorial.MainActivity;
import com.janfranco.daggertutorial.di.PerActivity;
import com.janfranco.daggertutorial.di.module.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
