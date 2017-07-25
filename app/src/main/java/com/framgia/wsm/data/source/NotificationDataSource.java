package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.api.request.NotificationRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import io.reactivex.Observable;

/**
 * Created by minhd on 7/5/2017.
 */

public interface NotificationDataSource {
    interface RemoteDataSource {
        Observable<BaseResponse<NotificationResponse>> getNotification(int page);

        Observable<BaseResponse> setReadNotification(NotificationRequest notificationRequest);
    }
}
