package com.framgia.wsm.screen.requestovertime.confirmovertime;

import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ConfirmOvertimeContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onCreateFormOverTimeSuccess();

        void onCreateFormOverTimeError(BaseException exception);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void createFormRequestOverTime(RequestOverTime requestOverTime);

        void getUser();
    }
}
