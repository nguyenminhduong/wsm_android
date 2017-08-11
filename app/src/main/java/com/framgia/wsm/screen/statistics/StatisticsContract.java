package com.framgia.wsm.screen.statistics;

import com.framgia.wsm.data.model.Chart;
import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface StatisticsContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetStatisticsSuccess(List<Chart> statisticOfCharts);

        void onGetStatisticsError(BaseException error);

        void onGetStatisticsByMonthSuccess(StatisticOfPersonal statisticOfPersonal);

        void onGetStatisticsByYearSuccess(StatisticOfPersonal statisticOfPersonal);

        void onShowIndeterminateProgressDialog();

        void onDismissProgressDialog();

        void onReloadData();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getStatistics(int year);
    }
}
