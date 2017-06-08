package com.framgia.wsm.screen.requestleave.listrequestleave;

/**
 * Exposes the data to be used in the ListRequestLeave screen.
 */

public class ListRequestLeaveViewModel implements ListRequestLeaveContract.ViewModel {

    private ListRequestLeaveContract.Presenter mPresenter;

    public ListRequestLeaveViewModel(ListRequestLeaveContract.Presenter presenter) {
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
