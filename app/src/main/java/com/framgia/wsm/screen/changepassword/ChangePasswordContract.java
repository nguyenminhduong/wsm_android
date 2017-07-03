package com.framgia.wsm.screen.changepassword;

import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ChangePasswordContract {
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
