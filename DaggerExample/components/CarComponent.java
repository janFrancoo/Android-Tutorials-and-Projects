package com.janfranco.daggerexample.components;

import com.janfranco.daggerexample.MainActivity;
import com.janfranco.daggerexample.modules.PetrolEngineModule;
import com.janfranco.daggerexample.modules.WheelsModule;

import dagger.Component;

@Component(modules = {WheelsModule.class, PetrolEngineModule.class})
public interface CarComponent {
    void inject(MainActivity mainActivity);
}
