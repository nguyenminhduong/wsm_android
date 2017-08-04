package com.framgia.wsm.screen.statisticsbymonth;

/**
 * Exposes the data to be used in the Statisticsbymonth screen.
 */

public class StatisticsByMonthViewModel implements StatisticsByMonthContract.ViewModel {

    private StatisticsByMonthContract.Presenter mPresenter;

    StatisticsByMonthViewModel(StatisticsByMonthContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }
}
