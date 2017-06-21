package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

import static com.framgia.wsm.utils.Constant.EXTRA_REQUEST_OVERTIME;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmOvertimePresenter}.
 */
@Module
public class ConfirmOvertimeModule {

    private Activity mActivity;

    public ConfirmOvertimeModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.ViewModel provideViewModel(
            ConfirmOvertimeContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        RequestOverTime requestOverTime =
                mActivity.getIntent().getParcelableExtra(EXTRA_REQUEST_OVERTIME);
        return new ConfirmOvertimeViewModel(presenter, requestOverTime, navigator, dialogManager);
    }

    @ActivityScope
    @Provides
    public ConfirmOvertimeContract.Presenter providePresenter(UserRepository userRepository,
            RequestRepository requestRepository, BaseSchedulerProvider baseSchedulerProvider) {
        return new ConfirmOvertimePresenter(userRepository, requestRepository,
                baseSchedulerProvider);
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
    RequestRepository provideRequestRepository(RequestRemoteDataSource remoteDataSource) {
        return new RequestRepository(remoteDataSource);
    }

    @ActivityScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(userLocalDataSource, remoteDataSource);
    }
}
