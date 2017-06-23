package com.framgia.wsm.screen.confirmrequestoff;

import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ConfirmRequestOffContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onCreateFormRequestOffSuccess();

        void onCreateFormFormRequestOffError(BaseException exception);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);

        void onDeleteFormRequestOffSuccess();

        void onDeleteFormRequestOffError(BaseException exception);

        void onEditFormRequestOffSuccess(RequestOff requestOff);

        void onEditFormRequestOffError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void createFormRequestOff(RequestOff requestOff);

        void deleteFormRequestOff(int requestOffId);

        void editFormRequestOff(RequestOff requestOff);

        void getUser();
    }
}
