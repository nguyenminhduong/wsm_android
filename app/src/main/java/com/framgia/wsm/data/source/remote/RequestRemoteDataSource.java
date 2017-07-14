package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestDataSource;
import com.framgia.wsm.data.source.remote.api.request.RequestLeaveRequest;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by tri on 12/06/2017.
 */

public class RequestRemoteDataSource extends BaseRemoteDataSource
        implements RequestDataSource.RemoteDataSource {

    private static final String PARAM_USER_NAME = "user_name";
    private static final String PARAM_FROM_TIME = "from_time";
    private static final String PARAM_TO_TIME = "to_time";
    private static final String PARAM_STATUS = "status";
    private static final String PARAM_GROUP_ID = "group_id";
    private static final String PARAM_WORKSPACE_ID = "worksapce_id";

    @Inject
    public RequestRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mWSMApi.createFormRequestOverTime(requestOverTime);
    }

    @Override
    public Observable<Object> createFormRequestOff(@NonNull RequestOffRequest requestOffRequest) {
        return mWSMApi.createFormRequestOff(requestOffRequest);
    }

    @Override
    public Observable<Object> createFormRequestLeave(
            @NonNull RequestLeaveRequest requestLeaveRequest) {
        return mWSMApi.createFormRequestLeave(requestLeaveRequest);
    }

    @Override
    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTime() {
        return mWSMApi.getListRequestOverTime();
    }

    @Override
    public Observable<BaseResponse<List<OffRequest>>> getListRequestOff() {
        return mWSMApi.getListRequestOff();
    }

    @Override
    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLateEarly() {
        return mWSMApi.getListRequestLeaves();
    }

    @Override
    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
            int status, String time) {
        return mWSMApi.getListRequestOverTimeWithStatusAndTime(status, time);
    }

    @Override
    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestLeaveWithStatusAndTime(
            int status, String time) {
        return mWSMApi.getListRequestLeaveWithStatusAndTime(status, time);
    }

    @Override
    public Observable<BaseResponse<List<LeaveRequest>>> getListRequestOffWithStatusAndTime(
            int status, String time) {
        return mWSMApi.getListRequestOffWithStatusAndTime(status, time);
    }

    @Override
    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mWSMApi.deleteFormRequestOff(requestOffId);
    }

    @Override
    public Observable<BaseResponse<OffRequest>> editFormRequestOff(
            @NonNull RequestOffRequest requestOffRequest) {
        return mWSMApi.editFormRequestOff(requestOffRequest)
                .flatMap(
                        new Function<BaseResponse<OffRequest>,
                                ObservableSource<BaseResponse<OffRequest>>>() {
                            @Override
                            public ObservableSource<BaseResponse<OffRequest>> apply(
                                    @NonNull BaseResponse<OffRequest> requestOffBaseResponse)
                                    throws Exception {
                                if (requestOffBaseResponse != null) {
                                    return Observable.just(requestOffBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<BaseResponse<RequestOverTime>> editFormRequestOverTime(
            @NonNull RequestOverTime requestOverTime) {
        return mWSMApi.editFormRequestOverTime(requestOverTime.getId(), requestOverTime)
                .flatMap(
                        new Function<BaseResponse<RequestOverTime>,
                                ObservableSource<BaseResponse<RequestOverTime>>>() {

                            @Override
                            public ObservableSource<BaseResponse<RequestOverTime>> apply(@NonNull
                                    BaseResponse<RequestOverTime> requestOverTimeBaseResponse)
                                    throws Exception {
                                if (requestOverTimeBaseResponse != null) {
                                    return Observable.just(requestOverTimeBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }

    @Override
    public Observable<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId) {
        return mWSMApi.deleteFormRequestOverTime(requestOverTimeId);
    }

    public Observable<Object> deleteFormRequestLeave(@NonNull int requestLeaveId) {
        return mWSMApi.deleteFormRequestLeave(requestLeaveId);
    }

    @Override
    public Observable<Object> editFormRequestLeave(@NonNull int requestId,
            RequestLeaveRequest requestLeave) {
        return mWSMApi.editFormRequestLeave(requestId, requestLeave);
    }

    @Override
    public Observable<BaseResponse<List<LeaveRequest>>> getListRequesLeavetManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestLeaveManage(inputParams(queryRequest));
    }

    @Override
    public Observable<BaseResponse<List<RequestOverTime>>> getListRequesOvertimetManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestOvertimeManage(inputParams(queryRequest));
    }

    @Override
    public Observable<BaseResponse<List<OffRequest>>> getListRequesOffManage(
            QueryRequest queryRequest) {
        return mWSMApi.getListRequestOffManage(inputParams(queryRequest));
    }

    @Override
    public Observable<BaseResponse<LeaveRequest>> approveRequestLeave(int requestId) {
        return mWSMApi.approveFormRequestLeave(requestId);
    }

    @Override
    public Observable<BaseResponse<OffRequest>> approveRequestOff(int requestId) {
        return mWSMApi.approveFormRequestOff(requestId);
    }

    @Override
    public Observable<BaseResponse<RequestOverTime>> approveRequestOverTime(int requestId) {
        return mWSMApi.approveFormRequestOverTime(requestId);
    }

    @Override
    public Observable<BaseResponse<LeaveRequest>> rejectRequestLeave(int requestId) {
        return mWSMApi.rejectFormRequestLeave(requestId);
    }

    @Override
    public Observable<BaseResponse<OffRequest>> rejectRequestOff(int requestId) {
        return mWSMApi.rejectFormRequestOff(requestId);
    }

    @Override
    public Observable<BaseResponse<RequestOverTime>> rejectRequestOverTime(int requestId) {
        return mWSMApi.rejectFormRequestOverTime(requestId);
    }

    private Map<String, String> inputParams(QueryRequest queryRequest) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_USER_NAME, queryRequest.getUserName());
        params.put(PARAM_FROM_TIME, queryRequest.getFromTime());
        params.put(PARAM_TO_TIME, queryRequest.getToTime());
        params.put(PARAM_STATUS, queryRequest.getStatus());
        params.put(PARAM_GROUP_ID, String.valueOf(queryRequest.getGroupId()));
        params.put(PARAM_WORKSPACE_ID, String.valueOf(queryRequest.getWorkspaceId()));
        return params;
    }
}
