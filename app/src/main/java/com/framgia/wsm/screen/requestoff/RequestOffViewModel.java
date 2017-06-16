package com.framgia.wsm.screen.requestoff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.RequestOff;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRequestOff;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.Calendar;
import java.util.Date;

/**
 * Exposes the data to be used in the RequestOff screen.
 */

public class RequestOffViewModel extends BaseRequestOff
        implements RequestOffContract.ViewModel, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "RequestOffViewModel";
    private static final int FLAG_START_DATE = 1;
    private static final int FLAG_END_DATE = 2;
    private static final int FLAG_SESSION_START_DATE = 1;
    private static final int FLAG_SESSION_END_DATE = 2;

    //TODO move next pull
    private static final int ONE_MONTH = 1;
    private static final int DAY_OF_MONTH = 25;

    private Context mContext;
    private RequestOffContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private Calendar mCalendar;
    private RequestOff mRequestOff;
    private Navigator mNavigator;
    private User mUser;
    private String mReasonError;

    private int mCurrentBranchPosition;
    private int mCurrentGroupPosition;

    private int mFlagDate;
    private int mFlagDateSession;
    private String mCurrentOffType;
    private int mCurrentPositionOffType;
    private boolean mIsVisibleLayoutCompanyPay;
    private boolean mIsVisibleLayoutInsuranceCoverage;
    private boolean mIsVisibleLayoutNoSalary;

    private String mStartDateHaveSalary;
    private String mEndDateHaveSalary;
    private String mCurrentDaySessionStartDayHaveSalary;
    private String mCurrentDaySessionEndDayHaveSalary;
    private int mCurrentPositionDaySessionStartDayHaveSalary;
    private int mCurrentPositionDaySessionEndDayHaveSalary;

    private String mStartDateNoSalary;
    private String mEndDateNoSalary;
    private String mCurrentDaySessionStartDayNoSalary;
    private String mCurrentDaySessionEndDayNoSalary;
    private int mCurrentPositionDaySessionStartDayNoSalary;
    private int mCurrentPositionDaySessionEndDayNoSalary;

    RequestOffViewModel(Context context, RequestOffContract.Presenter presenter,
            DialogManager dialogManager, Navigator navigator) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mNavigator = navigator;
        mPresenter.getUser();
        mRequestOff = new RequestOff();
        initData();
    }

    private void initData() {
        mCalendar = Calendar.getInstance();
        setVisibleLayoutCompanyPay(true);
        mDialogManager.dialogDatePicker(this);
        mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
        mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);

        mCurrentPositionDaySessionStartDayHaveSalary = DaySession.AM;
        mCurrentPositionDaySessionEndDayHaveSalary = DaySession.AM;
        mCurrentDaySessionStartDayHaveSalary = mContext.getString(R.string.am);
        mCurrentDaySessionEndDayHaveSalary = mContext.getString(R.string.am);

        mCurrentPositionDaySessionStartDayNoSalary = DaySession.AM;
        mCurrentPositionDaySessionEndDayNoSalary = DaySession.AM;
        mCurrentDaySessionStartDayNoSalary = mContext.getString(R.string.am);
        mCurrentDaySessionEndDayNoSalary = mContext.getString(R.string.am);
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
        notifyPropertyChanged(BR.user);
        setCurrentBranch();
        setCurrentGroup();
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "RequestOvertimeViewModel", e);
    }

    @Override
    public void onInputReasonError(String reason) {
        mReasonError = reason;
        notifyPropertyChanged(BR.reasonError);
    }

    @Override
    public void setVisibleLayoutNoSalary(boolean visibleLayoutONoSalary) {
        mIsVisibleLayoutNoSalary = visibleLayoutONoSalary;
        notifyPropertyChanged(BR.startDate);
        notifyPropertyChanged(BR.endDate);
        notifyPropertyChanged(BR.currentDaySessionStartDay);
        notifyPropertyChanged(BR.currentDaySessionEndDay);
    }

    @Override
    public void setPosistionOffType(int posistionOffType) {
        mRequestOff.setPositionRequestOffType(posistionOffType);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        if (mFlagDate == FLAG_START_DATE) {
            if (DateTimeUtils.convertStringToDate(date).after(currentMonthWorking())) {
                setEndDate(null);
                setStartDate(date);
            } else {
                setStartDate(null);
                showErrorDialog(mContext.getString(R.string.you_can_not_access));
            }
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + ONE_MONTH);
            return;
        }
        validateEndDate(date);
    }

    //TODO move next pull
    private Date currentMonthWorking() {
        mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) - ONE_MONTH);
        mCalendar.set(Calendar.DAY_OF_MONTH, DAY_OF_MONTH);
        String currentMonth = DateTimeUtils.convertToString(mCalendar.getTime(),
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD);
        return DateTimeUtils.convertStringToDate(currentMonth);
    }

    private void validateEndDate(String date) {
        if (mIsVisibleLayoutNoSalary) {
            if (DateTimeUtils.convertStringToDate(date)
                    .before(DateTimeUtils.convertStringToDate(mStartDateNoSalary))) {
                setEndDate(null);
                showErrorDialog(mContext.getString(R.string.end_date_must_greater_than_start_day));
            } else {
                setEndDate(date);
            }
        }
        //TODO validate EndDate with Layout Have Salary
    }

    public void onPickBranch(View view) {
        if (mUser.getBranches() == null || mUser.getBranches().size() == 0) {
            return;
        }
        String[] branches = new String[mUser.getBranches().size()];
        for (int i = 0; i < branches.length; i++) {
            branches[i] = mUser.getBranches().get(i).getBranchName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.branch), branches,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        mCurrentBranchPosition = position;
                        setCurrentBranch();
                        return true;
                    }
                });
    }

    public void onPickGroup(View view) {
        if (mUser.getGroups() == null || mUser.getGroups().size() == 0) {
            return;
        }
        String[] groups = new String[mUser.getGroups().size()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = mUser.getGroups().get(i).getGroupName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.group), groups,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        mCurrentGroupPosition = position;
                        setCurrentGroup();
                        return true;
                    }
                });
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
        int currentPositionDaySessionStartDay;
        if (mIsVisibleLayoutNoSalary) {
            currentPositionDaySessionStartDay = mCurrentPositionDaySessionStartDayNoSalary;
        } else {
            currentPositionDaySessionStartDay = mCurrentPositionDaySessionStartDayHaveSalary;
        }
        mFlagDateSession = FLAG_SESSION_START_DATE;
        showDialogListSingleChoice(currentPositionDaySessionStartDay);
    }

    public void onPickDaySessionEndDay(View view) {
        int currentPositionDaySessionEndDay;
        if (mIsVisibleLayoutNoSalary) {
            currentPositionDaySessionEndDay = mCurrentPositionDaySessionEndDayNoSalary;
        } else {
            currentPositionDaySessionEndDay = mCurrentPositionDaySessionEndDayHaveSalary;
        }
        mFlagDateSession = FLAG_SESSION_END_DATE;
        showDialogListSingleChoice(currentPositionDaySessionEndDay);
    }

    private void showDialogListSingleChoice(int currentPositionDaySession) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.day_session),
                R.array.day_session, currentPositionDaySession,
                new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        if (mFlagDateSession == FLAG_SESSION_START_DATE) {
                            setCurrentPositionDaySessionStartDay(position);
                            setCurrentDaySessionStartDay(String.valueOf(charSequence));
                        } else {
                            setCurrentPositionDaySessionEndDay(position);
                            setCurrentDaySessionEndDay(String.valueOf(charSequence));
                        }
                        return true;
                    }
                });
    }

    private void showErrorDialog(String errorMessage) {
        mDialogManager.dialogError(errorMessage, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mDialogManager.showDatePickerDialog();
            }
        });
    }

    public void onClickStartDate(View view) {
        mFlagDate = FLAG_START_DATE;
        mDialogManager.showDatePickerDialog();
    }

    public void onClickEndDate(View view) {
        mFlagDate = FLAG_END_DATE;
        if (mIsVisibleLayoutNoSalary) {
            if (mStartDateNoSalary != null) {
                mDialogManager.showDatePickerDialog();
            } else {
                showErrorDialog(mContext.getString(R.string.you_have_to_choose_start_date));
            }
            return;
        }
        if (mStartDateHaveSalary != null) {
            mDialogManager.showDatePickerDialog();
        } else {
            showErrorDialog(mContext.getString(R.string.you_have_to_choose_start_date));
        }
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickNext(View view) {
        if (!mPresenter.validateData(mRequestOff)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequestOff);
        mNavigator.startActivity(ConfirmRequestOffActivity.class, bundle);
    }

    @Bindable
    public RequestOff getRequestOff() {
        return mRequestOff;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getReasonError() {
        return mReasonError;
    }

    @Bindable
    public boolean isVisibleLayoutCompanyPay() {
        return mIsVisibleLayoutCompanyPay;
    }

    @Override
    public void setVisibleLayoutCompanyPay(boolean visibleLayoutCompanyPay) {
        mIsVisibleLayoutCompanyPay = visibleLayoutCompanyPay;
        notifyPropertyChanged(BR.visibleLayoutCompanyPay);
    }

    @Bindable
    public boolean isVisibleLayoutInsuranceCoverage() {
        return mIsVisibleLayoutInsuranceCoverage;
    }

    @Override
    public void setVisibleLayoutInsuranceCoverage(boolean visibleLayoutInsuranceCoverage) {
        mIsVisibleLayoutInsuranceCoverage = visibleLayoutInsuranceCoverage;
        notifyPropertyChanged(BR.visibleLayoutInsuranceCoverage);
    }

    @Bindable
    public String getStartDate() {
        return mIsVisibleLayoutNoSalary ? mStartDateNoSalary : mStartDateHaveSalary;
    }

    private void setStartDate(String startDate) {
        if (mIsVisibleLayoutNoSalary) {
            mStartDateNoSalary = startDate;
        } else {
            mStartDateHaveSalary = startDate;
        }
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return mIsVisibleLayoutNoSalary ? mEndDateNoSalary : mEndDateHaveSalary;
    }

    private void setEndDate(String endDate) {
        if (mIsVisibleLayoutNoSalary) {
            mEndDateNoSalary = endDate;
        } else {
            mEndDateHaveSalary = endDate;
        }
        notifyPropertyChanged(BR.endDate);
    }

    @Bindable
    public String getCurrentOffType() {
        return mCurrentOffType;
    }

    private void setCurrentOffType(String currentOffType) {
        mCurrentOffType = currentOffType;
        notifyPropertyChanged(BR.currentOffType);
    }

    @Bindable
    public String getCurrentDaySessionStartDay() {
        return mIsVisibleLayoutNoSalary ? mCurrentDaySessionStartDayNoSalary
                : mCurrentDaySessionStartDayHaveSalary;
    }

    private void setCurrentDaySessionStartDay(String currentDaySessionStartDay) {
        if (mIsVisibleLayoutNoSalary) {
            mCurrentDaySessionStartDayNoSalary = currentDaySessionStartDay;
        } else {
            mCurrentDaySessionStartDayHaveSalary = currentDaySessionStartDay;
        }
        notifyPropertyChanged(BR.currentDaySessionStartDay);
    }

    @Bindable
    public String getCurrentDaySessionEndDay() {
        return mIsVisibleLayoutNoSalary ? mCurrentDaySessionEndDayNoSalary
                : mCurrentDaySessionEndDayHaveSalary;
    }

    private void setCurrentDaySessionEndDay(String currentDaySessionEndDay) {
        if (mIsVisibleLayoutNoSalary) {
            mCurrentDaySessionEndDayNoSalary = currentDaySessionEndDay;
        } else {
            mCurrentDaySessionEndDayHaveSalary = currentDaySessionEndDay;
        }
        notifyPropertyChanged(BR.currentDaySessionEndDay);
    }

    private void setCurrentPositionOffType(int currentPositionOffType) {
        mCurrentPositionOffType = currentPositionOffType;
    }

    private void setCurrentPositionDaySessionStartDay(int currentPositionDaySessionStartDay) {
        if (mIsVisibleLayoutNoSalary) {
            mCurrentPositionDaySessionStartDayNoSalary = currentPositionDaySessionStartDay;
        } else {
            mCurrentPositionDaySessionStartDayHaveSalary = currentPositionDaySessionStartDay;
        }
    }

    private void setCurrentPositionDaySessionEndDay(int currentPositionDaySessionEndDay) {
        if (mIsVisibleLayoutNoSalary) {
            mCurrentPositionDaySessionEndDayNoSalary = currentPositionDaySessionEndDay;
        } else {
            mCurrentPositionDaySessionEndDayHaveSalary = currentPositionDaySessionEndDay;
        }
    }

    private void setCurrentBranch() {
        mRequestOff.setBranch(mUser.getBranches().get(mCurrentBranchPosition));
        notifyPropertyChanged(BR.requestOff);
    }

    private void setCurrentGroup() {
        mRequestOff.setGroup(mUser.getGroups().get(mCurrentGroupPosition));
        notifyPropertyChanged(BR.currentGroup);
    }

    private double getSumDateOffHaveSalary() {
        return StringUtils.convertStringToDouble(mRequestOff.getCompanyPay().getAnnualLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getCompanyPay().getLeaveForMarriage())
                + StringUtils.convertStringToDouble(mRequestOff.getCompanyPay().getFuneralLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getCompanyPay().getLeaveForChildMarriage())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getLeaveForCareOfSickChild())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getSickLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getMaternityLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getPregnancyExaminationLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getMiscarriageLeave())
                + StringUtils.convertStringToDouble(
                mRequestOff.getInsuranceCoverage().getWifeLaborLeave());
    }

    @IntDef({
            PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY,
            PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE, PositionOffType.OFF_NO_SALARY
    })
    public @interface PositionOffType {
        int OFF_HAVE_SALARY_COMPANY_PAY = 0;
        int OFF_HAVE_SALARY_INSURANCE_COVERAGE = 1;
        int OFF_NO_SALARY = 2;
    }

    @IntDef({
            DaySession.AM, DaySession.PM
    })
    @interface DaySession {
        int AM = 0;
        int PM = 1;
    }
}
