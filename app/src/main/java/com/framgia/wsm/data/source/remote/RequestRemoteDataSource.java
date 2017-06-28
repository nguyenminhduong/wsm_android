package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by tri on 12/06/2017.
 */

public class RequestRemoteDataSource extends BaseRemoteDataSource
        implements RequestDataSource.RemoteDataSource {
    @Inject
    public RequestRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime) {
        return mWSMApi.createFormRequestOverTime(requestOverTime);
    }

    @Override
    public Observable<Object> createFormRequestOff(@NonNull RequestOff requestOff) {
        return mWSMApi.createFormRequestOff(requestOff);
    }

    @Override
    public Observable<Object> createFormRequestLeave(@NonNull Request request) {
        return mWSMApi.createFormRequestLeave(request);
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestOverTime() {
        return mWSMApi.getListRequestOverTime();
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestOff() {
        return mWSMApi.getListRequestOff();
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestLateEarly() {
        return mWSMApi.getListRequestLateEarly();
    }

    @Override
    public Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
            int status, String time) {
        return mWSMApi.getListRequestOverTimeWithStatusAndTime(status, time);
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestLeaveWithStatusAndTime(int status,
            String time) {
        return mWSMApi.getListRequestLeaveWithStatusAndTime(status, time);
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestOffWithStatusAndTime(int status,
            String time) {
        return mWSMApi.getListRequestOffWithStatusAndTime(status, time);
    }

    @Override
    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mWSMApi.deleteFormRequestOff(requestOffId);
    }

    @Override
    public Observable<BaseResponse<RequestOff>> editFormRequestOff(@NonNull RequestOff requestOff) {
        return mWSMApi.editFormRequestOff(requestOff)
                .flatMap(
                        new Function<BaseResponse<RequestOff>,
                                ObservableSource<BaseResponse<RequestOff>>>() {
                            @Override
                            public ObservableSource<BaseResponse<RequestOff>> apply(
                                    @NonNull BaseResponse<RequestOff> requestOffBaseResponse)
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
        return mWSMApi.editFormRequestOverTime(requestOverTime)
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
    public Observable<BaseResponse<Request>> editFormRequestLeave(@NonNull Request requestLeave) {
        return mWSMApi.editFormRequestLeave(requestLeave)
                .flatMap(
                        new Function<BaseResponse<Request>,
                                ObservableSource<BaseResponse<Request>>>() {
                            @Override
                            public ObservableSource<BaseResponse<Request>> apply(
                                    @NonNull BaseResponse<Request> requestBaseResponse)
                                    throws Exception {
                                if (requestBaseResponse != null) {
                                    return Observable.just(requestBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }
}
