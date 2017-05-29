package com.framgia.wsm.screen.timesheet;

import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import java.util.List;

/**
 * Exposes the data to be used in the TimeSheet screen.
 */

public class TimeSheetViewModel implements TimeSheetContract.ViewModel {

    private TimeSheetContract.Presenter mPresenter;

    public TimeSheetViewModel(TimeSheetContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getTimeSheet();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetTimeSheetError(BaseException throwable) {
        //todo show error message
    }

    @Override
    public void onGetTimeSheetSuccess(List<TimeSheetDate> list) {
        //todo update time sheet view
    }
}
