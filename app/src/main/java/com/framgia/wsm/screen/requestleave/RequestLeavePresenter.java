package com.framgia.wsm.screen.requestleave;

/**
 * Listens to user actions from the UI ({@link RequestLeaveActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class RequestLeavePresenter implements RequestLeaveContract.Presenter {
    private static final String TAG = RequestLeavePresenter.class.getName();

    private RequestLeaveContract.ViewModel mViewModel;

    RequestLeavePresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(RequestLeaveContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
