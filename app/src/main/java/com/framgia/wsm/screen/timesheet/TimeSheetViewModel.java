package com.framgia.wsm.screen.timesheet;

/**
 * Exposes the data to be used in the TimeSheet screen.
 */

public class TimeSheetViewModel implements TimeSheetContract.ViewModel {

    private TimeSheetContract.Presenter mPresenter;

    public TimeSheetViewModel(TimeSheetContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }
}
