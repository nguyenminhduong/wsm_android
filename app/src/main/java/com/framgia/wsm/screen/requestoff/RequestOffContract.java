package com.framgia.wsm.screen.requestoff;

import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface RequestOffContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onInputReasonError(String reason);

        void onInputNumberDayHaveSalaryError(int typeOfDay);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void getUser();

        boolean validateData(OffRequest requestOff);

        boolean validateAllNumberDayHaveSalary(OffRequest requestOff);

        void validateNumberDayHaveSalary(OffRequest requestOff);
    }
}
