package com.framgia.wsm.screen.timesheet;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.TimeSheetDate;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestleave.RequestLeaveActivity;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.screen.requestovertime.RequestOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private Navigator mNavigator;
    private boolean isVisibleFloatingActionMenu;

    public TimeSheetViewModel(Context context, TimeSheetContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mTimeSheetDates = new ArrayList<>();
        mTimeSheetDate = new TimeSheetDate();
        mDialogManager = dialogManager;
        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        mNavigator = navigator;
        mDialogManager.showIndeterminateProgressDialog();
        mPresenter.getTimeSheet(mMonth, mYear);
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
    public void onGetTimeSheetSuccess(List<TimeSheetDate> list) {
        mTimeSheetDates.clear();
        // TODO: 31/05/2017 remove if (mMonth == 4) when have API
        if (mMonth == 5) {
            mTimeSheetDates.addAll(list);
        }
        notifyPropertyChanged(BR.timeSheetDates);
        mDialogManager.dismissProgressDialog();
    }

    @Bindable
    public String getDate() {
        //todo edit later
        return mTimeSheetDate.getDateString();
    }

    @Bindable
    public String getTimeIn() {
        return mContext.getString(R.string.time_in, mTimeSheetDate.getTimeIn());
    }

    @Bindable
    public String getTimeOut() {
        return mContext.getString(R.string.time_out, mTimeSheetDate.getTimeOut());
    }

    @Bindable
    public List<TimeSheetDate> getTimeSheetDates() {
        return mTimeSheetDates;
    }

    @Bindable
    public int getMonth() {
        return mMonth;
    }

    @Bindable
    public int getYear() {
        return mYear;
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
    }

    public void onClickRequestLeave(View view) {
        setVisibleFloatingActionMenu(false);
        mNavigator.startActivityForResult(RequestLeaveActivity.class,
                Constant.RequestCode.REQUEST_LEAVE);
    }

    public void onClickRequestOvertime(View view) {
        setVisibleFloatingActionMenu(false);
        mNavigator.startActivityForResult(RequestOvertimeActivity.class,
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
        if (mMonth == 12) {
            mMonth = 1;
            mYear++;
        } else {
            mMonth++;
        }
        mDialogManager.showIndeterminateProgressDialog();
        notifyPropertyChanged(BR.month);
        notifyPropertyChanged(BR.year);
        notifyPropertyChanged(BR.timeSheetDates);
        mPresenter.getTimeSheet(mMonth, mYear);
    }

    public void onPreviousMonth() {
        if (mMonth == 1) {
            mMonth = 12;
            mYear--;
        } else {
            mMonth--;
        }
        mDialogManager.showIndeterminateProgressDialog();
        notifyPropertyChanged(BR.month);
        notifyPropertyChanged(BR.year);
        notifyPropertyChanged(BR.timeSheetDates);
        mPresenter.getTimeSheet(mMonth, mYear);
    }
}
