package com.framgia.wsm.screen.statisticsbymonth;

import com.framgia.wsm.data.model.StatisticOfPersonal;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface StatisticsByMonthContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {

        void fillData(StatisticOfPersonal statistic);

        void onGetUserSuccess(User user);

        void onGetUserError(BaseException error);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
    }
}
