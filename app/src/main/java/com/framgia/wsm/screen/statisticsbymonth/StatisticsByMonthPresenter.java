package com.framgia.wsm.screen.statisticsbymonth;

/**
 * Listens to user actions from the UI ({@link StatisticsByMonthFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class StatisticsByMonthPresenter implements StatisticsByMonthContract.Presenter {
    private static final String TAG = StatisticsByMonthPresenter.class.getName();

    private StatisticsByMonthContract.ViewModel mViewModel;

    StatisticsByMonthPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(StatisticsByMonthContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
