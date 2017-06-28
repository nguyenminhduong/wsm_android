package com.framgia.wsm.screen.holidaycalendar;

import android.databinding.BaseObservable;
import com.framgia.wsm.data.model.HolidayCalendar;
import com.framgia.wsm.data.model.HolidayCalendarDate;
import com.framgia.wsm.widget.holidaycalendar.HolidayDateClickListener;
import java.util.List;

public class ItemHolidayCalendarViewModel extends BaseObservable {
    private HolidayCalendar mHolidayCalendar;
    private HolidayDateClickListener mHolidayDateClickListener;

    public ItemHolidayCalendarViewModel(HolidayCalendar holidayCalendar,
            HolidayDateClickListener holidayDateClickListener) {
        mHolidayCalendar = holidayCalendar;
        mHolidayDateClickListener = holidayDateClickListener;
    }

    public int getYear() {
        return mHolidayCalendar.getYear();
    }

    public int getMonth() {
        return mHolidayCalendar.getMonth();
    }

    public List<HolidayCalendarDate> getListHolidayDate() {
        return mHolidayCalendar.getHolidayCalendarDates();
    }

    public void onDayClicked(HolidayCalendarDate holidayCalendarDate) {
        if (mHolidayDateClickListener == null) {
            return;
        }
        mHolidayDateClickListener.onDayClick(holidayCalendarDate);
    }
}
