package com.janfranco.daggertutorial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.janfranco.daggertutorial.data.DataManager;
import com.janfranco.daggertutorial.data.model.User;
import com.janfranco.daggertutorial.di.component.ActivityComponent;
import com.janfranco.daggertutorial.di.component.DaggerActivityComponent;
import com.janfranco.daggertutorial.di.module.ActivityModule;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Inject
    DataManager mDataManager;

    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        createUser();
        getUser();
        mDataManager.saveAccessToken("ASDR12443JFDJF43543J543H3K543");

        String token = mDataManager.getAccessToken();
        Log.d(TAG, "onPostCreate: " + token);
    }

    private void createUser(){
        try {
            mDataManager.createUser(new User("JanFranco", "Istanbul"));
        } catch (Exception e) {
            Log.d(TAG, "createUser: " + e.getLocalizedMessage());
        }
    }

    private void getUser(){
        try {
            User user = mDataManager.getUser(1L);
            Log.d(TAG, "getUser: " + user);
        } catch (Exception e) {
            Log.d(TAG, "getUser: " + e.getLocalizedMessage());
        }
    }

}
