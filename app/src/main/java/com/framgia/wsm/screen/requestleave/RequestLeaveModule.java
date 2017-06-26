package com.framgia.wsm.screen.requestleave;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
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
 * the {@link RequestLeavePresenter}.
 */
@Module
public class RequestLeaveModule {

    private Activity mActivity;

    public RequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public RequestLeaveContract.ViewModel provideViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter, DialogManager dialogManager) {
        Request requestLeave =
                mActivity.getIntent().getParcelableExtra(Constant.EXTRA_REQUEST_LEAVE);
        int actionType = mActivity.getIntent().getExtras().getInt(Constant.EXTRA_ACTION_TYPE);
        return new RequestLeaveViewModel(context, navigator, presenter, dialogManager, requestLeave,
                actionType);
    }

    @ActivityScope
    @Provides
    public RequestLeaveContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider, Validator validator) {
        return new RequestLeavePresenter(userRepository, baseSchedulerProvider, validator);
    }

    @ActivityScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @ActivityScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    DialogManager provideDialogManager() {
        return new DialogManagerImpl(mActivity);
    }

    @ActivityScope
    @Provides
    Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), RequestLeaveViewModel.class);
    }
}
