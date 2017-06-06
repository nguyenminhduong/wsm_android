package com.framgia.wsm.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserDataSource;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsApi;
import com.framgia.wsm.data.source.local.sharedprf.SharedPrefsKey;
import com.google.gson.Gson;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private UserDbHelper mDbHelper;
    private SQLiteDatabase mDatabase;
    private SharedPrefsApi mSharedPrefsApi;

    @Inject
    public UserLocalDataSource(@NonNull Context context, @NonNull SharedPrefsApi prefsApi) {
        mDbHelper = new UserDbHelper(context);
        mSharedPrefsApi = prefsApi;
    }

    public void openTransaction() {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.beginTransaction();
    }

    public void closeTransaction() {
        mDatabase.close();
    }

    public void openReadTransaction() {
        mDbHelper.getReadableDatabase();
    }

    @Override
    public void saveUser(User user) {
        String data = new Gson().toJson(user);
        mSharedPrefsApi.put(SharedPrefsKey.KEY_USER, data);
    }

    @Override
    public Observable<User> getUser() {
        String data = mSharedPrefsApi.get(SharedPrefsKey.KEY_USER, String.class);
        return Observable.just(new Gson().fromJson(data, User.class));
    }

    @Override
    public void clearData() {
        mSharedPrefsApi.clear();
    }
}
