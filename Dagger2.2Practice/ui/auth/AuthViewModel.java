package com.janfranco.daggerpractice.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.janfranco.daggerpractice.SessionManager;
import com.janfranco.daggerpractice.models.User;
import com.janfranco.daggerpractice.network.auth.AuthAPI;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthAPI authAPI;
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthAPI authAPI, SessionManager sessionManager) {
        this.authAPI = authAPI;
        this.sessionManager = sessionManager;
    }

    public void authenticateWithId(int userId) {
        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(authAPI.getUser(userId)
                .onErrorReturn(throwable -> {
                    User errorUser = new User();
                    errorUser.setId(-1);
                    return errorUser;
                })
                .map((Function<User, AuthResource<User>>) user -> {
                    if(user.getId() == -1){
                        return AuthResource.error("Could not authenticate", null);
                    }
                    return AuthResource.authenticated(user);
                })
                .subscribeOn(Schedulers.io()));
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }

}
