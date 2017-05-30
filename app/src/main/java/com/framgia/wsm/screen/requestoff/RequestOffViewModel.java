package com.framgia.wsm.screen.requestoff;

/**
 * Exposes the data to be used in the RequestOff screen.
 */

public class RequestOffViewModel implements RequestOffContract.ViewModel {

    private RequestOffContract.Presenter mPresenter;

    public RequestOffViewModel(RequestOffContract.Presenter presenter) {
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
