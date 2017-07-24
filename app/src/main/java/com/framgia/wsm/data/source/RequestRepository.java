package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
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

    public Observable<Object> editFormRequestLeave(@NonNull int requestId,
            RequestLeaveRequest requestLeaveRequest) {
        return mRemoteDataSource.editFormRequestLeave(requestId, requestLeaveRequest);
    }

    public Observable<BaseResponse<List<OffRequest>>> getListRequestOff(QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestOff(queryRequest);
    }

    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestLateEarly(queryRequest);
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestOverTime(queryRequest);
    }

    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLeaveManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesLeavetManage(queryRequest);
    }

    public Observable<BaseResponse<List<OffRequest>>> getListRequestOffManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesOffManage(queryRequest);
    }

    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOvertimeManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesOvertimetManage(queryRequest);
    }

    public Observable<BaseResponse<LeaveRequest>> approveRequestLeave(ActionRequest actionRequest) {
        return mRemoteDataSource.approveRequestLeave(actionRequest);
    }

    public Observable<BaseResponse<OffRequest>> approveRequestOff(ActionRequest actionRequest) {
        return mRemoteDataSource.approveRequestOff(actionRequest);
    }

    public Observable<BaseResponse<RequestOverTime>> approveRequestOverTime(
            ActionRequest actionRequest) {
        return mRemoteDataSource.approveRequestOverTime(actionRequest);
    }

    public Observable<BaseResponse<LeaveRequest>> rejectRequestLeave(ActionRequest actionRequest) {
        return mRemoteDataSource.rejectRequestLeave(actionRequest);
    }

    public Observable<BaseResponse<OffRequest>> rejectRequestOff(ActionRequest actionRequest) {
        return mRemoteDataSource.rejectRequestOff(actionRequest);
    }

    public Observable<BaseResponse<RequestOverTime>> rejectRequestOverTime(
            ActionRequest actionRequest) {
        return mRemoteDataSource.rejectRequestOverTime(actionRequest);
    }
}
