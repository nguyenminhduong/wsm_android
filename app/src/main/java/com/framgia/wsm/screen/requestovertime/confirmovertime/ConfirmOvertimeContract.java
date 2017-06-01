package com.framgia.wsm.screen.requestovertime.confirmovertime;

import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ConfirmOvertimeContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
    }
}
