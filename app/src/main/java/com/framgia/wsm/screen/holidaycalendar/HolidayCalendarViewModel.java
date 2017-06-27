package com.framgia.wsm.screen.holidaycalendar;

/**
 * Exposes the data to be used in the HolidayCalendar screen.
 */

public class HolidayCalendarViewModel implements HolidayCalendarContract.ViewModel {

    private HolidayCalendarContract.Presenter mPresenter;

    HolidayCalendarViewModel(HolidayCalendarContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
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
