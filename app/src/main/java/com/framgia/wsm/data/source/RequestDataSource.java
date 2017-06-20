package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
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

        Observable<Object> createFormRequestOff(@NonNull RequestOff requestOff);

        Observable<BaseResponse<List<Request>>> getListRequestOff(@NonNull int userId);

        Observable<BaseResponse<List<Request>>> getListRequestLateEarly(@NonNull int userId);

        Observable<BaseResponse<List<Request>>> getListRequestOverTime(@NonNull int userId);
    }
}
