package com.framgia.wsm.screen.main;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.utils.Constant;

/**
 * Exposes the data to be used in the Main screen.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private static final int PAGE_LIMIT = 8;
    private MainContract.Presenter mPresenter;
    private String mStatusDrawerLayout;
    private User mUser;
    private int mCurrentItem;
    @Page
    private int mCurrentPage;
    private MainViewPagerAdapter mViewPagerAdapter;

    MainViewModel(MainContract.Presenter presenter, MainViewPagerAdapter viewPagerAdapter) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mViewPagerAdapter = viewPagerAdapter;
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
    public int getCurrentPage() {
        return mCurrentPage;
    }

    @Bindable
    public int getCurrentItem() {
        return mCurrentItem;
    }

    public MainViewPagerAdapter getViewPagerAdapter() {
        return mViewPagerAdapter;
    }

    private void setCurrentPageItem(@Page int currentPage, int currentItem) {
        mCurrentPage = currentPage;
        notifyPropertyChanged(BR.currentPage);
        mCurrentItem = currentItem;
        notifyPropertyChanged(BR.currentItem);
    }

    public boolean onItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_personal:
                setCurrentPageItem(Page.PERSONAL, R.id.item_personal);
                break;
            case R.id.item_setup_profile:
                setCurrentPageItem(Page.SETUP_PROFILE, R.id.item_setup_profile);
                break;
            case R.id.item_working_calendar:
                setCurrentPageItem(Page.WORKING_CALENDAR, R.id.item_working_calendar);
                break;
            case R.id.item_holiday_calendar:
                setCurrentPageItem(Page.HOLIDAY_CALENDAR, R.id.item_holiday_calendar);
                break;
            case R.id.item_statistic_of_personal:
                setCurrentPageItem(Page.STATISTIC_OF_PERSONAL, R.id.item_statistic_of_personal);
                break;
            case R.id.item_overtime:
                setCurrentPageItem(Page.OVERTIME, R.id.item_overtime);
                break;
            case R.id.item_come_late_leave_early:
                setCurrentPageItem(Page.COME_LATE_LEAVE_EARLY, R.id.item_come_late_leave_early);
                break;
            case R.id.item_workspace_data:
                setCurrentPageItem(Page.WORKSPACE_DATA, R.id.item_workspace_data);
                break;
            default:
                break;
        }
        setStatusDrawerLayout(Constant.DRAWER_IS_CLOSE);
        return true;
    }

    @Bindable
    public String getStatusDrawerLayout() {
        return mStatusDrawerLayout;
    }

    private void setStatusDrawerLayout(String statusDrawerLayout) {
        mStatusDrawerLayout = statusDrawerLayout;
        notifyPropertyChanged(BR.statusDrawerLayout);
    }

    public String getUsername() {
        return mUser != null ? mUser.getName() : "";
    }

    public String getEmail() {
        return mUser != null ? mUser.getEmail() : "";
    }

    @Override
    public boolean onBackPressed() {
        if (mStatusDrawerLayout.equals(Constant.DRAWER_IS_OPEN)) {
            setStatusDrawerLayout(Constant.DRAWER_IS_CLOSE);
            return true;
        } else {
            Fragment fragment = mViewPagerAdapter.getCurrentFragment();
            if (fragment == mViewPagerAdapter.getFragment(Page.WORKING_CALENDAR)) {
                MainContainerFragment containerFragment = (MainContainerFragment) fragment;
                return containerFragment.onBackPressed();
            } else {
                if (fragment instanceof MainContainerFragment) {
                    MainContainerFragment containerFragment = (MainContainerFragment) fragment;
                    boolean isCanBack = containerFragment.onBackPressed();
                    if (!isCanBack) {
                        setCurrentPageItem(Page.WORKING_CALENDAR, R.id.item_working_calendar);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @IntDef({
            Page.PERSONAL, Page.SETUP_PROFILE, Page.WORKING_CALENDAR, Page.HOLIDAY_CALENDAR,
            Page.STATISTIC_OF_PERSONAL, Page.OVERTIME, Page.COME_LATE_LEAVE_EARLY,
            Page.WORKSPACE_DATA
    })
    @interface Page {
        int PERSONAL = 0;
        int SETUP_PROFILE = 1;
        int WORKING_CALENDAR = 2;
        int HOLIDAY_CALENDAR = 3;
        int STATISTIC_OF_PERSONAL = 4;
        int OVERTIME = 5;
        int COME_LATE_LEAVE_EARLY = 6;
        int WORKSPACE_DATA = 7;
    }

    public void onDrawerIsOpen(View view) {
        setStatusDrawerLayout(Constant.DRAWER_IS_OPEN);
    }
}
