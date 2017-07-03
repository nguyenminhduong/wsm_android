package com.framgia.wsm.screen.setting;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link SettingProfilePresenter}.
 */
@Module
public class SettingProfileModule {

    private Fragment mFragment;

    public SettingProfileModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public SettingProfileContract.ViewModel provideViewModel(
            SettingProfileContract.Presenter presenter) {
        return new SettingProfileViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public SettingProfileContract.Presenter providePresenter() {
        return new SettingProfilePresenter();
    }
}
