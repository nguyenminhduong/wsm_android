package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.remote.NotificationRemoteDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by minhd on 7/5/2017.
 */

public class NotificationRepository {
    private NotificationDataSource.RemoteDataSource mRemoteDataSource;

    public NotificationRepository(NotificationRemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public Observable<BaseResponse<List<Notification>>> getNotification() {
        List<Notification> notifications = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            notifications.add(
                    new Notification(1, "2017/07/07 8:30", "Your request leave has been accepted",
                            false, "https://wsm.framgia"
                            + ".vn/assets/notification/RequestLeave-7828a53217fce73830f4773cf9fda0"
                            + "66a054c2794a378a2b3f06e1a86606d765.png"));
            notifications.add(
                    new Notification(1, "2017/07/07 8:30", "Your request leave has been accepted",
                            true, "https://wsm.framgia"
                            + ".vn/assets/notification/RequestLeave-7828a53217fce73830f4773cf9fda0"
                            + "66a054c2794a378a2b3f06e1a86606d765.png"));
        }
        BaseResponse<List<Notification>> baseResponse = new BaseResponse<>(notifications);
        return Observable.just(baseResponse);
    }
}
