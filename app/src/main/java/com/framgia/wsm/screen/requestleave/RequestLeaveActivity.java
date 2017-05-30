package com.framgia.wsm.screen.requestleave;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityRequestleaveBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * RequestLeave Screen.
 */
public class RequestLeaveActivity extends BaseActivity {

    @Inject
    RequestLeaveContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerRequestLeaveComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .requestLeaveModule(new RequestLeaveModule(this))
                .build()
                .inject(this);

        ActivityRequestleaveBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_requestleave);
        binding.setViewModel((RequestLeaveViewModel) mViewModel);
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
