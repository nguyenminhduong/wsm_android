package com.framgia.wsm.screen.updateprofile;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityUpdateProfileBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * Updateprofile Screen.
 */
public class UpdateProfileActivity extends BaseActivity {

    @Inject
    UpdateProfileContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerUpdateProfileComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .updateProfileModule(new UpdateProfileModule(this))
                .build()
                .inject(this);

        ActivityUpdateProfileBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        binding.setViewModel((UpdateProfileViewModel) mViewModel);
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
