package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestDataSource;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import com.framgia.wsm.utils.RequestType;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by tri on 12/06/2017.
 */

public class RequestRemoteDataSource extends BaseRemoteDataSource
        implements RequestDataSource.RemoteDataSource {

    private static final String PARAM_USER_NAME = "q[user_name_cont]";
    private static final String PARAM_FROM_TIME_REQUEST_OVERTIME = "q[from_time_or_end_time_gteq]";
    private static final String PARAM_TO_TIME_REQUEST_OVERTIME = "q[from_time_or_end_time_lteq]";
    private static final String PARAM_STATUS = "status";
    private static final String PARAM_GROUP_ID = "q[group_id_eq]";
    private static final String PARAM_WORKSPACE_ID = "q[workspace_id_eq]";
    private static final String PARAM_FROM_TIME_REQUEST_OFF =
            "q[off_have_salary_from][off_paid_from]";
    private static final String PARAM_TO_TIME_REQUEST_OFF = "q[off_have_salary_from][off_paid_to]";
    private static final String PARAM_FROM_TIME_REQUEST_LEAVE =
            "q[checkin_time_or_checkout_time_gteq]";
    private static final String PARAM_TO_TIME_REQUEST_LEAVE =
            "q[checkin_time_or_checkout_time_lteq]";
    private static final String PARAM_MONTH = "month";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_STATUS_PERSONAL = "q[status_eq]";

    @Inject
    public RequestRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Single<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mWSMApi.createFormRequestOverTime(requestOverTime);
    }

    @Override
    public Single<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mWSMApi.createFormRequestOff(requestOffRequest);
    }

    @Override
    public Single<Object> createFormRequestLeave(@NonNull RequestLeaveRequest requestLeaveRequest) {
        return mWSMApi.createFormRequestLeave(requestLeaveRequest);
    }

    @Override
    public Single<BaseResponse<List<RequestOverTime>>> getListRequestOverTime(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestOverTime(inputParamsRequestsPersonal(queryRequest));
    }

    @Override
    public Single<BaseResponse<List<OffRequest>>> getListRequestOff(QueryRequest queryRequest) {
        return mWSMApi.getListRequestOff(inputParamsRequestsPersonal(queryRequest));
    }

    @Override
    public Single<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestLeaves(inputParamsRequestsPersonal(queryRequest));
    }

    @Override
    public Single<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mWSMApi.deleteFormRequestOff(requestOffId);
    }

    @Override
    public Single<Object> editFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mWSMApi.editFormRequestOff(requestOffRequest.getRequestOff().getId(),
                requestOffRequest);
    }

    @Override
    public Single<Object> editFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mWSMApi.editFormRequestOverTime(requestOverTime.getId(), requestOverTime);
    }

    @Override
    public Single<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId) {
        return mWSMApi.deleteFormRequestOverTime(requestOverTimeId);
    }

    public Single<Object> deleteFormRequestLeave(@NonNull int requestLeaveId) {
        return mWSMApi.deleteFormRequestLeave(requestLeaveId);
    }

    @Override
    public Single<Object> editFormRequestLeave(@NonNull int requestId,
            RequestLeaveRequest requestLeave) {
        return mWSMApi.editFormRequestLeave(requestId, requestLeave);
    }

    @Override
    public Single<BaseResponse<List<LeaveRequest>>> getListRequesLeavetManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestLeaveManage(inputParamsRequestsManage(queryRequest));
    }

    @Override
    public Single<BaseResponse<List<RequestOverTime>>> getListRequesOvertimetManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestOvertimeManage(inputParamsRequestsManage(queryRequest));
    }

    @Override
    public Single<BaseResponse<List<OffRequest>>> getListRequesOffManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestOffManage(inputParamsRequestsManage(queryRequest));
    }

    @Override
    public Single<BaseResponse<ActionRequestResponse>> approveOrRejectRequest(
            ActionRequest actionRequest) {
        return mWSMApi.approveOrRejectRequest(actionRequest);
    }

    @Override
    public Single<BaseResponse<List<ActionRequestResponse>>> approveAllRequest(
            ActionRequest actionRequest) {
        return mWSMApi.approveAll(actionRequest);
    }

    @Override
    public Single<BaseResponse> sendEmail(String email) {
        return mWSMApi.sendEmail(email);
    }

    private Map<String, String> inputParamsRequestsManage(QueryRequest queryRequest) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_USER_NAME, queryRequest.getUserName());
        params.put(PARAM_STATUS, queryRequest.getStatus());
        params.put(PARAM_GROUP_ID, queryRequest.getGroupId());
        params.put(PARAM_WORKSPACE_ID, queryRequest.getWorkspaceId());
        params.put(PARAM_PAGE, queryRequest.getPage());
        switch (queryRequest.getRequestType()) {
            case RequestType.REQUEST_LATE_EARLY:
                params.put(PARAM_FROM_TIME_REQUEST_LEAVE, queryRequest.getFromTime());
                params.put(PARAM_TO_TIME_REQUEST_LEAVE, queryRequest.getToTime());
                break;
            case RequestType.REQUEST_OVERTIME:
                params.put(PARAM_FROM_TIME_REQUEST_OVERTIME, queryRequest.getFromTime());
                params.put(PARAM_TO_TIME_REQUEST_OVERTIME, queryRequest.getToTime());
                break;
            case RequestType.REQUEST_OFF:
                params.put(PARAM_FROM_TIME_REQUEST_OFF, queryRequest.getFromTime());
                params.put(PARAM_TO_TIME_REQUEST_OFF, queryRequest.getToTime());
                break;
            default:
                break;
        }
        return params;
    }

    private Map<String, String> inputParamsRequestsPersonal(QueryRequest queryRequest) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_STATUS_PERSONAL, queryRequest.getStatus());
        params.put(PARAM_MONTH, queryRequest.getMonthWorking());
        params.put(PARAM_PAGE, queryRequest.getPage());
        return params;
    }
}
