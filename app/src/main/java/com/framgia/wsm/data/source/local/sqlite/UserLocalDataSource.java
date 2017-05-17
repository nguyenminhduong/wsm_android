package com.framgia.wsm.data.source.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.UserDataSource;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserLocalDataSource implements UserDataSource.LocalDataSource {

    private UserDbHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    @Inject
    public UserLocalDataSource(@NonNull Context context) {
        mDbHelper = new UserDbHelper(context);
    }

    @Override
    public void openTransaction() {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.beginTransaction();
    }

    @Override
    public void closeTransaction() {
        mDatabase.close();
    }

    public void openReadTransaction() {
        mDbHelper.getReadableDatabase();
    }
}
