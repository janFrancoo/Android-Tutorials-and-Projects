package com.janfranco.daggerpractice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.janfranco.daggerpractice.ui.auth.AuthActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, authResource -> {
            if (authResource != null) {
                switch (authResource.status) {
                    case LOADING:
                        break;
                    case AUTHENTICATED:
                        break;
                    case ERROR:
                        break;
                    case NOT_AUTHENTICATED:
                        navLoginScreen();
                        break;
                }
            }
        });
    }

    private void navLoginScreen() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

}
