package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.RequestDataSource;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
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
    public Observable<Object> createFormRequestOverTime(@NonNull Request request) {
        return mWSMApi.createFormRequestOverTime(request);
    }
}
