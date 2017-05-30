package com.framgia.wsm.screen.requestoff;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityRequestOffBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * RequestOff Screen.
 */
public class RequestOffActivity extends BaseActivity {

    @Inject
    RequestOffContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerRequestOffComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .requestOffModule(new RequestOffModule(this))
                .build()
                .inject(this);

        ActivityRequestOffBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_request_off);
        binding.setViewModel((RequestOffViewModel) mViewModel);
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
