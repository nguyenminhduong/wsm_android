package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by tri on 12/06/2017.
 */

public interface RequestDataSource {
    interface RemoteDataSource {
        Observable<Object> createFormRequestOverTime(@NonNull RequestOverTime requestOverTime);

        Observable<Object> createFormRequestOff(@NonNull RequestOff requestOff);

        Observable<Object> createFormRequestLeave(@NonNull Request request);

        Observable<BaseResponse<List<Request>>> getListRequestOff(@NonNull int userId);

        Observable<BaseResponse<List<Request>>> getListRequestLateEarly(@NonNull int userId);

        Observable<BaseResponse<List<RequestOverTime>>> getListRequestOverTimeWithStatusAndTime(
                @NonNull int userId, int status, String time);

        Observable<Object> deleteFormRequestOff(@NonNull int requestOffId);

        Observable<BaseResponse<RequestOff>> editFormRequestOff(@NonNull RequestOff requestOff);

        Observable<BaseResponse<RequestOverTime>> editFormRequestOverTime(
                @NonNull RequestOverTime requestOverTime);

        Observable<Object> deleteFormRequestOverTime(@NonNull int requestOverTimeId);

        Observable<Object> deleteFormRequestLeave(@NonNull int requestLeaveId);

        Observable<BaseResponse<Request>> editFormRequestLeave(@NonNull Request requestLeave);
    }
}
