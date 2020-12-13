package com.janfranco.daggerexample.modules;

import com.janfranco.daggerexample.models.Engine;
import com.janfranco.daggerexample.models.PetrolEngine;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PetrolEngineModule {
    @Binds
    abstract Engine bindEngine(PetrolEngine engine);
}
