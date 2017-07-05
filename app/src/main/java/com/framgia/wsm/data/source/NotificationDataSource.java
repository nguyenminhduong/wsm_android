package com.framgia.wsm.data.source;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by minhd on 7/5/2017.
 */

public interface NotificationDataSource {
    interface RemoteDataSource {
        Observable<BaseResponse<List<Notification>>> getNotification();
    }
}
