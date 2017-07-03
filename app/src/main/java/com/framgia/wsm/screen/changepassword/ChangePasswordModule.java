package com.framgia.wsm.screen.changepassword;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ChangePasswordPresenter}.
 */
@Module
public class ChangePasswordModule {

    private Activity mActivity;

    public ChangePasswordModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ChangePasswordContract.ViewModel provideViewModel(
            ChangePasswordContract.Presenter presenter) {
        return new ChangePasswordViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public ChangePasswordContract.Presenter providePresenter() {
        return new ChangePasswordPresenter();
    }
}
