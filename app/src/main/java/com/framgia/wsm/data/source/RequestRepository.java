package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;

/**
 * Created by tri on 12/06/2017.
 */

public class RequestRepository {
    private final RequestDataSource.RemoteDataSource mRemoteDataSource;

    public RequestRepository(RequestRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Completable createFormRequestOverTime(@NonNull Request request) {
        return mRemoteDataSource.createFormRequestOverTime(request);
    }
}
