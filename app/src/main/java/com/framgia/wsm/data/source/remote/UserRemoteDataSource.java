package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.source.UserDataSource;
import com.framgia.wsm.data.source.remote.api.request.SignInRequest;
import com.framgia.wsm.data.source.remote.api.request.UpdateProfileRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.LeaveTypeResponse;
import com.framgia.wsm.data.source.remote.api.response.OffTypeResponse;
import com.framgia.wsm.data.source.remote.api.response.SignInDataResponse;
import com.framgia.wsm.data.source.remote.api.response.UserProfileResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import com.framgia.wsm.utils.RetrofitUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
    private static final String PARAM_NAME = "name";
    private static final String PARAM_BIRTHDAY = "birthday";

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

    @Override
    public Observable<List<LeaveType>> getListLeaveType() {
        return mWSMApi.getListLeaveType()
                .flatMap(
                        new Function<BaseResponse<LeaveTypeResponse>,
                                ObservableSource<List<LeaveType>>>() {
                            @Override
                            public ObservableSource<List<LeaveType>> apply(
                                    BaseResponse<LeaveTypeResponse> leaveTypeResponseBaseResponse)
                                    throws Exception {
                                if (leaveTypeResponseBaseResponse != null
                                        && leaveTypeResponseBaseResponse.getData() != null) {
                                    return Observable.just(
                                            leaveTypeResponseBaseResponse.getData().getLeaveType());
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<List<OffType>> getListOffType() {
        return mWSMApi.getListOffType()
                .flatMap(
                        new Function<BaseResponse<OffTypeResponse>,
                                ObservableSource<List<OffType>>>() {
                            @Override
                            public ObservableSource<List<OffType>> apply(
                                    BaseResponse<OffTypeResponse> leaveTypeResponseBaseResponse)
                                    throws Exception {
                                if (leaveTypeResponseBaseResponse != null
                                        && leaveTypeResponseBaseResponse.getData() != null) {
                                    return Observable.just(leaveTypeResponseBaseResponse.getData()
                                            .getOffDay()
                                            .getOffTypes());
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<BaseResponse<UserProfileResponse>> updateProfile(
            UpdateProfileRequest updateProfileRequest) {

        Map<String, RequestBody> params = new HashMap<>();
        params.put(PARAM_NAME, RetrofitUtils.toRequestBody(updateProfileRequest.getName()));
        params.put(PARAM_BIRTHDAY, RetrofitUtils.toRequestBody(updateProfileRequest.getBirthday()));

        return mWSMApi.updateProfile(params,
                RetrofitUtils.toMutilPartBody(updateProfileRequest.getAvatar()))
                .flatMap(
                        new Function<BaseResponse<UserProfileResponse>,
                                ObservableSource<BaseResponse<UserProfileResponse>>>() {

                            @Override
                            public ObservableSource<BaseResponse<UserProfileResponse>> apply(
                                    @NonNull BaseResponse<UserProfileResponse> userBaseResponse)
                                    throws Exception {
                                if (userBaseResponse != null) {
                                    return Observable.just(userBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }
}
