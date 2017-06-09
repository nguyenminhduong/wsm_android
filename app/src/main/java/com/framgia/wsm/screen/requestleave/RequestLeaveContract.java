package com.framgia.wsm.screen.requestleave;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RequestLeaveContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onInputReasonError(String reason);

        void onInputProjectNameError(String errorMessage);

        void onInputCheckinTimeError(String errorMessage);

        void onInputCheckoutTimeError(String errorMessage);

        void onInputCompensationFromError(String errorMessage);

        void onInputCompensationToError(String errorMessage);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getUser();

        boolean validateDataInput(Request request);
    }
}
