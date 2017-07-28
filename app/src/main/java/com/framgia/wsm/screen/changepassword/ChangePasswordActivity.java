package com.framgia.wsm.screen.changepassword;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.event.UnauthorizedEvent;
import com.framgia.wsm.databinding.ActivityChangePasswordBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.widget.dialog.DialogManager;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * changepassword Screen.
 */
public class ChangePasswordActivity extends BaseActivity {

    @Inject
    ChangePasswordContract.ViewModel mViewModel;
    @Inject
    DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerChangePasswordComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .changePasswordModule(new ChangePasswordModule(this))
                .build()
                .inject(this);

        ActivityChangePasswordBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.setViewModel((ChangePasswordViewModel) mViewModel);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnauthorizedEvent event) {
        mDialogManager.showDialogUnauthorized();
    }
}
