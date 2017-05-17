package com.framgia.wsm.screen.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link LoginPresenter}.
 */
@Module
public class LoginModule {

    private Activity mActivity;

    public LoginModule(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @ActivityScope
    @Provides
    public LoginContract.ViewModel provideViewModel(LoginContract.Presenter presenter) {
        return new LoginViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public LoginContract.Presenter providePresenter(UserRepository userRepository) {
        return new LoginPresenter(userRepository);
    }

    @ActivityScope
    @Provides
    public UserRepository provideUserRepository(UserRemoteDataSource remoteDataSource) {
        return new UserRepository(null, remoteDataSource);
    }
}
