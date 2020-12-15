package com.janfranco.cryptomvp.di.module;

import android.content.Context;

import com.janfranco.cryptomvp.di.qualifier.ApplicationContext;
import com.janfranco.cryptomvp.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context provideContext() {
        return mContext;
    }

}
