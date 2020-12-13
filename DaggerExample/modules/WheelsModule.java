package com.janfranco.daggerexample.modules;

import com.janfranco.daggerexample.models.Rims;
import com.janfranco.daggerexample.models.Tires;
import com.janfranco.daggerexample.models.Wheels;

import dagger.Module;
import dagger.Provides;

@Module
public class WheelsModule {

    @Provides
    static Rims provideRims() {
        return new Rims();
    }

    @Provides
    static Tires provideTires() {
        Tires tires = new Tires();
        tires.inflate();
        return tires;
    }

    @Provides
    static Wheels provideWheels(Rims rims, Tires tires) {
        return new Wheels(rims, tires);
    }

}
