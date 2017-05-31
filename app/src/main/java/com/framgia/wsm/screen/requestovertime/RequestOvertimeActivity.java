package com.framgia.wsm.screen.requestovertime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityRequestOvertimeBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * RequestOvertime Screen.
 */
public class RequestOvertimeActivity extends BaseActivity {

    @Inject
    RequestOvertimeContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerRequestOvertimeComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .requestOvertimeModule(new RequestOvertimeModule(this))
                .build()
                .inject(this);

        ActivityRequestOvertimeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_request_overtime);
        binding.setViewModel((RequestOvertimeViewModel) mViewModel);
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
