package com.framgia.wsm.screen.notification;

import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
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
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getNotification(int page);
    }
}
