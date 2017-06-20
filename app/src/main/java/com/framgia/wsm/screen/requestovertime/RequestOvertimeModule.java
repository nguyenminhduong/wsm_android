package com.framgia.wsm.screen.requestovertime;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
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
 * the {@link RequestOvertimePresenter}.
 */
@Module
public class RequestOvertimeModule {
    private Activity mActivity;

    public RequestOvertimeModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public RequestOvertimeContract.ViewModel provideViewModel(Context context,
            RequestOvertimeContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        return new RequestOvertimeViewModel(context, presenter, navigator, dialogManager);
    }

    @ActivityScope
    @Provides
    public RequestOvertimeContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider, Validator validator) {
        return new RequestOvertimePresenter(userRepository, baseSchedulerProvider, validator);
    }

    @ActivityScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(userLocalDataSource, remoteDataSource);
    }

    @ActivityScope
    @Provides
    Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), RequestOverTime.class);
    }

    @ActivityScope
    @Provides
    DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }
}
