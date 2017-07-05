package com.framgia.wsm.screen.changepassword;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
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
            ChangePasswordContract.Presenter presenter, Navigator navigator) {
        return new ChangePasswordViewModel(presenter, navigator);
    }

    @ActivityScope
    @Provides
    public ChangePasswordContract.Presenter providePresenter(Context context, Validator validator,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new ChangePasswordPresenter(context, validator, baseSchedulerProvider);
    }

    @ActivityScope
    @Provides
    public Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), ChangePasswordViewModel.class);
    }

    @ActivityScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator(mActivity);
    }
}
