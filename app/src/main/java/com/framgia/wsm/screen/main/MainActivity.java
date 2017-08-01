package com.framgia.wsm.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.data.event.UnauthorizedEvent;
import com.framgia.wsm.databinding.ActivityMainBinding;
import com.framgia.wsm.screen.BaseActivity;
import com.framgia.wsm.screen.notification.NotificationDialogFragment;
import com.framgia.wsm.screen.profile.ProfileFragment;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.widget.dialog.DialogManager;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.framgia.wsm.utils.Constant.EXTRA_NOTIFICATION_REQUEST_TYPE;

/**
 * Main Screen.
 */
public class MainActivity extends BaseActivity implements ProfileFragment.UpdateAvatarListener,
        NotificationDialogFragment.UpdateNotificationListener {
    private static final int DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000;

    @Inject
    MainContract.ViewModel mViewModel;
    @Inject
    DialogManager mDialogManager;

    private MainComponent mMainComponent;
    private Handler mHandler;
    private Runnable mRunnable;
    private boolean mIsDoubleTapBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainComponent = DaggerMainComponent.builder()
                .appComponent(((MainApplication) getApplication()).getAppComponent())
                .mainModule(new MainModule(this))
                .build();
        mMainComponent.inject(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel((MainViewModel) mViewModel);
        ((MainViewModel) mViewModel).setNotificationRequestType(
                getIntent().getStringExtra(EXTRA_NOTIFICATION_REQUEST_TYPE));
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mIsDoubleTapBack = false;
            }
        };
    }

    public boolean isOpenAppByClickNotification() {
        return StringUtils.isNotBlank(getIntent().getStringExtra(EXTRA_NOTIFICATION_REQUEST_TYPE));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        mHandler.removeCallbacks(mRunnable);
        super.onStop();
    }

    public MainComponent getMainComponent() {
        return mMainComponent;
    }

    @Override
    public void onBackPressed() {
        if (mViewModel.onBackPressed()) {
            return;
        }
        if (mIsDoubleTapBack) {
            super.onBackPressed();
            return;
        }
        mIsDoubleTapBack = true;
        Toast.makeText(this, getString(R.string.please_click_back_again_to_exit),
                Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, DELAY_TIME_TWO_TAP_BACK_BUTTON);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case Constant.RequestCode.REQUEST_OFF:
                mViewModel.goNextFragmentListRequestOff();
                break;
            case Constant.RequestCode.REQUEST_OVERTIME:
                mViewModel.goNextFragmentListRequestOverTime();
                break;
            case Constant.RequestCode.REQUEST_LEAVE:
                mViewModel.goNextFragmentListRequestLeave();
                break;
            case Constant.RequestCode.PROFILE_USER:
                mViewModel.goNextFragmentPersonalInformation();
                break;
            default:
                break;
        }
    }

    @Override
    public void onUpdateAvatar() {
        mViewModel.onReloadDataUser();
    }

    @Override
    public void onUpdateNotificationReadAll() {
        mViewModel.updateNotificationUnRead();
    }

    @Override
    public void onClickNotification(String trackableType, int permissionType) {
        mViewModel.handleClickNotification(trackableType, permissionType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UnauthorizedEvent event) {
        mDialogManager.showDialogUnauthorized();
    }
}
