package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
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

    public Observable<Object> createFormRequestOff(@NonNull RequestOff requestOff) {
        return mRemoteDataSource.createFormRequestOff(requestOff);
    }

    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mRemoteDataSource.deleteFormRequestOff(requestOffId);
    }

    public Observable<BaseResponse<RequestOff>> editFormRequestOff(@NonNull RequestOff requestOff) {
        return mRemoteDataSource.editFormRequestOff(requestOff);
    }

    public Observable<Object> createFormRequestLeave(@NonNull Request request) {
        return mRemoteDataSource.createFormRequestLeave(request);
    }

    public Observable<Object> deleteFormRequestLeave(@NonNull int requestId) {
        return mRemoteDataSource.deleteFormRequestLeave(requestId);
    }

    public Observable<BaseResponse<Request>> editFormRequestLeave(@NonNull Request request) {
        return mRemoteDataSource.editFormRequestLeave(request);
    }

    public Observable<BaseResponse<List<RequestOff>>> getListRequestOff() {
        // TODO: Edit later
        // return mRemoteDataSource.getListRequestOff(userId);
        List<RequestOff> requestOffs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RequestOff requestOff = new RequestOff();
            requestOff.setCreatedAt("16/06/2017");
            requestOff.setStatus(0);
            RequestOff.OffHaveSalaryFrom offHaveSalaryFrom = new RequestOff.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom("16/06/2017");
            RequestOff.OffHaveSalaryTo offHaveSalaryTo = new RequestOff.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo("16/06/2017");
            requestOff.setStartDayHaveSalary(offHaveSalaryFrom);
            requestOff.setEndDayHaveSalary(offHaveSalaryTo);
            requestOffs.add(requestOff);
        }
        BaseResponse<List<RequestOff>> baseResponse =
                new BaseResponse<List<RequestOff>>(requestOffs) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<Request>>> getListRequestLateEarly() {
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
            request.setProject("WSM");
            request.setFromTime("19/06/2017");
            request.setToTime("20/06/2017");
            request.setLeaveType(leaveType);
            requests.add(request);
        }
        BaseResponse<List<Request>> baseResponse = new BaseResponse<List<Request>>(requests) {
        };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime() {
        // TODO: Edit later
        //return mRemoteDataSource.getListRequestOverTime();
        List<RequestOverTime> requestOverTimes = new ArrayList<>();
        RequestOverTime requestOverTime = new RequestOverTime();
        requestOverTime.setId(1);
        requestOverTime.setCreatedAt("23/06/2017");
        requestOverTime.setFromTime("21/06/2017 18:00");
        requestOverTime.setToTime("22/06/2017 19:00");
        requestOverTime.setStatus(1);
        requestOverTime.setReason("Ko kịp tiến độ !!!");
        requestOverTime.setProject("WSM Android");
        requestOverTimes.add(requestOverTime);
        RequestOverTime requestOverTime3 = new RequestOverTime();
        requestOverTime3.setId(1);
        requestOverTime3.setCreatedAt("23/06/2017");
        requestOverTime3.setFromTime("21/06/2017 18:00");
        requestOverTime3.setToTime("22/06/2017 19:00");
        requestOverTime3.setStatus(2);
        requestOverTime3.setReason("Ko kịp tiến độ !!!");
        requestOverTime3.setProject("WSM Android");
        requestOverTimes.add(requestOverTime3);
        RequestOverTime requestOverTime4 = new RequestOverTime();
        requestOverTime4.setId(1);
        requestOverTime4.setCreatedAt("23/08/2017");
        requestOverTime4.setFromTime("21/08/2017 18:00");
        requestOverTime4.setToTime("22/08/2017 19:00");
        requestOverTime4.setStatus(0);
        requestOverTime4.setReason("Ko kịp tiến độ !!!");
        requestOverTime4.setProject("WSM Android");
        requestOverTimes.add(requestOverTime4);
        RequestOverTime requestOverTime2 = new RequestOverTime();
        requestOverTime2.setId(1);
        requestOverTime2.setCreatedAt("23/09/2017");
        requestOverTime2.setFromTime("21/09/2017 18:00");
        requestOverTime2.setToTime("22/09/2017 19:00");
        requestOverTime2.setStatus(1);
        requestOverTime2.setReason("Ko kịp tiến độ !!!");
        requestOverTime2.setProject("WSM Android");
        requestOverTimes.add(requestOverTime2);
        BaseResponse<List<RequestOverTime>> baseResponse =
                new BaseResponse<List<RequestOverTime>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
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
        requestOverTime.setStatus(1);
        requestOverTime.setReason("Ko kịp tiến độ !!!");
        requestOverTime.setProject("WSM Android");
        requestOverTimes.add(requestOverTime);
        RequestOverTime requestOverTime3 = new RequestOverTime();
        requestOverTime3.setId(1);
        requestOverTime3.setCreatedAt("23/06/2017");
        requestOverTime3.setFromTime("21/06/2017 18:00");
        requestOverTime3.setToTime("22/06/2017 19:00");
        requestOverTime3.setStatus(2);
        requestOverTime3.setReason("Ko kịp tiến độ !!!");
        requestOverTime3.setProject("WSM Android");
        requestOverTimes.add(requestOverTime3);
        BaseResponse<List<RequestOverTime>> baseResponse =
                new BaseResponse<List<RequestOverTime>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<Request>>> getListRequestLeaveWithStatusAndTime(
            @NonNull int status, String time) {
        //TODO edit later
        //return mRemoteDataSource.getListRequestLeaveWithStatusAndTime(userId, status,time);
        List<Request> requestOverTimes = new ArrayList<>();
        Request requestOverTime = new Request();
        requestOverTime.setId(1);
        requestOverTime.setCreatedAt("23/06/2017");
        requestOverTime.setFromTime("21/06/2017 18:00");
        requestOverTime.setToTime("22/06/2017 19:00");
        requestOverTime.setStatus(1);
        requestOverTime.setReason("Ko kịp tiến độ !!!");
        requestOverTime.setProject("WSM Android");
        requestOverTimes.add(requestOverTime);
        Request requestOverTime3 = new Request();
        requestOverTime3.setId(1);
        requestOverTime3.setCreatedAt("23/06/2017");
        requestOverTime3.setFromTime("21/06/2017 18:00");
        requestOverTime3.setToTime("22/06/2017 19:00");
        requestOverTime3.setStatus(2);
        requestOverTime3.setReason("Ko kịp tiến độ !!!");
        requestOverTime3.setProject("WSM Android");
        requestOverTimes.add(requestOverTime3);
        BaseResponse<List<Request>> baseResponse =
                new BaseResponse<List<Request>>(requestOverTimes) {
                };
        return Observable.just(baseResponse);
    }

    public Observable<BaseResponse<List<RequestOff>>> getListRequestOffWithStatusAndTime(
            @NonNull int status, String time) {
        //TODO edit later
        //return mRemoteDataSource.getListRequestOffWithStatusAndTime(userId, status, time);
        List<RequestOff> requestOffs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            RequestOff requestOff = new RequestOff();
            requestOff.setCreatedAt("16/06/2017");
            requestOff.setStatus(0);
            RequestOff.OffHaveSalaryFrom offHaveSalaryFrom = new RequestOff.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom("16/06/2017");
            RequestOff.OffHaveSalaryTo offHaveSalaryTo = new RequestOff.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo("16/06/2017");
            requestOff.setStartDayHaveSalary(offHaveSalaryFrom);
            requestOff.setEndDayHaveSalary(offHaveSalaryTo);
            requestOffs.add(requestOff);
        }
        BaseResponse<List<RequestOff>> baseResponse =
                new BaseResponse<List<RequestOff>>(requestOffs) {
                };
        return Observable.just(baseResponse);
    }
}
