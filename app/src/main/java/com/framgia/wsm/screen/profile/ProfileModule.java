package com.framgia.wsm.screen.profile;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.screen.profile.branch.BranchAdapter;
import com.framgia.wsm.screen.profile.group.GroupAdapter;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link ProfilePresenter}.
 */
@Module
public class ProfileModule {

    private Fragment mFragment;

    public ProfileModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public ProfileContract.ViewModel provideViewModel(ProfileContract.Presenter presenter,
            Navigator navigator, BranchAdapter branchAdapter, GroupAdapter groupAdapter) {
        return new ProfileViewModel(presenter, navigator, branchAdapter, groupAdapter);
    }

    @FragmentScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mFragment);
    }

    @FragmentScope
    @Provides
    public ProfileContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new ProfilePresenter(userRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @FragmentScope
    @Provides
    public BranchAdapter provideBranchAdapter() {
        return new BranchAdapter(mFragment.getActivity());
    }

    @FragmentScope
    @Provides
    public GroupAdapter provideGroupAdapter() {
        return new GroupAdapter(mFragment.getActivity());
    }
}
