package com.framgia.wsm.screen.createnewpassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityCreateNewPasswordBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * CreateNewPassword Screen.
 */
public class CreateNewPasswordActivity extends BaseActivity {

    @Inject
    CreateNewPasswordContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerCreateNewPasswordComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .createNewPasswordModule(new CreateNewPasswordModule(this))
                .build()
                .inject(this);

        ActivityCreateNewPasswordBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_new_password);
        binding.setViewModel((CreateNewPasswordViewModel) mViewModel);
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
