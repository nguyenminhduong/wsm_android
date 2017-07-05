package com.framgia.wsm.screen.notification;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;

/**
 * Exposes the data to be used in the Notification screen.
 */

public class NotificationViewModel implements NotificationContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification> {

    private NotificationContract.Presenter mPresenter;
    private NotificationAdapter mNotificationAdapter;

    public NotificationViewModel(NotificationContract.Presenter presenter,
            NotificationAdapter notificationAdapter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNotificationAdapter = notificationAdapter;
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
}
