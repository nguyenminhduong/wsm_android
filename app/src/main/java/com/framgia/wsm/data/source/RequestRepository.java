package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.request.ResetPasswordRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Single;
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

    public Single<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mRemoteDataSource.createFormRequestOverTime(requestOverTime);
    }

    public Single<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId) {
        return mRemoteDataSource.deleteFormRequestOverTime(requestOverTimeId);
    }

    public Single<Object> editFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mRemoteDataSource.editFormRequestOverTime(requestOverTime);
    }

    public Single<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mRemoteDataSource.createFormRequestOff(requestOffRequest);
    }

    public Single<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mRemoteDataSource.deleteFormRequestOff(requestOffId);
    }

    public Single<Object> editFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mRemoteDataSource.editFormRequestOff(requestOffRequest);
    }

    public Single<Object> createFormRequestLeave(@NonNull RequestLeaveRequest requestLeaveRequest) {
        return mRemoteDataSource.createFormRequestLeave(requestLeaveRequest);
    }

    public Single<Object> deleteFormRequestLeave(@NonNull int requestId) {
        return mRemoteDataSource.deleteFormRequestLeave(requestId);
    }

    public Single<Object> editFormRequestLeave(@NonNull int requestId,
            RequestLeaveRequest requestLeaveRequest) {
        return mRemoteDataSource.editFormRequestLeave(requestId, requestLeaveRequest);
    }

    public Single<BaseResponse<List<OffRequest>>> getListRequestOff(QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestOff(queryRequest);
    }

    public Single<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestLateEarly(queryRequest);
    }

    public Single<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequestOverTime(queryRequest);
    }

    public Single<BaseResponse<List<LeaveRequest>>> getListRequestLeaveManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesLeavetManage(queryRequest);
    }

    public Single<BaseResponse<List<OffRequest>>> getListRequestOffManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesOffManage(queryRequest);
    }

    public Single<BaseResponse<List<RequestOverTime>>> getListRequestOvertimeManage(
            QueryRequest queryRequest) {
        return mRemoteDataSource.getListRequesOvertimetManage(queryRequest);
    }

    public Single<BaseResponse<ActionRequestResponse>> actionApproveRejectRequest(
            ActionRequest actionRequest) {
        return mRemoteDataSource.approveOrRejectRequest(actionRequest);
    }

    public Single<BaseResponse<List<ActionRequestResponse>>> approveAllRequest(
            ActionRequest actionRequest) {
        return mRemoteDataSource.approveAllRequest(actionRequest);
    }

    public Single<BaseResponse> sendEmail(String email) {
        return mRemoteDataSource.sendEmail(email);
    }

    public Single<Object> resetPassword(String tokenReset, String newPassword,
            String confirmPassword) {
        ResetPasswordRequest resetPasswordRequest =
                new ResetPasswordRequest(tokenReset, newPassword, confirmPassword);
        return mRemoteDataSource.resetPassword(resetPasswordRequest);
    }
}
