package com.framgia.wsm.screen.confirmrequestoff;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.RequestOffRequest;
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

        void onEditFormRequestOffSuccess();

        void onEditFormRequestOffError(BaseException exception);

        void onDismissProgressDialog();

        void onShowIndeterminateProgressDialog();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void createFormRequestOff(RequestOffRequest requestOffRequest);

        void deleteFormRequestOff(int requestOffId);

        void editFormRequestOff(RequestOffRequest requestOffRequest);

        void getUser();
    }
}
