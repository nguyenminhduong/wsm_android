package com.framgia.wsm.screen.requestleave.listrequestleave;

/**
 * Listens to user actions from the UI ({@link ListRequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ListRequestLeavePresenter implements ListRequestLeaveContract.Presenter {
    private static final String TAG = ListRequestLeavePresenter.class.getName();

    private ListRequestLeaveContract.ViewModel mViewModel;

    ListRequestLeavePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ListRequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
