package com.framgia.wsm.screen.confirmrequestleave;

/**
 * Listens to user actions from the UI ({@link ConfirmRequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmRequestLeavePresenter implements ConfirmRequestLeaveContract.Presenter {
    private static final String TAG = ConfirmRequestLeavePresenter.class.getName();

    private ConfirmRequestLeaveContract.ViewModel mViewModel;

    ConfirmRequestLeavePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ConfirmRequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
