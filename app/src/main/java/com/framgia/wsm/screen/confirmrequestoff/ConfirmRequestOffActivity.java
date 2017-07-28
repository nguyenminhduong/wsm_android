package com.framgia.wsm.screen.confirmrequestoff;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.event.UnauthorizedEvent;
import com.framgia.wsm.databinding.ActivityConfirmRequestOffBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.widget.dialog.DialogManager;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * ConfirmRequestOff Screen.
 */
public class ConfirmRequestOffActivity extends BaseActivity {

    @Inject
    ConfirmRequestOffContract.ViewModel mViewModel;
    @Inject
    DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerConfirmRequestOffComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .confirmRequestOffModule(new ConfirmRequestOffModule(this))
                .build()
                .inject(this);

        ActivityConfirmRequestOffBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_confirm_request_off);
        binding.setViewModel((ConfirmRequestOffViewModel) mViewModel);
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
        if (requestCode == Constant.RequestCode.REQUEST_OFF && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnauthorizedEvent event) {
        mDialogManager.showDialogUnauthorized();
    }
}
