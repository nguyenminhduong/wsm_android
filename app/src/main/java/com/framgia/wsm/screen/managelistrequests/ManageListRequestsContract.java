package com.framgia.wsm.screen.managelistrequests;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import com.framgia.wsm.utils.RequestType;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ManageListRequestsContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void onGetListRequestManageError(BaseException exception);

        void onGetListRequestManageSuccess(@RequestType int requestType, Object object);

        void setRequestType(@RequestType int requestType);

        void onApproveRequestSuccess();

        void onApproveRequestError(BaseException exception);

        void onRejectRequestSuccess();

        void onRejectRequestError(BaseException exception);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {

        void getListAllRequestManage(@RequestType int requestType, String fromTime, String toTime);

        void approveRequest(@RequestType int requestType, int requestId);

        void rejectRequest(@RequestType int requestType, int requestId);
    }
}
