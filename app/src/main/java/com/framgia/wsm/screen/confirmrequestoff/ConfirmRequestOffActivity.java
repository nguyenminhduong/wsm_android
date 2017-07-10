package com.framgia.wsm.screen.confirmrequestoff;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityConfirmRequestOffBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
import javax.inject.Inject;

/**
 * ConfirmRequestOff Screen.
 */
public class ConfirmRequestOffActivity extends BaseActivity {

    @Inject
    ConfirmRequestOffContract.ViewModel mViewModel;

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
}
