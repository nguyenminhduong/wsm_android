package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link UpdateProfilePresenter}.
 */
@Module
public class UpdateProfileModule {

    private Activity mActivity;

    public UpdateProfileModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public UpdateProfileContract.ViewModel provideViewModel(
            UpdateProfileContract.Presenter presenter) {
        return new UpdateProfileViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public UpdateProfileContract.Presenter providePresenter() {
        return new UpdateProfilePresenter();
    }
}
