package com.framgia.wsm.screen.createnewpassword;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link CreateNewPasswordPresenter}.
 */
@Module
public class CreateNewPasswordModule {

    private Activity mActivity;

    public CreateNewPasswordModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public CreateNewPasswordContract.ViewModel provideViewModel(
            CreateNewPasswordContract.Presenter presenter) {
        return new CreateNewPasswordViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public CreateNewPasswordContract.Presenter providePresenter() {
        return new CreateNewPasswordPresenter();
    }
}
