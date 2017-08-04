package com.framgia.wsm.screen.statisticsbymonth;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.databinding.FragmentStatisticsByMonthBinding;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * Statisticsbymonth Screen.
 */
public class StatisticsByMonthFragment extends Fragment {

    @Inject
    StatisticsByMonthContract.ViewModel mViewModel;

    public static StatisticsByMonthFragment newInstance() {
        return new StatisticsByMonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        DaggerStatisticsByMonthComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .statisticsByMonthModule(new StatisticsByMonthModule(this))
                .build()
                .inject(this);

        FragmentStatisticsByMonthBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_statistics_by_month, container,
                        false);
        binding.setViewModel((StatisticsByMonthViewModel) mViewModel);
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
}
