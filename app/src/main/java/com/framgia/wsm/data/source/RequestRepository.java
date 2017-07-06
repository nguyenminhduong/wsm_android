package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.StatusCode;
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

    public Observable<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId) {
        return mRemoteDataSource.deleteFormRequestOverTime(requestOverTimeId);
    }

    public Observable<BaseResponse<RequestOverTime>> editFormRequestOverTime(
            @NonNull RequestOverTime requestOverTime) {
        return mRemoteDataSource.editFormRequestOverTime(requestOverTime);
    }

    public Observable<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mRemoteDataSource.createFormRequestOff(requestOffRequest);
    }

    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mRemoteDataSource.deleteFormRequestOff(requestOffId);
    }

    public Observable<BaseResponse<OffRequest>> editFormRequestOff(
            @NonNull RequestOffRequest requestOffRequest) {
        return mRemoteDataSource.editFormRequestOff(requestOffRequest);
    }

    public Observable<Object> createFormRequestLeave(
            @NonNull RequestLeaveRequest requestLeaveRequest) {
        return mRemoteDataSource.createFormRequestLeave(requestLeaveRequest);
    }

    public Observable<Object> deleteFormRequestLeave(@NonNull int requestId) {
        return mRemoteDataSource.deleteFormRequestLeave(requestId);
    }

    public Observable<BaseResponse<LeaveRequest>> editFormRequestLeave(
            @NonNull LeaveRequest request) {
        return mRemoteDataSource.editFormRequestLeave(request);
    }

    public Observable<BaseResponse<List<OffRequest>>> getListRequestOff() {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOff(userId);
        List<OffRequest> requestOffs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OffRequest requestOff = new OffRequest();
            requestOff.setCreatedAt("16/06/2017");
            requestOff.setStatus(StatusCode.ACCEPT_CODE);
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom("16/06/2017");
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo("16/06/2017");
            requestOff.setStartDayHaveSalary(offHaveSalaryFrom);
            requestOff.setEndDayHaveSalary(offHaveSalaryTo);
            requestOffs.add(requestOff);
        }
        BaseResponse<List<OffRequest>> baseResponse =
                new BaseResponse<List<OffRequest>>(requestOffs) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly() {
        return mRemoteDataSource.getListRequestLateEarly();
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime() {
        return mRemoteDataSource.getListRequestOverTime();
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
            @NonNull int status, String time) {
        //TODO edit later
        //return mRemoteDataSource.getListRequestOverTimeStatusAndTime(userId, status, time);
        List<RequestOverTime> requestOverTimes = new ArrayList<>();
        RequestOverTime requestOverTime = new RequestOverTime();
        requestOverTime.setId(1);
        requestOverTime.setCreatedAt("23/06/2017");
        requestOverTime.setFromTime("21/06/2017 18:00");
        requestOverTime.setToTime("22/06/2017 19:00");
        requestOverTime.setStatus(StatusCode.PENDING_CODE);
        requestOverTime.setReason("Ko kịp tiến độ !!!");
        requestOverTime.setProject("WSM Android");
        requestOverTimes.add(requestOverTime);
        RequestOverTime requestOverTime3 = new RequestOverTime();
        requestOverTime3.setId(1);
        requestOverTime3.setCreatedAt("23/06/2017");
        requestOverTime3.setFromTime("21/06/2017 18:00");
        requestOverTime3.setToTime("22/06/2017 19:00");
        requestOverTime3.setStatus(StatusCode.REJECT_CODE);
        requestOverTime3.setReason("Ko kịp tiến độ !!!");
        requestOverTime3.setProject("WSM Android");
        requestOverTimes.add(requestOverTime3);
        BaseResponse<List<RequestOverTime>> baseResponse =
                new BaseResponse<List<RequestOverTime>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLeaveWithStatusAndTime(
            @NonNull int status, String time) {
        //TODO edit later
        //return mRemoteDataSource.getListRequestLeaveWithStatusAndTime(userId, status,time);
        List<LeaveRequest> requestOverTimes = new ArrayList<>();
        BaseResponse<List<LeaveRequest>> baseResponse =
                new BaseResponse<List<LeaveRequest>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<OffRequest>>> getListRequestOffWithStatusAndTime(
            @NonNull int status, String time) {
        //TODO edit later
        //return mRemoteDataSource.getListRequestOffWithStatusAndTime(userId, status, time);
        List<OffRequest> requestOffs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OffRequest requestOff = new OffRequest();
            requestOff.setCreatedAt("16/06/2017");
            requestOff.setStatus(StatusCode.ACCEPT_CODE);
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom("16/06/2017");
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo("16/06/2017");
            requestOff.setStartDayHaveSalary(offHaveSalaryFrom);
            requestOff.setEndDayHaveSalary(offHaveSalaryTo);
            requestOffs.add(requestOff);
        }
        BaseResponse<List<OffRequest>> baseResponse =
                new BaseResponse<List<OffRequest>>(requestOffs) {
                };
        return Observable.just(baseResponse);
    }
}
