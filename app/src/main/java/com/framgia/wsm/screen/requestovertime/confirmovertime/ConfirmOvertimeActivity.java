package com.framgia.wsm.screen.requestovertime.confirmovertime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityConfirmOvertimeBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * ConfirmOvertime Screen.
 */
public class ConfirmOvertimeActivity extends BaseActivity {

    @Inject
    ConfirmOvertimeContract.ViewModel mViewModel;

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
}
