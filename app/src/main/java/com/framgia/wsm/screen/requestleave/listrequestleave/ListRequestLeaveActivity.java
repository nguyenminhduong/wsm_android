package com.framgia.wsm.screen.requestleave.listrequestleave;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityListRequestLeaveBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * ListRequestLeave Screen.
 */
public class ListRequestLeaveActivity extends BaseActivity {

    @Inject
    ListRequestLeaveContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerListRequestLeaveComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .listRequestLeaveModule(new ListRequestLeaveModule(this))
                .build()
                .inject(this);

        ActivityListRequestLeaveBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_list_request_leave);
        binding.setViewModel((ListRequestLeaveViewModel) mViewModel);
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
