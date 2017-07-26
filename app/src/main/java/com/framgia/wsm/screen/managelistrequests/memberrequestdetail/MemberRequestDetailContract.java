package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * Created by ths on 11/07/2017.
 */

public interface MemberRequestDetailContract {
    /**
     * View
     */
    interface ViewModel extends BaseViewModel {
        void onError(BaseException e);

        void onDismissProgressDialog();

        void onShowIndeterminateProgressDialog();

        void onApproveOrRejectRequestSuccess(ActionRequestResponse actionRequestResponse);

        void onGetUserSuccess(User user);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void approveOrRejectRequest(ActionRequest actionRequest);

        void getUser();
    }
}
