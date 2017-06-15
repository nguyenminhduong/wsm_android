package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by tri on 12/06/2017.
 */

public interface RequestDataSource {
    interface RemoteDataSource {
        Observable<Object> createFormRequestOverTime(@NonNull Request request);

        Observable<BaseResponse<List<Request>>> getListRequestOff(@NonNull int userId);
    }
}
