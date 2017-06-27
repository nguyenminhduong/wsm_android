package com.framgia.wsm.screen.holidaycalendar;

/**
 * Listens to user actions from the UI ({@link HolidayCalendarFragment}), retrieves the data and
 * updates
 * the UI as required.
 */
final class HolidayCalendarPresenter implements HolidayCalendarContract.Presenter {
    private static final String TAG = HolidayCalendarPresenter.class.getName();

    private HolidayCalendarContract.ViewModel mViewModel;

    HolidayCalendarPresenter() {
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
}
