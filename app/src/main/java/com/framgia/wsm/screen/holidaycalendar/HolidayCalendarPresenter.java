package com.framgia.wsm.screen.holidaycalendar;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.source.HolidayCalendarRepository;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.error.RequestError;
import com.framgia.wsm.data.source.remote.api.response.BaseResponse;
import com.framgia.wsm.utils.rx.BaseSchedulerProvider;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Listens to user actions from the UI ({@link HolidayCalendarFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class HolidayCalendarPresenter implements HolidayCalendarContract.Presenter {
    private static final String TAG = HolidayCalendarPresenter.class.getName();

    private HolidayCalendarContract.ViewModel mViewModel;
    private HolidayCalendarRepository mHolidayCalendarRepository;
    private CompositeDisposable mCompositeDisposable;
    private BaseSchedulerProvider mBaseSchedulerProvider;

    HolidayCalendarPresenter(HolidayCalendarRepository holidayCalendarRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mHolidayCalendarRepository = holidayCalendarRepository;
        mBaseSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void setViewModel(HolidayCalendarContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void getHolidayCalendar(int year) {
        Disposable disposable = mHolidayCalendarRepository.getHolidayCalendar(year)
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<BaseResponse<List<HolidayCalendar>>>() {
                    @Override
                    public void accept(@NonNull BaseResponse<List<HolidayCalendar>> response)
                            throws Exception {
                        mViewModel.onGetHolidayCalendarSuccess(response.getData());
                    }
                }, new RequestError() {
                    @Override
                    public void onRequestError(BaseException error) {
                        mViewModel.onGetHolidayCalendarError(error);
                    }
                });
        mCompositeDisposable.add(disposable);
    }
}
