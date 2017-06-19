package com.framgia.wsm.screen.confirmrequestoff;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.model.RequestOff;
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
 * the {@link ConfirmRequestOffPresenter}.
 */
@Module
public class ConfirmRequestOffModule {

    private Activity mActivity;

    public ConfirmRequestOffModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public ConfirmRequestOffContract.ViewModel provideViewModel(
            ConfirmRequestOffContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        RequestOff requestOff =
                mActivity.getIntent().getExtras().getParcelable(Constant.EXTRA_REQUEST_OFF);
        return new ConfirmRequestOffViewModel(presenter, navigator, dialogManager, requestOff);
    }

    @ActivityScope
    @Provides
    public ConfirmRequestOffContract.Presenter providePresenter(UserRepository userRepository,
            RequestRepository requestRepository, BaseSchedulerProvider baseSchedulerProvider) {
        return new ConfirmRequestOffPresenter(userRepository, requestRepository,
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
