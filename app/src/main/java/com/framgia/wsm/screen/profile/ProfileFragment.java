package com.framgia.wsm.screen.profile;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentProfileBinding;
import com.framgia.wsm.screen.BaseFragment;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.Constant;
import javax.inject.Inject;

/**
 * Profile Screen.
 */
public class ProfileFragment extends BaseFragment {

    public static final String TAG = "ProfileFragment";

    @Inject
    ProfileContract.ViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        DaggerProfileComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .profileModule(new ProfileModule(this))
                .build()
                .inject(this);

        FragmentProfileBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setViewModel((ProfileViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    public void onStop() {
        mViewModel.onStop();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestCode.PROFILE_USER && resultCode == Activity.RESULT_OK) {
            mViewModel.reloadData();
        }
    }
}
