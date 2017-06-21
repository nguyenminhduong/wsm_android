package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.UserDataSource;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {

    @Inject
    public UserRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse<SignInDataResponse>> login(String userName, String password) {
        SignInRequest signInRequest = new SignInRequest(userName, password);
        return mWSMApi.login(signInRequest)
                .flatMap(
                        new Function<BaseResponse<SignInDataResponse>,
                                ObservableSource<BaseResponse<SignInDataResponse>>>() {
                            @Override
                            public ObservableSource<BaseResponse<SignInDataResponse>> apply(@NonNull
                                    BaseResponse<SignInDataResponse> signInDataResponseBaseResponse)
                                    throws Exception {
                                if (signInDataResponseBaseResponse != null) {
                                    return Observable.just(signInDataResponseBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<BaseResponse<UserProfileResponse>> getUserProfile(int userId) {
        return mWSMApi.getUserProfile(userId)
                .flatMap(
                        new Function<BaseResponse<UserProfileResponse>,
                                ObservableSource<BaseResponse<UserProfileResponse>>>() {
                            @Override
                            public ObservableSource<BaseResponse<UserProfileResponse>> apply(
                                    @NonNull
                                            BaseResponse<UserProfileResponse>
                                            userProfileResponseBaseResponse)
                                    throws Exception {
                                if (userProfileResponseBaseResponse != null) {
                                    return Observable.just(userProfileResponseBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }
}
