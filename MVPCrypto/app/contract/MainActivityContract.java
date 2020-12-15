package com.janfranco.cryptomvp.app.contract;

import com.janfranco.cryptomvp.model.Data;

import java.util.List;

public interface MainActivityContract {
    interface View {
        void setup();
        void setupUI();

        void showData(Data coins);
        void showError(String errorMessage);

        void showComplete();
        void showProgress();
        void hideProgress();
    }

    interface Presenter {
        void onCreate();
        void loadData();
    }
}
