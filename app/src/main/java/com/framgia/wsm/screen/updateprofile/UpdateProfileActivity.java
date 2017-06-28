package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityUpdateProfileBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == Constant.RequestCode.REQUEST_SELECT_AVATAR) {
            Uri uriAvatar = data.getData();
            mViewModel.setAvatarUser(uriAvatar.toString());
        }
    }
}
