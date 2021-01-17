package com.janfranco.daggerpractice.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.janfranco.daggerpractice.R;
import com.janfranco.daggerpractice.ui.main.MainActivity;
import com.janfranco.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private AuthViewModel viewModel;

    private EditText userIdET;
    private ProgressBar progressBar;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(AuthViewModel.class);

        userIdET = findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(v -> attemptLogin());

        progressBar = findViewById(R.id.progress_bar);

        setLogo();

        subscribeObservers();
    }

    private void setLogo() {
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    private void attemptLogin() {
        if (!TextUtils.isEmpty(userIdET.getText().toString()))
            viewModel.authenticateWithId(Integer.parseInt(userIdET.getText().toString()));
    }

    private void subscribeObservers() {
        viewModel.observeAuthState().observe(this, authResource -> {
            if (authResource != null) {
                switch (authResource.status) {
                    case LOADING:
                        showProgress(true);
                        break;
                    case AUTHENTICATED:
                        showProgress(false);
                        onLoginSuccess();
                        break;
                    case ERROR:
                        showProgress(false);
                        Toast.makeText(this, authResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    case NOT_AUTHENTICATED:
                        showProgress(false);
                        break;
                }
            }
        });
    }

    private void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showProgress(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}
