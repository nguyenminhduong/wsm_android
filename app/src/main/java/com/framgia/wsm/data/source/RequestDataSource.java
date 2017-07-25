package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by tri on 12/06/2017.
 */

public interface RequestDataSource {
    interface RemoteDataSource {
        Observable<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime);

        Observable<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest);

        Observable<Object> createFormRequestLeave(@NonNull RequestLeaveRequest requestLeaveRequest);

        Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
                QueryRequest queryRequest);

        Observable<BaseResponse<List<OffRequest>>> getListRequestOff(QueryRequest queryRequest);

        Observable<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly(
                QueryRequest queryRequest);

        Observable<Object> deleteFormRequestOff(@NonNull int requestOffId);

        Observable<BaseResponse<OffRequest>> editFormRequestOff(
                @NonNull RequestOffRequest requestOffRequest);

        Observable<BaseResponse<RequestOverTime>> editFormRequestOverTime(
                @NonNull RequestOverTime requestOverTime);

        Observable<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId);

        Observable<Object> deleteFormRequestLeave(@NonNull int requestLeaveId);

        Observable<Object> editFormRequestLeave(@NonNull int requestId,
                RequestLeaveRequest requestLeave);

        Observable<BaseResponse<List<LeaveRequest>>> getListRequesLeavetManage(
                QueryRequest queryRequest);

        Observable<BaseResponse<List<RequestOverTime>>> getListRequesOvertimetManage(
                QueryRequest queryRequest);

        Observable<BaseResponse<List<OffRequest>>> getListRequesOffManage(
                QueryRequest queryRequest);

        Observable<BaseResponse<ActionRequestResponse>> approveOrRejectRequest(
                ActionRequest actionRequest);
    }
}
