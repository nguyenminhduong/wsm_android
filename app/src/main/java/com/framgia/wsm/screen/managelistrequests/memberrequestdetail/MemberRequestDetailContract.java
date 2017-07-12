package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import com.framgia.wsm.data.source.remote.api.error.BaseException;
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
        void onRejectedSuccess();

        void onApproveSuccess();

        void onRejectedOrAcceptedError(BaseException e);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void rejectRequest(int requestId);

        void approveRequest(int requestId);
    }
}
