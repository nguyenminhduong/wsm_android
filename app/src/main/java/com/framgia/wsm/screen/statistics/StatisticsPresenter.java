package com.framgia.wsm.screen.statistics;

import com.framgia.wsm.data.source.StatisticsRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.data.source.remote.api.response.StatisticsResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link StatisticsFragment}), retrieves the data and updates
 * the UI as required.
 */
final class StatisticsPresenter implements StatisticsContract.Presenter {
    private static final String TAG = StatisticsPresenter.class.getName();

    private StatisticsContract.ViewModel mViewModel;
    private StatisticsRepository mStatisticsRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    StatisticsPresenter(StatisticsRepository statisticsRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mStatisticsRepository = statisticsRepository;
        mBaseSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(StatisticsContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getStatistics(int year) {
        Disposable disposable = mStatisticsRepository.getStatistic(year)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mViewModel.onShowIndeterminateProgressDialog();
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        mViewModel.onDismissProgressDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<StatisticsResponse>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<StatisticsResponse> response)
                            throws Exception {
                        if (response.getData() != null) {
                            mViewModel.onGetStatisticsSuccess(
                                    response.getData().getStatisticOfChart());
                            mViewModel.onGetStatisticsByMonthSuccess(
                                    response.getData().getStatisticOfPersonal());
                            mViewModel.onGetStatisticsByYearSuccess(
                                    response.getData().getStatisticOfPersonal());
                        }
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetStatisticsError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
