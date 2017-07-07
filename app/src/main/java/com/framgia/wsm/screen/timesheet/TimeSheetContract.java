package com.framgia.wsm.screen.timesheet;

import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TimeSheetContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetTimeSheetError(BaseException throwable);

        void onGetTimeSheetSuccess(UserTimeSheet userTimeSheet);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getTimeSheet(int month, int year);
    }
}
