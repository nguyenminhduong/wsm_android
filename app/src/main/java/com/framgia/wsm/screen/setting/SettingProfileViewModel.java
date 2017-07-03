package com.framgia.wsm.screen.setting;

import android.databinding.BaseObservable;

/**
 * Created by ths on 03/07/2017.
 */

public class SettingProfileViewModel extends BaseObservable
        implements SettingProfileContract.ViewModel {

    private SettingProfileContract.Presenter mPresenter;

    public SettingProfileViewModel(SettingProfileContract.Presenter presenter) {
        mPresenter = presenter;
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
