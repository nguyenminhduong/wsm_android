package com.framgia.wsm.screen.statistics;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment}), retrieves the data and updates
 * the UI as required.
 */
final class StatisticsPresenter implements StatisticsContract.Presenter {
    private static final String TAG = StatisticsPresenter.class.getName();

    private StatisticsContract.ViewModel mViewModel;

    StatisticsPresenter() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(StatisticsContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }
}
