package com.framgia.wsm.screen.setting;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentSettingProfileBinding;
import com.framgia.wsm.screen.BaseFragment;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingProfileFragment extends BaseFragment {

    public static final String TAG = "SettingProfileFragment";

    @Inject
    SettingProfileContract.ViewModel mViewModel;

    public static SettingProfileFragment newInstance() {
        return new SettingProfileFragment();
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container,
            @NonNull Bundle savedInstanceState) {

        DaggerSettingProfileComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .settingProfileModule(new SettingProfileModule(this))
                .build()
                .inject(this);

        FragmentSettingProfileBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_setting_profile, container,
                        false);
        binding.setViewModel((SettingProfileViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStart();
        super.onStop();
    }
}
