package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.RequestOffResponse;
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
    public Observable<BaseResponse<List<Request>>> getListRequestOff(@NonNull int userId) {
        return mWSMApi.getListRequestOff(userId);
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestLateEarly(@NonNull int userId) {
        return mWSMApi.getListRequestLateEarly(userId);
    }

    @Override
    public Observable<BaseResponse<List<Request>>> getListRequestOverTime(@NonNull int userId) {
        return mWSMApi.getListRequestOverTime(userId);
    }

    @Override
    public Observable<Object> deleteFormRequestOff(@NonNull int requestOffId) {
        return mWSMApi.deleteFormRequestOff(requestOffId);
    }

    @Override
    public Observable<BaseResponse<RequestOffResponse>> editFormRequestOff(
            @NonNull RequestOff requestOff) {
        return mWSMApi.editFormRequestOff(requestOff)
                .flatMap(
                        new Function<BaseResponse<RequestOffResponse>,
                                ObservableSource<BaseResponse<RequestOffResponse>>>() {

                            @Override
                            public ObservableSource<BaseResponse<RequestOffResponse>> apply(@NonNull
                                    BaseResponse<RequestOffResponse> requestOffResponseBaseResponse)
                                    throws Exception {
                                if (requestOffResponseBaseResponse != null) {
                                    return Observable.just(requestOffResponseBaseResponse);
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
}
