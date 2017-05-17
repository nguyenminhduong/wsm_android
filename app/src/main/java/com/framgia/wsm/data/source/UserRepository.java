package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.local.sqlite.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRepository {

    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    public UserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }
}
