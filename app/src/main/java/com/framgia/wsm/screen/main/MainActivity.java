package com.framgia.wsm.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import com.framgia.wsm.MainApplication;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.ActivityMainBinding;
import com.framgia.wsm.screen.BaseActivity;
import javax.inject.Inject;

/**
 * Main Screen.
 */
public class MainActivity extends BaseActivity {

    @Inject
    MainContract.ViewModel mViewModel;

    private MainComponent mMainComponent;

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

    public MainComponent getMainComponent() {
        return mMainComponent;
    }
}
