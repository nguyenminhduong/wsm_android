package com.framgia.wsm.screen.notification;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.NotificationRequest;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface NotificationContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetNotificationSuccess(List<Notification> list);

        void onGetNotificationError(BaseException e);

        void onLoadMoreNotification();

        void onSetReadSuccess();

        void onSetReadError(BaseException e);

        void setUpdateNotificationListener(
                NotificationDialogFragment.UpdateNotificationListener listener);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getNotification(int page);

        void setRead(NotificationRequest notificationRequest);
    }
}
