package com.janfranco.cryptomvp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.janfranco.cryptomvp.R;
import com.janfranco.cryptomvp.app.CryptoApplication;
import com.janfranco.cryptomvp.app.contract.MainActivityContract;
import com.janfranco.cryptomvp.di.component.ApplicationComponent;
import com.janfranco.cryptomvp.di.component.DaggerMainActivityComponent;
import com.janfranco.cryptomvp.di.component.MainActivityComponent;
import com.janfranco.cryptomvp.di.module.main_activity.MainActivityContextModule;
import com.janfranco.cryptomvp.di.module.main_activity.MainActivityMVPModule;
import com.janfranco.cryptomvp.di.qualifier.ActivityContext;
import com.janfranco.cryptomvp.di.qualifier.ApplicationContext;
import com.janfranco.cryptomvp.model.Data;
import com.janfranco.cryptomvp.presenter.MainActivityPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private ProgressBar mProgressBar;

    MainActivityComponent mainActivityComponent;

    @Inject
    @ApplicationContext
    Context context;

    @Inject
    @ActivityContext
    Context activityContext;

    @Inject
    MainActivityPresenter presenter;

    @Inject
    CoinListRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.onCreate();
        presenter.loadData();
    }

    @Override
    public void setup() {
        ApplicationComponent applicationComponent = CryptoApplication.get(this)
                .getApplicationComponent();

        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .mainActivityMVPModule(new MainActivityMVPModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectActivity(this);
    }

    @Override
    public void setupUI() {
        RecyclerView mCoinListRV = findViewById(R.id.coin_list_recycler_view);
        mCoinListRV.setLayoutManager(new LinearLayoutManager(activityContext));
        mCoinListRV.setAdapter(adapter);
        mProgressBar = findViewById(R.id.coin_list_progress_bar);
    }

    @Override
    public void showData(Data coins) {
        adapter.setCoins(coins.data);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() { }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

}
