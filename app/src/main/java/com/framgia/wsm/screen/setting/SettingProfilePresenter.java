package com.framgia.wsm.screen.setting;

/**
 * Listens to user actions from the UI ({@link SettingProfileFragment}), retrieves the data and
 * updates
 * the UI as required.
 */

final class SettingProfilePresenter implements SettingProfileContract.Presenter {

    private SettingProfileContract.ViewModel mViewModel;

    public SettingProfilePresenter() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setViewModel(SettingProfileContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
