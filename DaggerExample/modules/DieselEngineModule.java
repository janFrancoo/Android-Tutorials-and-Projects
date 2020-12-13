package com.janfranco.daggerexample.modules;

import com.janfranco.daggerexample.models.DieselEngine;
import com.janfranco.daggerexample.models.Engine;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DieselEngineModule {
    @Binds
    abstract Engine bindEngine(DieselEngine engine);
}
