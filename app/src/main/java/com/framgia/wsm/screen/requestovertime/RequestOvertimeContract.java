package com.framgia.wsm.screen.requestovertime;

import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RequestOvertimeContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onInputProjectNameError(String projectNameError);

        void onInputReasonError(String reasonErrpr);

        void onInputFromTimeError(String fromTimeError);

        void onInputToTimeError(String toTimeError);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getUser();

        boolean validateDataInput(Request request);
    }
}
