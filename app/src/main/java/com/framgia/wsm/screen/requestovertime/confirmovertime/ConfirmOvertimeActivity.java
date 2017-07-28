package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.event.UnauthorizedEvent;
import com.framgia.wsm.databinding.ActivityConfirmOvertimeBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.widget.dialog.DialogManager;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * ConfirmOvertime Screen.
 */
public class ConfirmOvertimeActivity extends BaseActivity {

    @Inject
    ConfirmOvertimeContract.ViewModel mViewModel;

    @Inject
    DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerConfirmOvertimeComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .confirmOvertimeModule(new ConfirmOvertimeModule(this))
                .build()
                .inject(this);

        ActivityConfirmOvertimeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_confirm_overtime);
        binding.setViewModel((ConfirmOvertimeViewModel) mViewModel);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestCode.REQUEST_OVERTIME && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnauthorizedEvent event) {
        mDialogManager.showDialogUnauthorized();
    }
}
