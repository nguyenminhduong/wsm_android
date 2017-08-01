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
import com.framgia.wsm.data.source.remote.api.response.NotificationResponse;
import com.framgia.wsm.screen.login.LoginActivity;
import com.framgia.wsm.screen.notification.NotificationDialogFragment;
import com.framgia.wsm.screen.notification.NotificationViewModel;
import com.framgia.wsm.screen.timesheet.TimeSheetFragment;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.NavigateAnim;
import com.framgia.wsm.utils.navigator.Navigator;

/**
 * Exposes the data to be used in the Main screen.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private static final String TAG = "MainActivity";
    private static final int PAGE_LIMIT = 12;

    private MainContract.Presenter mPresenter;
    private String mStatusDrawerLayout;
    private User mUser;
    @Page
    private int mCurrentItem;
    private int mCurrentPage;
    private Navigator mNavigator;
    private MainViewPagerAdapter mViewPagerAdapter;
    private String mCurrentTitleToolbar;
    private boolean mIsShowNumberNotification;
    private int mNumberOfNotificationUnread;
    private Context mContext;
    private String mNotificationRequestType;

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
        mPresenter.getNotification();
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
        return (mUser != null && mUser.getAvatar() != null) ? Constant.END_POINT_URL
                + mUser.getAvatar() : "";
    }

    @Bindable
    public String getStaffType() {
        return mUser != null ? StringUtils.getStaffType(mUser.getCode()) : "";
    }

    @Bindable
    public boolean isManage() {
        return mUser != null && mUser.isManage();
    }

    @Bindable
    public boolean isShowNumberNotification() {
        return mIsShowNumberNotification;
    }

    public void setShowNumberNotification(boolean showNumberNotification) {
        mIsShowNumberNotification = showNumberNotification;
        notifyPropertyChanged(BR.showNumberNotification);
    }

    @Bindable
    public String getNumberOfNotificationUnread() {
        return String.valueOf(mNumberOfNotificationUnread);
    }

    public void setNumberOfNotificationUnread(int numberOfNotificationUnread) {
        mNumberOfNotificationUnread = numberOfNotificationUnread;
        notifyPropertyChanged(BR.numberOfNotificationUnread);
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
        notifyPropertyChanged(BR.staffType);
        notifyPropertyChanged(BR.manage);
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void onLogoutSuccess() {
        mPresenter.clearUser();
        mNavigator.startActivity(LoginActivity.class);
        mNavigator.finishActivity();
    }

    @Override
    public void onLogoutError(BaseException exception) {
        mNavigator.showToast(exception.getMessage());
    }

    @Override
    public void goNextFragmentListRequestOff() {
        setCurrentPage(Page.OFF);
        setCurrentItem(R.id.item_off);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_off));
    }

    @Override
    public void goNextFragmentListRequestOverTime() {
        setCurrentPage(Page.OVERTIME);
        setCurrentItem(R.id.item_overtime);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_overtime));
    }

    @Override
    public void goNextFragmentListRequestLeave() {
        setCurrentPage(Page.COME_LATE_LEAVE_EARLY);
        setCurrentItem(R.id.item_come_late_leave_early);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_leave));
    }

    @Override
    public void goNextFragmentListManageRequestOff() {
        setCurrentPage(Page.MANAGE_OFF);
        setCurrentItem(R.id.item_manage_off);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.manage_request_off));
    }

    @Override
    public void goNextFragmentListManageRequestOverTime() {
        setCurrentPage(Page.MANAGE_OVERTIME);
        setCurrentItem(R.id.item_manage_overtime);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.manage_request_overtime));
    }

    @Override
    public void goNextFragmentListManageRequestLeave() {
        setCurrentPage(Page.MANAGE_COME_LATE_LEAVE_EARLY);
        setCurrentItem(R.id.item_manage_come_late_leave_early);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.manage_request_leave));
    }

    @Override
    public void goNextFragmentPersonalInformation() {
        setCurrentPage(Page.PERSONAL);
        setCurrentItem(R.id.item_personal);
    }

    @Override
    public void goNextFragmentManageListRequestOff() {
        setCurrentPage(Page.MANAGE_OFF);
        setCurrentItem(R.id.item_manage_off);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_off));
    }

    @Override
    public void goNextFragmentManageListRequestOverTime() {
        setCurrentPage(Page.MANAGE_OVERTIME);
        setCurrentItem(R.id.item_manage_overtime);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_overtime));
    }

    @Override
    public void goNextFragmentManageListRequestLeave() {
        setCurrentPage(Page.MANAGE_COME_LATE_LEAVE_EARLY);
        setCurrentItem(R.id.item_manage_come_late_leave_early);
        setCurrentTitleToolbar(mContext.getResources().getString(R.string.request_leave));
    }

    @Override
    public void onReloadDataUser() {
        mPresenter.getUser();
    }

    @Override
    public void onGetNotificationSuccess(NotificationResponse notificationResponse) {
        setNumberOfNotificationUnread(notificationResponse.getCountUnreadNotifications());
        if (mNumberOfNotificationUnread == 0) {
            setShowNumberNotification(false);
            return;
        }
        setShowNumberNotification(true);
    }

    @Override
    public void onGetNotificationError(BaseException e) {
        Log.e(TAG, "onGetUserError", e);
    }

    @Override
    public void updateNotificationUnRead() {
        mPresenter.getNotification();
    }

    @Override
    public void handleClickNotification(String trackableType, int permissionType) {
        mPresenter.getNotification();
        if (permissionType == NotificationViewModel.PermissionType.USER) {
            switch (trackableType) {
                case NotificationViewModel.TrackableType.REQUEST_LEAVE:
                    goNextFragmentListRequestLeave();
                    break;
                case NotificationViewModel.TrackableType.REQUEST_OFF:
                    goNextFragmentListRequestOff();
                    break;
                case NotificationViewModel.TrackableType.REQUEST_OT:
                    goNextFragmentListRequestOverTime();
                    break;
                default:
                    break;
            }
            return;
        }
        switch (trackableType) {
            case NotificationViewModel.TrackableType.REQUEST_LEAVE:
                goNextFragmentListManageRequestLeave();
                break;
            case NotificationViewModel.TrackableType.REQUEST_OFF:
                goNextFragmentListManageRequestOff();
                break;
            case NotificationViewModel.TrackableType.REQUEST_OT:
                goNextFragmentListManageRequestOverTime();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (Constant.DRAWER_IS_OPEN.equals(mStatusDrawerLayout)) {
            setStatusDrawerLayout(Constant.DRAWER_IS_CLOSE);
            return true;
        }
        Fragment fragment = mViewPagerAdapter.getCurrentFragment();
        if (fragment == null) {
            return false;
        }
        if (fragment instanceof MainContainerFragment) {
            Fragment childFragment =
                    fragment.getChildFragmentManager().findFragmentById(R.id.layout_container);
            if (childFragment instanceof TimeSheetFragment) {
                return ((MainContainerFragment) fragment).onBackPressed();
            }
        }
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
        return false;
    }

    @Bindable
    public String getNotificationRequestType() {
        return mNotificationRequestType;
    }

    public void setNotificationRequestType(String notificationRequestType) {
        mNotificationRequestType = notificationRequestType;
        notifyPropertyChanged(BR.notificationRequestType);
        getPageNotification(mNotificationRequestType);
    }

    public MainViewPagerAdapter getViewPagerAdapter() {
        return mViewPagerAdapter;
    }

    public void onDrawerIsOpen(View view) {
        setStatusDrawerLayout(Constant.DRAWER_IS_OPEN);
    }

    public void onLogoutClick(View view) {
        mPresenter.logout();
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
            case R.id.item_manage_overtime:
                setCurrentPage(Page.MANAGE_OVERTIME);
                break;
            case R.id.item_manage_off:
                setCurrentPage(Page.MANAGE_OFF);
                break;
            case R.id.item_manage_come_late_leave_early:
                setCurrentPage(Page.MANAGE_COME_LATE_LEAVE_EARLY);
                break;
            default:
                break;
        }
        setCurrentItem(item.getItemId());
        setCurrentTitleToolbar(String.valueOf(item.getTitle()));
        setStatusDrawerLayout(Constant.DRAWER_IS_CLOSE);
        return true;
    }

    private void getPageNotification(String notificationRequestType) {
        if (StringUtils.isBlank(notificationRequestType)) {
            return;
        }
        switch (notificationRequestType) {
            case Constant.REQUEST_OTS:
                goNextFragmentListRequestOverTime();
                break;
            case Constant.REQUEST_OFFS:
                goNextFragmentListRequestOff();
                break;
            case Constant.REQUEST_LEAVES:
                goNextFragmentListRequestLeave();
                break;
            case Constant.MANAGE_REQUEST_OTS:
                goNextFragmentManageListRequestOverTime();
                break;
            case Constant.MANAGE_REQUEST_OFFS:
                goNextFragmentManageListRequestOff();
                break;
            case Constant.MANAGE_REQUEST_LEAVES:
                goNextFragmentManageListRequestLeave();
                break;
            default:
                break;
        }
    }

    @NonNull
    private String titleWorkingCalendar() {
        return mContext.getResources().getString(R.string.working_calendar);
    }

    @IntDef({
            Page.WORKING_CALENDAR, Page.SETUP_PROFILE, Page.PERSONAL, Page.HOLIDAY_CALENDAR,
            Page.STATISTIC_OF_PERSONAL, Page.OVERTIME, Page.OFF, Page.COME_LATE_LEAVE_EARLY,
            Page.WORKSPACE_DATA, Page.MANAGE_OVERTIME, Page.MANAGE_OFF,
            Page.MANAGE_COME_LATE_LEAVE_EARLY
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
        int MANAGE_OVERTIME = 9;
        int MANAGE_OFF = 10;
        int MANAGE_COME_LATE_LEAVE_EARLY = 11;
    }
}
