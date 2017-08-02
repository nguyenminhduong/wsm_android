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
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by tri on 12/06/2017.
 */

public interface RequestDataSource {
    interface RemoteDataSource {
        Single<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime);

        Single<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest);

        Single<Object> createFormRequestLeave(@NonNull RequestLeaveRequest requestLeaveRequest);

        Single<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
                QueryRequest queryRequest);

        Single<BaseResponse<List<OffRequest>>> getListRequestOff(QueryRequest queryRequest);

        Single<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly(QueryRequest queryRequest);

        Single<Object> deleteFormRequestOff(@NonNull int requestOffId);

        Single<Object> editFormRequestOff(@NonNull RequestOffRequest requestOffRequest);

        Single<Object> editFormRequestOverTime(@NonNull RequestOverTime requestOverTime);

        Single<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId);

        Single<Object> deleteFormRequestLeave(@NonNull int requestLeaveId);

        Single<Object> editFormRequestLeave(@NonNull int requestId,
                RequestLeaveRequest requestLeave);

        Single<BaseResponse<List<LeaveRequest>>> getListRequesLeavetManage(
                QueryRequest queryRequest);

        Single<BaseResponse<List<RequestOverTime>>> getListRequesOvertimetManage(
                QueryRequest queryRequest);

        Single<BaseResponse<List<OffRequest>>> getListRequesOffManage(QueryRequest queryRequest);

        Single<BaseResponse<ActionRequestResponse>> approveOrRejectRequest(
                ActionRequest actionRequest);

        Single<BaseResponse<List<ActionRequestResponse>>> approveAllRequest(
                ActionRequest actionRequest);
    }
}
