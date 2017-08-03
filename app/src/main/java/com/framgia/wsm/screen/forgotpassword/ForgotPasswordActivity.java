package com.framgia.wsm.screen.forgotpassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityForgotPasswordBinding;
import javax.inject.Inject;

/**
 * forgotpassword Screen.
 */
public class ForgotPasswordActivity extends AppCompatActivity {

    @Inject
    ForgotPasswordContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerForgotPasswordComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .forgotPasswordModule(new ForgotPasswordModule(this))
                .build()
                .inject(this);

        ActivityForgotPasswordBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        binding.setViewModel((ForgotPasswordViewModel) mViewModel);
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
