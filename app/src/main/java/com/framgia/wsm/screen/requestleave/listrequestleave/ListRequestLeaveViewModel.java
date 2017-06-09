package com.framgia.wsm.screen.requestleave.listrequestleave;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;
import java.util.Calendar;

/**
 * Exposes the data to be used in the ListRequestLeave screen.
 */

public class ListRequestLeaveViewModel extends BaseObservable
        implements ListRequestLeaveContract.ViewModel, DatePickerDialog.OnDateSetListener {
    private static final int FORMAT_MONTH = 10;

    private Context mContext;
    private ListRequestLeaveContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private int mCurrentPositionStatus;
    private String mCurrentStatus;
    private String mMonthYear;
    private int mYear;
    private int mMonth;
    private Calendar mCalendar;

    public ListRequestLeaveViewModel(Context context, ListRequestLeaveContract.Presenter presenter,
            DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mCurrentPositionStatus = PositionStatus.ALL;
        mCurrentStatus = mContext.getString(R.string.all);
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDialogManager.dialogMonthYearPicker(this, mYear, mMonth);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    public void onPickTypeStatus(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.status), R.array.status,
                mCurrentPositionStatus, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentPositionStatus(positionType);
                        setCurrentStatus(String.valueOf(charSequence));
                        return true;
                    }
                });
    }

    public void onPickMonthYear(View view) {
        mDialogManager.showMonthYearPickerDialog();
    }

    public void setCurrentPositionStatus(int currentPositionStatus) {
        mCurrentPositionStatus = currentPositionStatus;
    }

    @Bindable
    public String getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        mCurrentStatus = currentStatus;
        notifyPropertyChanged(BR.currentStatus);
    }

    @Bindable
    public String getMonthYear() {
        return mMonthYear;
    }

    public void setMonthYear(String monthYear) {
        mMonthYear = monthYear;
        notifyPropertyChanged(BR.monthYear);
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        updateMonthYear();
    }

    private void updateMonthYear() {
        // Default value mMonth = 0 (From 0 to 11) => localMonth = 1 (From 1 to 12)
        int localMonth = (mMonth + 1);
        // Format month 01/2017...instead 1/2017...
        String month = localMonth < FORMAT_MONTH ? "0" + localMonth : Integer.toString(localMonth);
        String year = Integer.toString(mYear);
        setMonthYear(String.valueOf(
                new StringBuilder().append(month).append("/").append(year).append("")));
    }

    @IntDef({
            PositionStatus.ALL, PositionStatus.ACCEPTED, PositionStatus.PENDING,
            PositionStatus.REJECTED
    })
    @interface PositionStatus {
        int ALL = 0;
        int ACCEPTED = 1;
        int PENDING = 2;
        int REJECTED = 3;
    }
}
