package com.janfranco.cryptomvp.presenter;

import com.janfranco.cryptomvp.app.contract.MainActivityContract;
import com.janfranco.cryptomvp.model.Data;
import com.janfranco.cryptomvp.service.network.APIInterface;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    APIInterface apiInterface;
    MainActivityContract.View view;

    @Inject
    public MainActivityPresenter(APIInterface apiInterface, MainActivityContract.View view) {
        this.apiInterface = apiInterface;
        this.view = view;
    }

    @Override
    public void onCreate() {
        view.setup();
        view.setupUI();
    }

    @Override
    public void loadData() {
        view.showProgress();

        apiInterface.getData("7841cbe8-1bad-44e4-8dd3-5cc1734ec46b")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull Data cryptoCoins) {
                        view.showData(cryptoCoins);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getLocalizedMessage());
                        view.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        view.showComplete();
                        view.hideProgress();
                    }
                });
    }

}
