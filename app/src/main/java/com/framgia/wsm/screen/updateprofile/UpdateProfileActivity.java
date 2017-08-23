package com.framgia.wsm.screen.updateprofile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.event.UnauthorizedEvent;
import com.framgia.wsm.databinding.ActivityUpdateProfileBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.widget.dialog.DialogManager;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Updateprofile Screen.
 */
public class UpdateProfileActivity extends BaseActivity {

    @Inject
    UpdateProfileContract.ViewModel mViewModel;
    @Inject
    DialogManager mDialogManager;

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
        if (requestCode == Constant.RequestCode.REQUEST_SELECT_AVATAR
                && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                return;
            }
            Uri uriAvatar = data.getData();
            mViewModel.setAvatarUser(uriAvatar);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
            @NonNull int[] grantResults) {
        if (requestCode != Constant.RequestCode.REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mViewModel.pickAvatarUser();
        } else {
            //TODO show toast message access denied
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnauthorizedEvent event) {
        mDialogManager.showDialogUnauthorized();
    }
}
