package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.source.NotificationDataSource;
import com.framgia.wsm.data.source.remote.api.request.NotificationRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by minhd on 7/10/2017.
 */

public class NotificationRemoteDataSource extends BaseRemoteDataSource
        implements NotificationDataSource.RemoteDataSource {
    @Inject
    public NotificationRemoteDataSource(WSMApi api) {
        super(api);
    }

    @Override
    public Observable<BaseResponse<NotificationResponse>> getNotification(int page) {
        return mWSMApi.getNotification(page);
    }

    @Override
    public Observable<BaseResponse> setReadNotification(NotificationRequest notificationRequest) {
        return mWSMApi.setReadNotifcation(notificationRequest);
    }
}
