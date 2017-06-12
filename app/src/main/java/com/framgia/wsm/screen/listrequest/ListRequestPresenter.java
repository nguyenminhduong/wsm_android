package com.framgia.wsm.screen.listrequest;

/**
 * Listens to user actions from the UI ({@link ListRequestFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ListRequestPresenter implements ListRequestContract.Presenter {
    private static final String TAG = ListRequestPresenter.class.getName();

    private ListRequestContract.ViewModel mViewModel;

    ListRequestPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ListRequestContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
