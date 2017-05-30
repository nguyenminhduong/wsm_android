package com.framgia.wsm.screen.requestoff;

/**
 * Listens to user actions from the UI ({@link RequestOffActivity}), retrieves the data and updates
 * the UI as required.
 */
final class RequestOffPresenter implements RequestOffContract.Presenter {
    private static final String TAG = RequestOffPresenter.class.getName();

    private RequestOffContract.ViewModel mViewModel;

    RequestOffPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(RequestOffContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
