package com.framgia.wsm.data.source;

import com.framgia.wsm.data.source.remote.NotificationRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.request.NotificationRequest;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import io.reactivex.Observable;

/**
 * Created by minhd on 7/5/2017.
 */

public class NotificationRepository {
    private NotificationDataSource.RemoteDataSource mRemoteDataSource;

    public NotificationRepository(NotificationRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<NotificationResponse>> getNotification(int page) {
        return mRemoteDataSource.getNotification(page);
    }

    public Observable<BaseResponse> setReadNotification(NotificationRequest notificationRequest) {
        return mRemoteDataSource.setReadNotification(notificationRequest);
    }
}
