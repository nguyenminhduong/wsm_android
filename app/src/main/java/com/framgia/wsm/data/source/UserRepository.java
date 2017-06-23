package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;

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

    public Observable<SignInDataResponse> login(String userName, String passWord) {
        return mRemoteDataSource.login(userName, passWord)
                .flatMap(
                        new Function<BaseResponse<SignInDataResponse>,
                                ObservableSource<SignInDataResponse>>() {
                            @Override
                            public ObservableSource<SignInDataResponse> apply(@NonNull
                                    BaseResponse<SignInDataResponse> signInDataResponseBaseResponse)
                                    throws Exception {
                                if (signInDataResponseBaseResponse != null) {
                                    return Observable.just(
                                            signInDataResponseBaseResponse.getData());
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    public Observable<UserProfileResponse> getUserProfile(int userId) {
        return mRemoteDataSource.getUserProfile(userId)
                .flatMap(
                        new Function<BaseResponse<UserProfileResponse>,
                                ObservableSource<UserProfileResponse>>() {
                            @Override
                            public ObservableSource<UserProfileResponse> apply(@NonNull
                                    BaseResponse<UserProfileResponse>
                                    userProfileResponseBaseResponse)
                                    throws Exception {
                                if (userProfileResponseBaseResponse != null) {
                                    return Observable.just(
                                            userProfileResponseBaseResponse.getData());
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    public Observable<List<LeaveType>> getListLeaveType() {
        return mRemoteDataSource.getListLeaveType();
    }

    public Observable<List<OffType>> getListOffType() {
        return mRemoteDataSource.getListOffType();
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
