package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.RequestOffResponse;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tri on 12/06/2017.
 */

public class RequestRepository {
    private final RequestDataSource.RemoteDataSource mRemoteDataSource;

    public RequestRepository(RequestRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mRemoteDataSource.createFormRequestOverTime(requestOverTime);
    }

    public Observable<Object> createFormRequestOff(@NonNull RequestOff requestOff) {
        return mRemoteDataSource.createFormRequestOff(requestOff);
    }

    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mRemoteDataSource.deleteFormRequestOff(requestOffId);
    }

    public Observable<BaseResponse<RequestOffResponse>> editFormRequestOff(
            @NonNull RequestOff requestOff) {
        return mRemoteDataSource.editFormRequestOff(requestOff);
    }

    public Observable<Object> createFormRequestLeave(@NonNull Request request) {
        return mRemoteDataSource.createFormRequestLeave(request);
    }

    public Observable<BaseResponse<List<RequestOff>>> getListRequestOff(@NonNull int userId) {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOff(userId);
        List<RequestOff> requestOffs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RequestOff requestOff = new RequestOff();
            requestOff.setCreatedAt("16/06/2017");
            requestOffs.add(requestOff);
        }
        BaseResponse<List<RequestOff>> baseResponse =
                new BaseResponse<List<RequestOff>>(requestOffs) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<Request>>> getListRequestLateEarly(@NonNull int userId) {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestLateEarly(userId);
        List<Request> requests = new ArrayList<>();
        LeaveType leaveType = new LeaveType();
        leaveType.setName("In late (M)");
        leaveType.setCode("ILM");
        leaveType.setId(0);
        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setCreatedAt("16/06/2017");
            request.setStatus(1);
            request.setFromTime("19/06/2017");
            request.setToTime("20/06/2017");
            request.setLeaveType(leaveType);
            requests.add(request);
        }
        BaseResponse<List<Request>> baseResponse = new BaseResponse<List<Request>>(requests) {
        };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
            @NonNull int userId) {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOverTime(userId);
        List<RequestOverTime> requestOverTimes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RequestOverTime requestOverTime = new RequestOverTime();
            requestOverTime.setFromTime("21/06/2017");
            requestOverTime.setToTime("22/06/2017");
            requestOverTimes.add(requestOverTime);
        }
        BaseResponse<List<RequestOverTime>> baseResponse =
                new BaseResponse<List<RequestOverTime>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
    }
}
