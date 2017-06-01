package com.framgia.wsm.screen.requestovertime.confirmovertime;

/**
 * Listens to user actions from the UI ({@link ConfirmOvertimeActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmOvertimePresenter implements ConfirmOvertimeContract.Presenter {
    private static final String TAG = ConfirmOvertimePresenter.class.getName();

    private ConfirmOvertimeContract.ViewModel mViewModel;

    ConfirmOvertimePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ConfirmOvertimeContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
