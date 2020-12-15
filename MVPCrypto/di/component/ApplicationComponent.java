package com.janfranco.cryptomvp.di.component;

import android.content.Context;

import com.janfranco.cryptomvp.app.CryptoApplication;
import com.janfranco.cryptomvp.di.module.ContextModule;
import com.janfranco.cryptomvp.di.module.NetworkServiceModule;
import com.janfranco.cryptomvp.di.qualifier.ApplicationContext;
import com.janfranco.cryptomvp.di.scope.ApplicationScope;
import com.janfranco.cryptomvp.service.network.APIInterface;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, NetworkServiceModule.class})
public interface ApplicationComponent {
    APIInterface getAPIInterface();

    @ApplicationContext
    Context getContext();

    void injectApplication(CryptoApplication application);
}
