package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.OffTypeDay;
import com.framgia.wsm.data.model.Setting;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.UserDataSource;
import com.framgia.wsm.data.source.remote.api.request.ChangePasswordRequest;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.request.UpdateProfileRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import com.framgia.wsm.utils.RetrofitUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.RequestBody;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class UserRemoteDataSource extends BaseRemoteDataSource
        implements UserDataSource.RemoteDataSource {
    private static final String PARAM_NAME = "user[name]";
    private static final String PARAM_BIRTHDAY = "user[birthday]";

    @Inject
    public UserRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse<SignInDataResponse>> login(String userName, String password,
            String deviceId) {
        SignInRequest signInRequest = new SignInRequest(userName, password, deviceId);
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
    public Observable<Object> logout() {
        return mWSMApi.logout();
    }

    @Override
    public Observable<BaseResponse<User>> getUserProfile(int userId) {
        return mWSMApi.getUserProfile(userId)
                .flatMap(new Function<BaseResponse<User>, ObservableSource<BaseResponse<User>>>() {
                    @Override
                    public ObservableSource<BaseResponse<User>> apply(
                            @NonNull BaseResponse<User> userBaseResponse) throws Exception {
                        if (userBaseResponse != null) {
                            return Observable.just(userBaseResponse);
                        }
                        return Observable.error(new NullPointerException());
                    }
                });
    }

    @Override
    public Observable<List<LeaveType>> getListLeaveType() {
        return mWSMApi.getListLeaveType()
                .flatMap(
                        new Function<BaseResponse<List<LeaveType>>,
                                ObservableSource<List<LeaveType>>>() {
                            @Override
                            public ObservableSource<List<LeaveType>> apply(
                                    @NonNull BaseResponse<List<LeaveType>> listBaseResponse)
                                    throws Exception {
                                if (listBaseResponse != null) {
                                    return Observable.just(listBaseResponse.getData());
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<List<OffType>> getListOffType() {
        return mWSMApi.getListOffType()
                .flatMap(new Function<BaseResponse<OffTypeDay>, ObservableSource<List<OffType>>>() {
                    @Override
                    public ObservableSource<List<OffType>> apply(
                            BaseResponse<OffTypeDay> offTypeDayBaseResponse) throws Exception {
                        if (offTypeDayBaseResponse != null
                                && offTypeDayBaseResponse.getData() != null) {
                            return Observable.just(offTypeDayBaseResponse.getData().getOffTypes());
                        }
                        return Observable.error(new NullPointerException());
                    }
                });
    }

    @Override
    public Observable<BaseResponse<OffTypeDay>> getRemainingDayOff() {
        return mWSMApi.getListOffType()
                .flatMap(
                        new Function<BaseResponse<OffTypeDay>,
                                ObservableSource<BaseResponse<OffTypeDay>>>() {
                            @Override
                            public ObservableSource<BaseResponse<OffTypeDay>> apply(
                                    @NonNull BaseResponse<OffTypeDay> offTypeDayBaseResponse)
                                    throws Exception {
                                if (offTypeDayBaseResponse != null
                                        && offTypeDayBaseResponse.getData() != null) {
                                    return Observable.just(offTypeDayBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<BaseResponse<User>> updateProfile(UpdateProfileRequest updateProfileRequest) {

        Map<String, RequestBody> params = new HashMap<>();
        params.put(PARAM_NAME, RetrofitUtils.toRequestBody(updateProfileRequest.getName()));
        params.put(PARAM_BIRTHDAY, RetrofitUtils.toRequestBody(updateProfileRequest.getBirthday()));

        return mWSMApi.updateProfile(updateProfileRequest.getUserId(), params,
                RetrofitUtils.toMutilPartBody(updateProfileRequest.getAvatar()));
    }

    @Override
    public Observable<Object> changePassword(ChangePasswordRequest changePasswordRequest) {
        return mWSMApi.changePassword(changePasswordRequest);
    }

    @Override
    public Single<BaseResponse<Setting>> getSetting() {
        return mWSMApi.getSetting();
    }

    @Override
    public Single<Object> changeSetting(Setting setting) {
        return mWSMApi.changeSetting(setting);
    }
}
