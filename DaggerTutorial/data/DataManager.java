package com.janfranco.daggertutorial.data;

import android.content.Context;
import android.content.res.Resources;

import com.janfranco.daggertutorial.data.model.User;
import com.janfranco.daggertutorial.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final SharedPreferencesHelper mSharedPreferencesHelper;

    @Inject
    public DataManager(@ApplicationContext Context context, DbHelper dbHelper,
                       SharedPreferencesHelper sharedPreferencesHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mSharedPreferencesHelper = sharedPreferencesHelper;
    }

    public void saveAccessToken(String accessToken) {
        mSharedPreferencesHelper.put(SharedPreferencesHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken(){
        return mSharedPreferencesHelper.get(SharedPreferencesHelper.PREF_KEY_ACCESS_TOKEN, null);
    }

    public Long createUser(User user) throws Exception {
        return mDbHelper.insertUser(user);
    }

    public User getUser(Long userId) throws Resources.NotFoundException, NullPointerException {
        return mDbHelper.getUser(userId);
    }

}
