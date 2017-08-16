package com.framgia.wsm.screen.holidaycalendar;

import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BasePresenter;
import com.framgia.wsm.screen.BaseViewModel;
import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface HolidayCalendarContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel {
        void onGetHolidayCalendarSuccess(List<HolidayCalendar> list);

        void onGetHolidayCalendarError(BaseException e);

        void onReloadData();
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter<ViewModel> {
        void getHolidayCalendar(int year);
    }
}
