package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.TimeSheetDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import javax.inject.Inject;

/**
 * Created by Duong on 5/24/2017.
 */

public class TimeSheetRemoteDataSource extends BaseRemoteDataSource
        implements TimeSheetDataSource.RemoteDataSource {

    @Inject
    public TimeSheetRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse<TimeSheetResponse>> getTimeSheet(int month, int year) {
        return mWSMApi.getTimeSheet(month, year)
                .flatMap(
                        new Function<BaseResponse<TimeSheetResponse>,
                                ObservableSource<BaseResponse<TimeSheetResponse>>>() {
                            @Override
                            public ObservableSource<BaseResponse<TimeSheetResponse>> apply(
                                    @NonNull BaseResponse<TimeSheetResponse> requestOffBaseResponse)
                                    throws Exception {
                                if (requestOffBaseResponse != null) {
                                    return Observable.just(requestOffBaseResponse);
                                }
                                return Observable.error(new NullPointerException());
                            }
                        });
    }
}
