package com.framgia.wsm.screen.statisticsbyyear;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.databinding.FragmentStatisticsByYearBinding;
import com.framgia.wsm.screen.main.MainActivity;
import javax.inject.Inject;

/**
 * Statisticsbyyear Screen.
 */
public class StatisticsByYearFragment extends Fragment {

    @Inject
    StatisticsByYearContract.ViewModel mViewModel;

    public static StatisticsByYearFragment newInstance() {
        return new StatisticsByYearFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        DaggerStatisticsByYearComponent.builder()
                .mainComponent(((MainActivity) getActivity()).getMainComponent())
                .statisticsByYearModule(new StatisticsByYearModule())
                .build()
                .inject(this);

        FragmentStatisticsByYearBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_statistics_by_year, container,
                        false);
        binding.setViewModel((StatisticsByYearViewModel) mViewModel);
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

    public void setStatistic(StatisticOfPersonal statistic) {
        mViewModel.fillData(statistic);
    }
}
