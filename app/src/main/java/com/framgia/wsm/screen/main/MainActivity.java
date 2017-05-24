package com.framgia.wsm.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityMainBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * Main Screen.
 */
public class MainActivity extends BaseActivity {
    private static final int DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000;

    @Inject
    MainContract.ViewModel mViewModel;
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

        initView();
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

    public void initView() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel((MainViewModel) mViewModel);

        Toolbar toolbar = binding.toolbar;
        DrawerLayout drawerLayout = binding.drawerLayout;

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mIsDoubleTapBack = false;
            }
        };
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
}
