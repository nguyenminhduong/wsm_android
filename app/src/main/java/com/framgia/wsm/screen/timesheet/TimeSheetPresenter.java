package com.framgia.wsm.screen.timesheet;

import com.framgia.wsm.data.source.TimeSheetRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.TimeSheetResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Listens to user actions from the UI ({@link TimeSheetFragment}), retrieves the data and updates
 * the UI as required.
 */
final class TimeSheetPresenter implements TimeSheetContract.Presenter {
    private static final String TAG = TimeSheetPresenter.class.getName();

    private TimeSheetContract.ViewModel mViewModel;
    private TimeSheetRepository mTimeSheetRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mSchedulerProvider;

    TimeSheetPresenter(TimeSheetRepository timeSheetRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mTimeSheetRepository = timeSheetRepository;
        mCompositeDisposable = new CompositeDisposable();
        mSchedulerProvider = baseSchedulerProvider;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void setViewModel(TimeSheetContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getTimeSheet(int month, int year) {
        Disposable subscription = mTimeSheetRepository.getTimeSheet(month, year)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<TimeSheetResponse>() {
                    @Override
                    public void accept(@NonNull TimeSheetResponse timeSheetResponse)
                            throws Exception {
                        mViewModel.onGetTimeSheetSuccess(timeSheetResponse.getTimeSheetDates());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetTimeSheetError(error);
                    }
                });
        mCompositeDisposable.add(subscription);
    }
}
