package com.framgia.wsm.screen.forgotpassword;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Validator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ForgotPasswordPresenter}.
 */
@Module
public class ForgotPasswordModule {

    private final Activity mActivity;

    public ForgotPasswordModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ForgotPasswordContract.ViewModel provideViewModel(Context context,
            ForgotPasswordContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        return new ForgotPasswordViewModel(context, presenter, navigator, dialogManager);
    }

    @ActivityScope
    @Provides
    public ForgotPasswordContract.Presenter providePresenter() {
        return new ForgotPasswordPresenter();
    }

    @ActivityScope
    @Provides
    public Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), ForgotPasswordActivity.class);
    }

    @ActivityScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    public DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
