package com.framgia.wsm.screen.confirmrequestleave;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ConfirmRequestLeavePresenter}.
 */
@Module
public class ConfirmRequestLeaveModule {

    private Activity mActivity;

    public ConfirmRequestLeaveModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmRequestLeaveContract.ViewModel provideViewModel(Context context,
            ConfirmRequestLeaveContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        LeaveRequest requestLeave =
                mActivity.getIntent().getParcelableExtra(Constant.EXTRA_REQUEST_LEAVE);
        int actionType = mActivity.getIntent().getExtras().getInt(Constant.EXTRA_ACTION_TYPE);
        return new ConfirmRequestLeaveViewModel(context, presenter, requestLeave, navigator,
                dialogManager, actionType);
    }

    @ActivityScope
    @Provides
    public ConfirmRequestLeaveContract.Presenter providePresenter(UserRepository userRepository,
            RequestRepository requestRepository, BaseSchedulerProvider baseSchedulerProvider,
            DialogManager dialogManager) {
        return new ConfirmRequestLeavePresenter(userRepository, requestRepository,
                baseSchedulerProvider, dialogManager);
    }

    @ActivityScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @ActivityScope
    @Provides
    RequestRepository provideRequestRepository(RequestRemoteDataSource remoteDataSource) {
        return new RequestRepository(remoteDataSource);
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
}
