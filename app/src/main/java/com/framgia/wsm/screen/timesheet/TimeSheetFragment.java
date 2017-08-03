package com.framgia.wsm.screen.timesheet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentTimeSheetBinding;
import com.framgia.wsm.screen.BaseFragment;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * TimeSheet Screen.
 */
public class TimeSheetFragment extends BaseFragment {
    public static final String TAG = "TimeSheetFragment";

    @Inject
    TimeSheetContract.ViewModel mViewModel;

    public static TimeSheetFragment newInstance() {
        return new TimeSheetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        DaggerTimeSheetComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .timeSheetModule(new TimeSheetModule(this))
                .build()
                .inject(this);

        FragmentTimeSheetBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_time_sheet, container, false);
        binding.setViewModel((TimeSheetViewModel) mViewModel);
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            return;
        }
        mViewModel.onReloadData();
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
    public void onResume() {
        super.onResume();
        mViewModel.onReloadData();
    }
}
