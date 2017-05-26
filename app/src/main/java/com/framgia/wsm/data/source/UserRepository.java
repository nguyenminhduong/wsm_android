package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.UserResponse;
import io.reactivex.Observable;

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

    public Observable<UserResponse> login(String userName, String passWord) {
        return mRemoteDataSource.login(userName, passWord);
    }

    public void saveUser(User user) {
        mLocalDataSource.saveUser(user);
    }

    public Observable<User> getUser() {
        return mLocalDataSource.getUser();
    }

    public void clearData() {
        mLocalDataSource.clearData();
    }
}
