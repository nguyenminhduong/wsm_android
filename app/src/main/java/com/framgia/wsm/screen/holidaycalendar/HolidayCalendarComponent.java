package com.framgia.wsm.screen.holidaycalendar;

import com.framgia.wsm.AppComponent;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * This is a Dagger component. Refer to
 * {@link com.framgia.wsm.screen.holidaycalendar.MainApplication}
 * for the list of Dagger components
 * used in this application.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = HolidayCalendarModule.class)
public interface HolidayCalendarComponent {
    void inject(HolidayCalendarFragment holidaycalendarFragment);
}
