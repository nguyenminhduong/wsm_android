package com.framgia.wsm.screen.statisticsbyyear;

/**
 * Exposes the data to be used in the Statisticsbyyear screen.
 */

public class StatisticsByYearViewModel implements StatisticsByYearContract.ViewModel {

    private StatisticsByYearContract.Presenter mPresenter;

    StatisticsByYearViewModel(StatisticsByYearContract.Presenter presenter) {
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
