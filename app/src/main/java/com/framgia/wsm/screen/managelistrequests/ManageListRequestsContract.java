package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ManageListRequestsContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onGetListRequestManageError(BaseException exception);

        void onGetListRequestManageSuccess(Object object);

        void onReloadData();

        void onApproveOrRejectRequestSuccess(ActionRequestResponse actionRequestResponse);

        void onApproveOrRejectRequestError(BaseException exception);

        void onDismissProgressDialog();

        void onShowIndeterminateProgressDialog();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void getListAllRequestManage(int requestType, QueryRequest queryRequest);

        void approveOrRejectRequest(ActionRequest actionRequest);
    }
}
