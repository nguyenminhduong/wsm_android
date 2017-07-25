package com.framgia.wsm.screen.notification;

import android.util.Log;
import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.navigator.Navigator;
import java.util.List;

/**
 * Exposes the data to be used in the Notification screen.
 */

public class NotificationViewModel implements NotificationContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification> {
    private static final String TAG = "NotificationViewModel";
    private NotificationContract.Presenter mPresenter;
    private NotificationAdapter mNotificationAdapter;
    private int mPage;

    NotificationViewModel(NotificationContract.Presenter presenter,
            NotificationAdapter notificationAdapter, Navigator navigator) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNotificationAdapter = notificationAdapter;
        mPage = 1;
        mPresenter.getNotification(mPage);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onItemRecyclerViewClick(Notification item) {
        // todo open screen follow notification type
    }

    public NotificationAdapter getNotificationAdapter() {
        return mNotificationAdapter;
    }

    @Override
    public void onGetNotificationSuccess(List<Notification> list) {
        if (list == null) {
            return;
        }
        mNotificationAdapter.updateData(list);
    }

    @Override
    public void onGetNotificationError(BaseException e) {
        Log.e(TAG, "NotificationViewModel", e);
    }
}
