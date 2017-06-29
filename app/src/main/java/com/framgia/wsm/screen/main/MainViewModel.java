package com.framgia.wsm.screen.main;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.login.LoginActivity;
import com.framgia.wsm.screen.notification.NotificationDialogFragment;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.NavigateAnim;
import com.framgia.wsm.utils.navigator.Navigator;

/**
 * Exposes the data to be used in the Main screen.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private static final String TAG = "MainActivity";
    private static final int PAGE_LIMIT = 8;

    private MainContract.Presenter mPresenter;
    private String mStatusDrawerLayout;
    private User mUser;
    @Page
    private int mCurrentItem;
    private int mCurrentPage;
    private Navigator mNavigator;
    private MainViewPagerAdapter mViewPagerAdapter;
    private String mCurrentTitleToolbar;
    private Context mContext;

    MainViewModel(Context context, MainContract.Presenter presenter,
            MainViewPagerAdapter viewPagerAdapter, Navigator navigator) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mViewPagerAdapter = viewPagerAdapter;
        mNavigator = navigator;
        mCurrentItem = R.id.item_working_calendar;
        mCurrentTitleToolbar = titleWorkingCalendar();
        mPresenter.getUser();
    }

    public int getPageLimit() {
        return PAGE_LIMIT;
    }

    @Bindable
    public int getCurrentPage() {
        return mCurrentPage;
    }

    private void setCurrentPage(@Page int currentPage) {
        mCurrentPage = currentPage;
        notifyPropertyChanged(BR.currentPage);
    }

    @Bindable
    public int getCurrentItem() {
        return mCurrentItem;
    }

    private void setCurrentItem(int currentItem) {
        mCurrentItem = currentItem;
        notifyPropertyChanged(BR.currentItem);
    }

    @Bindable
    public String getStatusDrawerLayout() {
        return mStatusDrawerLayout;
    }

    private void setStatusDrawerLayout(String statusDrawerLayout) {
        mStatusDrawerLayout = statusDrawerLayout;
        notifyPropertyChanged(BR.statusDrawerLayout);
    }

    @Bindable
    public String getCurrentTitleToolbar() {
        return mCurrentTitleToolbar;
    }

    private void setCurrentTitleToolbar(String currentTitleToolbar) {
        mCurrentTitleToolbar = currentTitleToolbar;
        notifyPropertyChanged(BR.currentTitleToolbar);
    }

    @Bindable
    public String getUsername() {
        return mUser != null ? mUser.getName() : "";
    }

    @Bindable
    public String getEmail() {
        return mUser != null ? mUser.getEmail() : "";
    }

    @Bindable
    public String getAvatar() {
        return (mUser != null && mUser.getAvatar() != null) ? mUser.getAvatar().getUrl() : "";
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.username);
        notifyPropertyChanged(BR.email);
        notifyPropertyChanged(BR.avatar);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void goNextFragmentListRequestOff() {
        setCurrentPage(Page.OFF);
        setCurrentItem(R.id.item_off);
    }

    @Override
    public void goNextFragmentListRequestOverTime() {
        setCurrentPage(Page.OVERTIME);
        setCurrentItem(R.id.item_overtime);
    }

    @Override
    public void goNextFragmentListRequestLeave() {
        setCurrentPage(Page.COME_LATE_LEAVE_EARLY);
        setCurrentItem(R.id.item_come_late_leave_early);
    }

    @Override
    public boolean onBackPressed() {
        if (Constant.DRAWER_IS_OPEN.equals(mStatusDrawerLayout)) {
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
                        setCurrentPage(Page.WORKING_CALENDAR);
                        setCurrentItem(R.id.item_working_calendar);
                        setCurrentTitleToolbar(titleWorkingCalendar());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public MainViewPagerAdapter getViewPagerAdapter() {
        return mViewPagerAdapter;
    }

    public void onDrawerIsOpen(View view) {
        setStatusDrawerLayout(Constant.DRAWER_IS_OPEN);
    }

    public void onLogoutClick(View view) {
        mPresenter.clearUser();
        mNavigator.startActivity(LoginActivity.class);
        mNavigator.finishActivity();
    }

    public void onClickDetailProfile(View view) {
        //TODO Click to Fragment detail profile
    }

    public void onClickNotification(View view) {
        mNavigator.showDialogFragment(R.id.layout_container,
                NotificationDialogFragment.newInstance(), false, NavigateAnim.NONE,
                NotificationDialogFragment.TAG);
    }

    public boolean onItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_personal:
                setCurrentPage(Page.PERSONAL);
                break;
            case R.id.item_setup_profile:
                setCurrentPage(Page.SETUP_PROFILE);
                break;
            case R.id.item_working_calendar:
                setCurrentPage(Page.WORKING_CALENDAR);
                break;
            case R.id.item_holiday_calendar:
                setCurrentPage(Page.HOLIDAY_CALENDAR);
                break;
            case R.id.item_statistic_of_personal:
                setCurrentPage(Page.STATISTIC_OF_PERSONAL);
                break;
            case R.id.item_overtime:
                setCurrentPage(Page.OVERTIME);
                break;
            case R.id.item_off:
                setCurrentPage(Page.OFF);
                break;
            case R.id.item_come_late_leave_early:
                setCurrentPage(Page.COME_LATE_LEAVE_EARLY);
                break;
            case R.id.item_workspace_data:
                setCurrentPage(Page.WORKSPACE_DATA);
                break;
            default:
                break;
        }
        setCurrentItem(item.getItemId());
        setCurrentTitleToolbar(String.valueOf(item.getTitle()));
        setStatusDrawerLayout(Constant.DRAWER_IS_CLOSE);
        return true;
    }

    @NonNull
    private String titleWorkingCalendar() {
        return mContext.getResources().getString(R.string.working_calendar);
    }

    @IntDef({
            Page.WORKING_CALENDAR, Page.SETUP_PROFILE, Page.PERSONAL, Page.HOLIDAY_CALENDAR,
            Page.STATISTIC_OF_PERSONAL, Page.OVERTIME, Page.OFF, Page.COME_LATE_LEAVE_EARLY,
            Page.WORKSPACE_DATA
    })
    @interface Page {
        int WORKING_CALENDAR = 0;
        int SETUP_PROFILE = 1;
        int PERSONAL = 2;
        int HOLIDAY_CALENDAR = 3;
        int STATISTIC_OF_PERSONAL = 4;
        int OVERTIME = 5;
        int OFF = 6;
        int COME_LATE_LEAVE_EARLY = 7;
        int WORKSPACE_DATA = 8;
    }
}
