package com.framgia.wsm.screen.main;

/**
 * Exposes the data to be used in the Main screen.
 */

public class MainViewModel implements MainContract.ViewModel {

    private MainContract.Presenter mPresenter;

    public MainViewModel(MainContract.Presenter presenter) {
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
