package com.framgia.wsm.screen.requestoff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;
import java.util.Calendar;

import static com.framgia.wsm.utils.common.DateTimeUtils.FORMAT_DATE;
import static com.framgia.wsm.utils.common.DateTimeUtils.convertToString;

/**
 * Exposes the data to be used in the RequestOff screen.
 */

public class RequestOffViewModel extends BaseObservable
        implements RequestOffContract.ViewModel, DatePickerDialog.OnDateSetListener {
    private static final int FLAG_START_DATE = 1;
    private static final int FLAG_END_DATE = 2;

    private Context mContext;
    private RequestOffContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private int mCurrentPositionOffType;
    private String mCurrentOffType;
    private boolean mIsVisibleLayoutCompanyPay;
    private boolean mIsVisibleLayoutInsuranceCoverage;
    private Calendar mCalendar;
    private String mStartDate;
    private String mEndDate;
    private int mCurrentPositionDaySessionStartDay;
    private String mCurrentDaySessionStartDay;
    private int mCurrentPositionDaySessionEndDay;
    private String mCurrentDaySessionEndDay;
    private int mFlag;

    public RequestOffViewModel(Context context, RequestOffContract.Presenter presenter,
            DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        setVisibleLayoutCompanyPay(true);
        mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
        mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);
        mCurrentPositionDaySessionStartDay = DaySession.AM;
        mCurrentDaySessionStartDay = mContext.getString(R.string.am);
        mCurrentPositionDaySessionEndDay = DaySession.AM;
        mCurrentDaySessionEndDay = mContext.getString(R.string.am);
        mDialogManager.dialogDatePicker(this);
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    public String getTitleToolbar() {
        return mContext.getResources().getString(R.string.create_request_off);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        if (mFlag == FLAG_START_DATE) {
            mStartDate = convertToString(mCalendar.getTime(), FORMAT_DATE);
            setStartDate(mStartDate);
        } else {
            mEndDate = convertToString(mCalendar.getTime(), FORMAT_DATE);
            setEndDate(mEndDate);
        }
    }

    @Bindable
    public boolean isVisibleLayoutCompanyPay() {
        return mIsVisibleLayoutCompanyPay;
    }

    public void setVisibleLayoutCompanyPay(boolean visibleLayoutCompanyPay) {
        mIsVisibleLayoutCompanyPay = visibleLayoutCompanyPay;
        notifyPropertyChanged(BR.visibleLayoutCompanyPay);
    }

    @Bindable
    public boolean isVisibleLayoutInsuranceCoverage() {
        return mIsVisibleLayoutInsuranceCoverage;
    }

    public void setVisibleLayoutInsuranceCoverage(boolean visibleLayoutInsuranceCoverage) {
        mIsVisibleLayoutInsuranceCoverage = visibleLayoutInsuranceCoverage;
        notifyPropertyChanged(BR.visibleLayoutInsuranceCoverage);
    }

    private void setLayoutOffType(int positionType) {
        if (positionType == PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY) {
            setVisibleLayoutCompanyPay(true);
        } else {
            setVisibleLayoutCompanyPay(false);
        }
        if (positionType == PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE) {
            setVisibleLayoutInsuranceCoverage(true);
        } else {
            setVisibleLayoutInsuranceCoverage(false);
        }
        if (positionType == PositionOffType.OFF_NO_SALARY) {
            setVisibleLayoutCompanyPay(false);
            setVisibleLayoutInsuranceCoverage(false);
        }
    }

    @Bindable
    public String getCurrentOffType() {
        return mCurrentOffType;
    }

    public void setCurrentOffType(String currentOffType) {
        mCurrentOffType = currentOffType;
        notifyPropertyChanged(BR.currentOffType);
    }

    public void setCurrentPositionOffType(int currentPositionOffType) {
        mCurrentPositionOffType = currentPositionOffType;
    }

    @Bindable
    public String getStartDate() {
        if (StringUtils.isNotBlank(mStartDate)) {
            return mStartDate;
        }
        return "";
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        if (StringUtils.isNotBlank(mEndDate)) {
            return mEndDate;
        }
        return "";
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
        notifyPropertyChanged(BR.endDate);
    }

    @Bindable
    public int getCurrentPositionDaySessionStartDay() {
        return mCurrentPositionDaySessionStartDay;
    }

    public void setCurrentPositionDaySessionStartDay(int currentPositionDaySessionStartDay) {
        mCurrentPositionDaySessionStartDay = currentPositionDaySessionStartDay;
        notifyPropertyChanged(BR.currentPositionDaySessionStartDay);
    }

    @Bindable
    public String getCurrentDaySessionStartDay() {
        return mCurrentDaySessionStartDay;
    }

    public void setCurrentDaySessionStartDay(String currentDaySessionStartDay) {
        mCurrentDaySessionStartDay = currentDaySessionStartDay;
        notifyPropertyChanged(BR.currentDaySessionStartDay);
    }

    @Bindable
    public int getCurrentPositionDaySessionEndDay() {
        return mCurrentPositionDaySessionEndDay;
    }

    public void setCurrentPositionDaySessionEndDay(int currentPositionDaySessionEndDay) {
        mCurrentPositionDaySessionEndDay = currentPositionDaySessionEndDay;
        notifyPropertyChanged(BR.currentPositionDaySessionEndDay);
    }

    @Bindable
    public String getCurrentDaySessionEndDay() {
        return mCurrentDaySessionEndDay;
    }

    public void setCurrentDaySessionEndDay(String currentDaySessionEndDay) {
        mCurrentDaySessionEndDay = currentDaySessionEndDay;
        notifyPropertyChanged(BR.currentDaySessionEndDay);
    }

    public void onPickTypeRequestOff(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.off_type),
                R.array.off_type, mCurrentPositionOffType,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentPositionOffType(positionType);
                        setCurrentOffType(String.valueOf(charSequence));
                        setLayoutOffType(positionType);
                        return true;
                    }
                });
    }

    public void onPickDaySessionStartDay(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.day_session),
                R.array.day_session, mCurrentPositionDaySessionStartDay,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        setCurrentPositionDaySessionStartDay(position);
                        setCurrentDaySessionStartDay(String.valueOf(charSequence));
                        return true;
                    }
                });
    }

    public void onPickDaySessionEndDay(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.day_session),
                R.array.day_session, mCurrentPositionDaySessionEndDay,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        setCurrentPositionDaySessionEndDay(position);
                        setCurrentDaySessionEndDay(String.valueOf(charSequence));

                        return true;
                    }
                });
    }

    public void onClickStartDate(View view) {
        mFlag = FLAG_START_DATE;
        mDialogManager.showDatePickerDialog();
    }

    public void onClickEndDate(View view) {
        mFlag = FLAG_END_DATE;
        mDialogManager.showDatePickerDialog();
    }

    //Type Salary
    @IntDef({
            PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY,
            PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE, PositionOffType.OFF_NO_SALARY
    })
    @interface PositionOffType {
        int OFF_HAVE_SALARY_COMPANY_PAY = 0;
        int OFF_HAVE_SALARY_INSURANCE_COVERAGE = 1;
        int OFF_NO_SALARY = 2;
    }

    //Day Session
    @IntDef({
            DaySession.AM, DaySession.PM
    })
    @interface DaySession {
        int AM = 0;
        int PM = 1;
    }
}
