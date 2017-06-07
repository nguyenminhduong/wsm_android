package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
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

    public Observable<BaseResponse<SignInDataResponse>> login(String userName, String passWord) {
        //todo edit later
        User user = new User();
        user.setName("Nguyễn Văn A");
        user.setCode("B1228368");
        user.setEmail("nguyen.van.a@framgia.com");
        SignInDataResponse signInDataResponse = new SignInDataResponse();
        signInDataResponse.setUser(user);
        signInDataResponse.setAuthenToken("12345678");
        BaseResponse<SignInDataResponse> baseResponse =
                new BaseResponse<SignInDataResponse>(signInDataResponse) {
                };
        return Observable.just(baseResponse);
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
