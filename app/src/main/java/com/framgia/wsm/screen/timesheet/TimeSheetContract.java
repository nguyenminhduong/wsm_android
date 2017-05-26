package com.framgia.wsm.screen.timesheet;

import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface TimeSheetContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetTimeSheetError(BaseException throwable);

        void onGetTimeSheetSuccess(List<TimeSheetDate> list);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getTimeSheet();
    }
}
