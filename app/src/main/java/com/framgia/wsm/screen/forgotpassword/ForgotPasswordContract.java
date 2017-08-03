package com.framgia.wsm.screen.forgotpassword;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ForgotPasswordContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onSendEmailSuccess();

        void onSendEmailError(BaseException exception);

        void onInputEmailError(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void sendEmail(String email);

        void validateDataInput(String email);

        void validateEmailInput(String emailInput);
    }
}
