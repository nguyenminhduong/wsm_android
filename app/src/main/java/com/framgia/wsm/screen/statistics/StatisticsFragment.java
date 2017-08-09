package com.framgia.wsm.screen.statistics;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentStatisticsBinding;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * Statistics Screen.
 */
public class StatisticsFragment extends Fragment {
    public static final String TAG = "StatisticsFragment";

    @Inject
    StatisticsContract.ViewModel mViewModel;

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        DaggerStatisticsComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .statisticsModule(new StatisticsModule(this))
                .build()
                .inject(this);

        FragmentStatisticsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);
        binding.setViewModel((StatisticsViewModel) mViewModel);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            return;
        }
        mViewModel.onReloadData();
    }
}
