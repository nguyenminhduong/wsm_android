package com.framgia.wsm.screen.statisticsbyyear;

/**
 * Listens to user actions from the UI ({@link StatisticsByYearFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class StatisticsByYearPresenter implements StatisticsByYearContract.Presenter {
    private static final String TAG = StatisticsByYearPresenter.class.getName();

    private StatisticsByYearContract.ViewModel mViewModel;

    StatisticsByYearPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(StatisticsByYearContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
