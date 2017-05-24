package com.framgia.wsm.screen.timesheet;

/**
 * Listens to user actions from the UI ({@link TimeSheetFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TimeSheetPresenter implements TimeSheetContract.Presenter {
    private static final String TAG = TimeSheetPresenter.class.getName();

    private TimeSheetContract.ViewModel mViewModel;

    TimeSheetPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(TimeSheetContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
