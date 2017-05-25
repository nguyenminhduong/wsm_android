package com.framgia.wsm.screen.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.framgia.wsm.data.source.UserRepository;
import com.framgia.wsm.data.source.remote.UserRemoteDataSource;
import com.framgia.wsm.utils.dagger.ActivityScope;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Validator;
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
    public LoginContract.ViewModel provideViewModel(Context context,
            LoginContract.Presenter presenter, Navigator navigator) {
        return new LoginViewModel(context, presenter, navigator);
    }

    @ActivityScope
    @Provides
    public LoginContract.Presenter providePresenter(UserRepository userRepository,
            Validator validator) {
        return new LoginPresenter(userRepository, validator);
    }

    @ActivityScope
    @Provides
    public UserRepository provideUserRepository(UserRemoteDataSource remoteDataSource) {
        return new UserRepository(null, remoteDataSource);
    }

    @ActivityScope
    @Provides
    public Navigator provideNavigator() {
        return new Navigator(mActivity);
    }

    @ActivityScope
    @Provides
    public Validator provideValidator() {
        return new Validator(mActivity.getApplicationContext(), LoginViewModel.class);
    }
}
