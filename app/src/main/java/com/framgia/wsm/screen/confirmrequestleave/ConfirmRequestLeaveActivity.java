package com.framgia.wsm.screen.confirmrequestleave;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityConfirmRequestLeaveBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
import javax.inject.Inject;

/**
 * ConfirmRequestLeave Screen.
 */
public class ConfirmRequestLeaveActivity extends BaseActivity {

    @Inject
    ConfirmRequestLeaveContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerConfirmRequestLeaveComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .confirmRequestLeaveModule(new ConfirmRequestLeaveModule(this))
                .build()
                .inject(this);

        ActivityConfirmRequestLeaveBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_confirm_request_leave);
        binding.setViewModel((ConfirmRequestLeaveViewModel) mViewModel);
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
        if (requestCode == Constant.RequestCode.REQUEST_LEAVE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
