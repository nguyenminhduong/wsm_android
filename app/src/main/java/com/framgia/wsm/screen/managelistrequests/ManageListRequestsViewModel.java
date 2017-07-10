package com.framgia.wsm.screen.managelistrequests;

/**
 * Exposes the data to be used in the Managelistrequests screen.
 */

public class ManageListRequestsViewModel implements ManageListRequestsContract.ViewModel {

    private ManageListRequestsContract.Presenter mPresenter;

    public ManageListRequestsViewModel(ManageListRequestsContract.Presenter presenter) {
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
