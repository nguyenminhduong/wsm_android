package com.framgia.wsm.screen.createnewpassword;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.utils.validator.Validator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
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
            CreateNewPasswordContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        String tokenResetPassword =
                mActivity.getIntent().getExtras().getString(Constant.EXTRA_TOKEN_RESET_PASSWORD);
        return new CreateNewPasswordViewModel(presenter, navigator, dialogManager,
                tokenResetPassword);
    }

    @ActivityScope
    @Provides
    public CreateNewPasswordContract.Presenter providePresenter(Context context,
            RequestRepository requestRepository, Validator validator,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new CreateNewPasswordPresenter(context, requestRepository, validator,
                baseSchedulerProvider);
    }

    @ActivityScope
    @Provides
    RequestRepository provideRequestRepository(RequestRemoteDataSource remoteDataSource) {
        return new RequestRepository(remoteDataSource);
    }

    @ActivityScope
    @Provides
    public Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), CreateNewPasswordViewModel.class);
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
