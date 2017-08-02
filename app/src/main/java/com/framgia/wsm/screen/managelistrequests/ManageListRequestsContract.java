package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ManageListRequestsContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onGetListRequestManageError(BaseException exception);

        void onGetListRequestManageSuccess(Object object, boolean isLoadMore);

        void onReloadData();

        void onApproveOrRejectRequestSuccess(ActionRequestResponse actionRequestResponse);

        void onApproveOrRejectRequestError(BaseException exception);

        void onDismissProgressDialog();

        void onShowIndeterminateProgressDialog();

        void onLoadMoreListRequest();

        void showLayoutFooter();

        void hideLayoutFooter();

        void onApproveAllRequestSuccess(List<ActionRequestResponse> actionRequestResponseList);

        void onApproveAllRequestError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void getListAllRequestManage(int requestType, QueryRequest queryRequest,
                boolean isLoadMore);

        void getListAllRequestManageNoProgressDialog(int requestType, QueryRequest queryRequest,
                boolean isLoadMore);

        void approveOrRejectRequest(ActionRequest actionRequest);

        void approveAllRequest(ActionRequest actionRequest);
    }
}
