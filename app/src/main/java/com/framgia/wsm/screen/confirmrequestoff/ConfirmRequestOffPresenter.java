package com.framgia.wsm.screen.confirmrequestoff;

/**
 * Listens to user actions from the UI ({@link ConfirmRequestOffActivity}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ConfirmRequestOffPresenter implements ConfirmRequestOffContract.Presenter {
    private static final String TAG = ConfirmRequestOffPresenter.class.getName();

    private ConfirmRequestOffContract.ViewModel mViewModel;

    ConfirmRequestOffPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ConfirmRequestOffContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
