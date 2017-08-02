package com.framgia.wsm.screen.main;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        boolean onBackPressed();

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onLogoutSuccess();

        void onLogoutError(BaseException exception);

        void goNextFragmentListRequestOff();

        void goNextFragmentListRequestOverTime();

        void goNextFragmentListRequestLeave();

        void goNextFragmentPersonalInformation();

        void goNextFragmentManageListRequestOff();

        void goNextFragmentManageListRequestOverTime();

        void goNextFragmentManageListRequestLeave();

        void onReloadDataUser();

        void onGetNotificationSuccess(NotificationResponse notificationResponse);

        void onGetNotificationError(BaseException e);

        void updateNotificationUnRead();

        void handleClickNotification(String trackableType);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void logout();

        void clearUser();

        void getUser();

        void getNotification();
    }
}
