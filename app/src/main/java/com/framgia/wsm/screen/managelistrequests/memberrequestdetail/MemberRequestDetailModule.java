package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ths on 11/07/2017.
 */

@Module
public class MemberRequestDetailModule {

    private Fragment mFragment;
    private DismissDialogListener mDismissDialogListener;
    private Object mItem;

    public MemberRequestDetailModule(@NonNull Fragment fragment, Object item) {
        mFragment = fragment;
        mDismissDialogListener = (DismissDialogListener) fragment;
        mItem = item;
    }

    @FragmentScope
    @Provides
    public MemberRequestDetailContract.ViewModel provideViewModel(Context context,
            MemberRequestDetailContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager) {
        return new MemberRequestDetailViewModel(context, mFragment, presenter, navigator,
                dialogManager, mItem);
    }

    @FragmentScope
    @Provides
    public MemberRequestDetailContract.Presenter providePresenter(
            BaseSchedulerProvider baseSchedulerProvider, RequestRepository requestRepository,
            UserRepository userRepository) {
        return new MemberRequestDetailPresenter(baseSchedulerProvider, requestRepository,
                userRepository);
    }

    @FragmentScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mFragment);
    }

    @FragmentScope
    @Provides
    DialogManager provideDialogManager() {
        return new DialogManagerImpl(mFragment.getActivity());
    }

    @FragmentScope
    @Provides
    public RequestRepository provideRequestRepository(
            RequestRemoteDataSource requestRemoteDataSource) {
        return new RequestRepository(requestRemoteDataSource);
    }

    @FragmentScope
    @Provides
    public UserRepository provideUserRepository(UserLocalDataSource userLocalDataSource,
            UserRemoteDataSource userRemoteDataSource) {
        return new UserRepository(userLocalDataSource, userRemoteDataSource);
    }
}
