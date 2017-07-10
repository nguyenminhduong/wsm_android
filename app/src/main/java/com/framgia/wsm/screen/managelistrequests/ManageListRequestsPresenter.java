package com.framgia.wsm.screen.managelistrequests;

/**
 * Listens to user actions from the UI ({@link ManageListRequestsFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class ManageListRequestsPresenter implements ManageListRequestsContract.Presenter {
    private static final String TAG = ManageListRequestsPresenter.class.getName();

    private ManageListRequestsContract.ViewModel mViewModel;

    public ManageListRequestsPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(ManageListRequestsContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
