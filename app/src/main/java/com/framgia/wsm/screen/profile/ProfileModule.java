package com.framgia.wsm.screen.profile;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.FragmentScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ProfilePresenter}.
 */
@Module
public class ProfileModule {

    private Fragment mFragment;

    public ProfileModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public ProfileContract.ViewModel provideViewModel(ProfileContract.Presenter presenter) {
        return new ProfileViewModel(presenter);
    }

    @FragmentScope
    @Provides
    public ProfileContract.Presenter providePresenter() {
        return new ProfilePresenter();
    }
}
