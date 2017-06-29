package com.framgia.wsm.screen.notification;

/**
 * Listens to user actions from the UI ({@link NotificationDialogFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class NotificationPresenter implements NotificationContract.Presenter {
    private static final String TAG = NotificationPresenter.class.getName();

    private NotificationContract.ViewModel mViewModel;

    public NotificationPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(NotificationContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
