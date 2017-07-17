package com.framgia.wsm.screen.timesheet;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.model.UserTimeSheet;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestleave.RequestLeaveActivity;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.screen.requestovertime.RequestOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.framgia.wsm.utils.Constant.TimeConst.DAY_25_OF_MONTH;
import static com.framgia.wsm.utils.Constant.TimeConst.ONE_MONTH;

/**
 * Exposes the data to be used in the TimeSheet screen.
 */

public class TimeSheetViewModel extends BaseObservable implements TimeSheetContract.ViewModel {

    private Context mContext;
    private TimeSheetContract.Presenter mPresenter;
    private List<TimeSheetDate> mTimeSheetDates;
    private DialogManager mDialogManager;
    private int mMonth;
    private int mYear;
    private boolean isShowInformation;
    private TimeSheetDate mTimeSheetDate;
    private UserTimeSheet mUserTimeSheet;
    private Navigator mNavigator;
    private boolean isVisibleFloatingActionMenu;

    public TimeSheetViewModel(Context context, TimeSheetContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mTimeSheetDates = new ArrayList<>();
        mTimeSheetDate = new TimeSheetDate();
        mUserTimeSheet = new UserTimeSheet();
        mDialogManager = dialogManager;
        mNavigator = navigator;
        mDialogManager.showIndeterminateProgressDialog();
        setMonth();
        setYear();
        mPresenter.getTimeSheet(mMonth + ONE_MONTH, mYear);
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
    public void onGetTimeSheetError(BaseException throwable) {
        //todo show error message
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onGetTimeSheetSuccess(UserTimeSheet userTimeSheet) {
        setTimeSheetDates(userTimeSheet.getTimeSheetDates());
        setUserTimeSheet(userTimeSheet);
        mDialogManager.dismissProgressDialog();
    }

    @Bindable
    public String getDate() {
        return (mTimeSheetDate.getDateString() == null) ? "" : DateTimeUtils.convertToString(
                DateTimeUtils.convertStringToDate(mTimeSheetDate.getDateString(),
                        DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_2),
                DateTimeUtils.DATE_FORMAT_EEE_D_MMM__YYYY);
    }

    @Bindable
    public String getTimeIn() {
        return mTimeSheetDate.getTimeIn();
    }

    @Bindable
    public String getTimeOut() {
        return mTimeSheetDate.getTimeOut();
    }

    @Bindable
    public String getColorMorning() {
        return mTimeSheetDate.getColorMorning();
    }

    @Bindable
    public String getColorAfternoon() {
        return mTimeSheetDate.getColorAfternoon();
    }

    @Bindable
    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    private void setTimeSheetDates(List<TimeSheetDate> timeSheetDates) {
        if (timeSheetDates == null) {
            return;
        }
        mTimeSheetDates.clear();
        mTimeSheetDates.addAll(timeSheetDates);
        notifyPropertyChanged(BR.timeSheetDates);
    }

    @Bindable
    public int getMonth() {
        return mMonth;
    }

    private void setMonth() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) > DAY_25_OF_MONTH) {
            mMonth = calendar.get(Calendar.MONTH) + 1;
            return;
        }
        mMonth = calendar.get(Calendar.MONTH);
    }

    @Bindable
    public int getYear() {
        return mYear;
    }

    private void setYear() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
    }

    @Bindable
    public String getTotalDayOff() {
        return String.valueOf(mUserTimeSheet.getTotalDayOff());
    }

    @Bindable
    public String getTotalFining() {
        return String.valueOf(mUserTimeSheet.getTotalFining());
    }

    @Bindable
    public String getNumberDayOffHaveSalary() {
        return String.valueOf(mUserTimeSheet.getNumberDayOffHaveSalary());
    }

    @Bindable
    public String getNumberDayOffNoSalary() {
        return String.valueOf(mUserTimeSheet.getNumberDayOffNoSalary());
    }

    @Bindable
    public String getNumberOverTime() {
        return String.valueOf(mUserTimeSheet.getNumberOverTime());
    }

    private void setUserTimeSheet(UserTimeSheet userTimeSheet) {
        mUserTimeSheet = userTimeSheet;
        notifyPropertyChanged(BR.totalDayOff);
        notifyPropertyChanged(BR.totalFining);
        notifyPropertyChanged(BR.numberDayOffHaveSalary);
        notifyPropertyChanged(BR.numberDayOffNoSalary);
        notifyPropertyChanged(BR.numberOverTime);
    }

    @Bindable
    public boolean isShowInformation() {
        return isShowInformation;
    }

    @Bindable
    public boolean isVisibleFloatingActionMenu() {
        return isVisibleFloatingActionMenu;
    }

    public void setVisibleFloatingActionMenu(boolean visibleFloatingActionMenu) {
        isVisibleFloatingActionMenu = visibleFloatingActionMenu;
        notifyPropertyChanged(BR.visibleFloatingActionMenu);
    }

    public void onDayClicked(TimeSheetDate timeSheetDate) {
        mTimeSheetDate = timeSheetDate;
        isShowInformation = true;
        notifyPropertyChanged(BR.showInformation);
        notifyPropertyChanged(BR.date);
        notifyPropertyChanged(BR.timeIn);
        notifyPropertyChanged(BR.timeOut);
        notifyPropertyChanged(BR.colorMorning);
        notifyPropertyChanged(BR.colorAfternoon);
    }

    public void onClickRequestLeave(View view) {
        setVisibleFloatingActionMenu(false);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_CREATE);
        mNavigator.startActivityForResult(RequestLeaveActivity.class, bundle,
                Constant.RequestCode.REQUEST_LEAVE);
    }

    public void onClickRequestOvertime(View view) {
        setVisibleFloatingActionMenu(false);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_CREATE);
        mNavigator.startActivityForResult(RequestOvertimeActivity.class, bundle,
                Constant.RequestCode.REQUEST_OVERTIME);
    }

    public void onClickRequestOff(View view) {
        setVisibleFloatingActionMenu(false);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_CREATE);
        mNavigator.startActivityForResult(RequestOffActivity.class, bundle,
                Constant.RequestCode.REQUEST_OFF);
    }

    public void onNextMonth() {
        if (mMonth == 11) {
            mMonth = 0;
            mYear++;
        } else {
            mMonth++;
        }
        mDialogManager.showIndeterminateProgressDialog();
        notifyPropertyChanged(BR.month);
        notifyPropertyChanged(BR.year);
        mPresenter.getTimeSheet(mMonth + ONE_MONTH, mYear);
    }

    public void onPreviousMonth() {
        if (mMonth == 0) {
            mMonth = 11;
            mYear--;
        } else {
            mMonth--;
        }
        mDialogManager.showIndeterminateProgressDialog();
        notifyPropertyChanged(BR.month);
        notifyPropertyChanged(BR.year);
        mPresenter.getTimeSheet(mMonth + ONE_MONTH, mYear);
    }
}
