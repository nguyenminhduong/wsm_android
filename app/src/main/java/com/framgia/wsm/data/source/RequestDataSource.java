package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Request;
import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;

/**
 * Created by tri on 12/06/2017.
 */

public interface RequestDataSource {
    interface RemoteDataSource {
        Completable createFormRequestOverTime(@NonNull Request request);
    }
}
