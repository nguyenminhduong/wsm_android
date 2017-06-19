package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
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

    public Observable<Object> createFormRequestOverTime(@NonNull Request request) {
        return mRemoteDataSource.createFormRequestOverTime(request);
    }

    public Observable<BaseResponse<List<Request>>> getListRequestOff(@NonNull int userId) {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOff(userId);
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setCreatedAt("16/06/2017");
            request.setStatus(0);
            request.setFromTime("17/06/2017");
            request.setToTime("18/06/2017");
            requests.add(request);
        }
        BaseResponse<List<Request>> baseResponse = new BaseResponse<List<Request>>(requests) {
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

    public Observable<BaseResponse<List<Request>>> getListRequestOverTime(@NonNull int userId) {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOverTime(userId);
        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Request request = new Request();
            request.setCreatedAt("16/06/2017");
            request.setStatus(2);
            request.setFromTime("21/06/2017");
            request.setToTime("22/06/2017");
            requests.add(request);
        }
        BaseResponse<List<Request>> baseResponse = new BaseResponse<List<Request>>(requests) {
        };
        return Observable.just(baseResponse);
    }
}