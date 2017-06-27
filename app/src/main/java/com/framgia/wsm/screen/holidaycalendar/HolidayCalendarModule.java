package com.framgia.wsm.screen.holidaycalendar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.framgia.wsm.utils.dagger.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to
 * the {@link HolidayCalendarPresenter}.
 */
@Module
public class HolidayCalendarModule {

    private Activity mActivity;

    public HolidayCalendarModule(@NonNull Fragment fragment) {
        this.mActivity = fragment.getActivity();
    }

    @ActivityScope
    @Provides
    public HolidayCalendarContract.ViewModel provideViewModel(
            HolidayCalendarContract.Presenter presenter) {
        return new HolidayCalendarViewModel(presenter);
    }

    @ActivityScope
    @Provides
    public HolidayCalendarContract.Presenter providePresenter() {
        return new HolidayCalendarPresenter();
    }
}
