package com.framgia.wsm.screen.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityLoginBinding;
import javax.inject.Inject;

/**
 * Login Screen.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String URI_RESET_PASSWORD =
            "http://wsm.framgia.vn//users/password/edit?reset_password_token=";

    @Inject
    LoginContract.ViewModel mViewModel;

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

        Intent intent = getIntent();
        Uri urlResetPassword = intent.getData();
        String tokenResetPassword;
        if (urlResetPassword != null) {
            tokenResetPassword = String.valueOf(urlResetPassword).replace(URI_RESET_PASSWORD, "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
