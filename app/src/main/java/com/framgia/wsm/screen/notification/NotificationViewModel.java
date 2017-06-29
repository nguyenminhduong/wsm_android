package com.framgia.wsm.screen.notification;

/**
 * Exposes the data to be used in the Notification screen.
 */

public class NotificationViewModel implements NotificationContract.ViewModel {

    private NotificationContract.Presenter mPresenter;

    public NotificationViewModel(NotificationContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }
}
