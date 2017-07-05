package com.framgia.wsm.data.source.remote;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.NotificationDataSource;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.service.WSMApi;
import io.reactivex.Observable;
import java.util.List;
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
    public Observable<BaseResponse<List<Notification>>> getNotification() {
        return mWSMApi.getNotification();
    }
}
