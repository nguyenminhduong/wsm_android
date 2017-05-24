package com.framgia.wsm.screen.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;

/**
 * Exposes the data to be used in the Main screen.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private static final int PAGE_LIMIT = 8;
    private MainContract.Presenter mPresenter;
    private boolean mIsDrawerLayoutClose;
    private User mUser;
    @Item
    private int mCurrentItem;

    MainViewModel(MainContract.Presenter presenter) {
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

    public int getPageLimit() {
        return PAGE_LIMIT;
    }

    @Bindable
    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setCurrentItem(@Item int item) {
        mCurrentItem = item;
        notifyPropertyChanged(BR.currentItem);
    }

    public boolean onItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_personal:
                setCurrentItem(Item.PERSONAL);
                break;
            case R.id.item_setup_profile:
                setCurrentItem(Item.SETUP_PROFILE);
                break;
            case R.id.item_working_calendar:
                setCurrentItem(Item.WORKING_CALENDAR);
                break;
            case R.id.item_holiday_calendar:
                setCurrentItem(Item.HOLIDAY_CALENDAR);
                break;
            case R.id.item_statistic_of_personal:
                setCurrentItem(Item.STATISTIC_OF_PERSONAL);
                break;
            case R.id.item_overtime:
                setCurrentItem(Item.OVERTIME);
                break;
            case R.id.item_come_late_leave_early:
                setCurrentItem(Item.COME_LATE_LEAVE_EARLY);
                break;
            case R.id.item_workspace_data:
                setCurrentItem(Item.WORKSPACE_DATA);
                break;
            default:
                setCurrentItem(Item.WORKING_CALENDAR);
                break;
        }
        setDrawerLayoutClose(true);
        return true;
    }

    @Bindable
    public boolean isDrawerLayoutClose() {
        return mIsDrawerLayoutClose;
    }

    private void setDrawerLayoutClose(boolean drawerLayoutClose) {
        mIsDrawerLayoutClose = drawerLayoutClose;
        notifyPropertyChanged(BR.drawerLayoutClose);
    }

    public String getUsername() {
        return mUser != null ? mUser.getName() : "";
    }

    public String getEmail() {
        return mUser != null ? mUser.getEmail() : "";
    }

    @IntDef({
            Item.PERSONAL, Item.SETUP_PROFILE, Item.WORKING_CALENDAR, Item.HOLIDAY_CALENDAR,
            Item.STATISTIC_OF_PERSONAL, Item.OVERTIME, Item.COME_LATE_LEAVE_EARLY,
            Item.WORKSPACE_DATA
    })
    private @interface Item {
        int PERSONAL = 0;
        int SETUP_PROFILE = 1;
        int WORKING_CALENDAR = 2;
        int HOLIDAY_CALENDAR = 3;
        int STATISTIC_OF_PERSONAL = 4;
        int OVERTIME = 5;
        int COME_LATE_LEAVE_EARLY = 6;
        int WORKSPACE_DATA = 7;
    }

    @Override
    public boolean onBackPressed() {
        //TODO change late
        return false;
    }
}
