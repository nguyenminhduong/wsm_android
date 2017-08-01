package com.framgia.wsm.screen.listrequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.RequestRepository;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.RequestRemoteDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.framgia.wsm.widget.dialog.DialogManagerImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ListRequestPresenter}.
 */
@Module
public class ListRequestModule {

    private Fragment mFragment;

    public ListRequestModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public ListRequestContract.ViewModel provideViewModel(Context context,
            ListRequestContract.Presenter presenter,
            @Named("ListRequest") DialogManager dialogManager,
            ListRequestAdapter listRequestAdapter, Navigator navigator) {
        ListRequestViewModel viewModel =
                new ListRequestViewModel(context, presenter, dialogManager, listRequestAdapter,
                        navigator);
        viewModel.setRequestType(mFragment.getArguments().getInt(Constant.EXTRA_REQUEST_TYPE));
        return viewModel;
    }

    @FragmentScope
    @Provides
    public ListRequestContract.Presenter providePresenter(RequestRepository requestRepository,
            UserRepository userRepository, BaseSchedulerProvider baseSchedulerProvider) {
        return new ListRequestPresenter(requestRepository, userRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    @Named("ListRequest")
    public DialogManager provideDialogManager() {
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
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(userLocalDataSource, remoteDataSource);
    }

    @FragmentScope
    @Provides
    public ListRequestAdapter provideListRequestAdapter() {
        int mRequestType = mFragment.getArguments().getInt(Constant.EXTRA_REQUEST_TYPE);
        return new ListRequestAdapter(mFragment.getActivity(), mRequestType);
    }

    @FragmentScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator(mFragment);
    }
}
