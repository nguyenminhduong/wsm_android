package com.framgia.wsm.screen.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.local.UserLocalDataSource;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.FragmentScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import com.framgia.wsm.widget.dialog.DialogManager;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link SettingProfilePresenter}.
 */
@Module
public class SettingProfileModule {

    private Fragment mFragment;

    public SettingProfileModule(@NonNull Fragment fragment) {
        mFragment = fragment;
    }

    @FragmentScope
    @Provides
    public SettingProfileContract.ViewModel provideViewModel(Context context,
            SettingProfileContract.Presenter presenter, DialogManager dialogManager,
            Navigator navigator) {
        return new SettingProfileViewModel(context, presenter, dialogManager, navigator);
    }

    @FragmentScope
    @Provides
    public SettingProfileContract.Presenter providePresenter(UserRepository userRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        return new SettingProfilePresenter(userRepository, baseSchedulerProvider);
    }

    @FragmentScope
    @Provides
    UserRepository provideUserRepository(UserLocalDataSource localDataSource,
            UserRemoteDataSource remoteDataSource) {
        return new UserRepository(localDataSource, remoteDataSource);
    }

    @FragmentScope
    @Provides
    Navigator provideNavigator() {
        return new Navigator(mFragment);
    }
}
