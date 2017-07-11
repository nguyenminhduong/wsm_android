package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

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
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<ViewModel> {
    }
}
