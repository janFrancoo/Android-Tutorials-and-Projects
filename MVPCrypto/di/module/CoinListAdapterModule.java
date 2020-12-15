package com.janfranco.cryptomvp.di.module;

import com.janfranco.cryptomvp.di.module.main_activity.MainActivityContextModule;
import com.janfranco.cryptomvp.di.scope.ActivityScope;
import com.janfranco.cryptomvp.view.CoinListRVAdapter;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class CoinListAdapterModule {

    @Provides
    @ActivityScope
    public CoinListRVAdapter getCoinList() {
        return new CoinListRVAdapter();
    }

}
