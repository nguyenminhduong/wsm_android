package com.framgia.wsm.screen.confirmrequestleave;

import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ConfirmRequestLeaveContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onCreateFormRequestLeaveSuccess();

        void onCreateFormFormRequestLeaveError(BaseException exception);

        void onEditFormRequestLeaveSuccess();

        void onEditFormFormRequestLeaveError(BaseException exception);

        void onDeleteFormRequestLeaveSuccess();

        void onDeleteFormFormRequestLeaveError(BaseException exception);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void createFormRequestLeave(LeaveRequest request);

        void editFormRequestLeave(LeaveRequest request);

        void deleteFormRequestLeave(int requestId);

        void getUser();
    }
}
