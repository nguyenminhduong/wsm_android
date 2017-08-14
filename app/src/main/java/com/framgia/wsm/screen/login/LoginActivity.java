package com.framgia.wsm.screen.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityLoginBinding;
import com.framgia.wsm.screen.createnewpassword.CreateNewPasswordActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import javax.inject.Inject;

/**
 * Login Screen.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TOKEN_RESET_PASSWORD = "reset_password_token";

    @Inject
    LoginContract.ViewModel mViewModel;
    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerLoginComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);

        ActivityLoginBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setViewModel((LoginViewModel) mViewModel);
        getTokenResetPassword(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getTokenResetPassword(intent);
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    private void getTokenResetPassword(Intent intent) {
        Uri urlResetPassword = intent.getData();
        String tokenResetPassword;
        if (urlResetPassword != null) {
            tokenResetPassword = urlResetPassword.getQueryParameter(TOKEN_RESET_PASSWORD);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.EXTRA_TOKEN_RESET_PASSWORD, tokenResetPassword);
            mNavigator.startActivity(CreateNewPasswordActivity.class, bundle);
            mNavigator.finishActivity();
        }
    }
}
