package com.framgia.wsm.screen.requestoff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.CompanyPay;
import com.framgia.wsm.data.model.Group;
import com.framgia.wsm.data.model.InsuranceCoverage;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.OffType;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRequestOff;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.framgia.wsm.utils.Constant.TimeConst.ONE_MONTH;
import static com.framgia.wsm.utils.common.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD;
import static com.framgia.wsm.utils.common.DateTimeUtils.FORMAT_DATE;

/**
 * Exposes the data to be used in the OffRequest screen.
 */

public class RequestOffViewModel extends BaseRequestOff
        implements RequestOffContract.ViewModel, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "RequestOffViewModel";
    private static final String ANNUAL = "Annual";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private static final int FLAG_START_DATE = 1;
    private static final int FLAG_END_DATE = 2;
    private static final int FLAG_SESSION_START_DATE = 1;
    private static final int FLAG_SESSION_END_DATE = 2;

    private Context mContext;
    private RequestOffContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private Calendar mCalendar;
    private OffRequest mRequestOff;
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
    private int mActionType;
    private List<OffRequest.RequestDayOffTypesAttribute> mRequestDayOffTypesAttributes =
            new ArrayList<>();
    private OffRequest.RequestDayOffTypesAttribute mRequestDayOffTypesAttribute;

    private String mAnnualLeave;
    private String mLeaveForMarriage;
    private String mLeaveForChildMarriage;
    private String mFuneralLeave;

    private String mLeaveForCareOfSickChild;
    private String mPregnancyExaminationLeave;
    private String mSickLeave;
    private String mMiscarriageLeave;
    private String mMaternityLeave;
    private String mWifeLaborLeave;

    private String mAnnualLeaveAmount;
    private String mLeaveForMarriageAmount;
    private String mLeaveForChildMarriageAmount;
    private String mFuneralLeaveAmount;

    private String mLeaveForCareOfSickChildAmount;
    private String mPregnancyExaminationLeaveAmount;
    private String mSickLeaveAmount;
    private String mMiscarriageLeaveAmount;
    private String mMaternityLeaveAmount;
    private String mWifeLaborLeaveAmount;

    RequestOffViewModel(Context context, RequestOffContract.Presenter presenter,
            DialogManager dialogManager, Navigator navigator, OffRequest requestOff,
            int actionType) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mNavigator = navigator;
        mPresenter.getUser();
        mCalendar = Calendar.getInstance();
        mDialogManager.dialogDatePicker(this);
        mActionType = actionType;
        initData(requestOff);
    }

    private void initData(OffRequest requestOff) {
        String am = mContext.getString(R.string.am);
        String pm = mContext.getString(R.string.pm);
        if (requestOff == null) {
            mRequestOff = new OffRequest();
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            mRequestOff.setStartDayHaveSalary(offHaveSalaryFrom);
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            mRequestOff.setEndDayHaveSalary(offHaveSalaryTo);
            OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            mRequestOff.setStartDayNoSalary(offNoSalaryFrom);
            OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();
            mRequestOff.setEndDayNoSalary(offNoSalaryTo);

            mCurrentPositionDaySessionStartDayHaveSalary = DaySession.AM;
            mCurrentPositionDaySessionEndDayHaveSalary = DaySession.AM;
            mCurrentDaySessionStartDayHaveSalary = am;
            mCurrentDaySessionEndDayHaveSalary = am;

            mCurrentPositionDaySessionStartDayNoSalary = DaySession.AM;
            mCurrentPositionDaySessionEndDayNoSalary = DaySession.AM;
            mCurrentDaySessionStartDayNoSalary = am;
            mCurrentDaySessionEndDayNoSalary = am;

            setVisibleLayoutCompanyPay(true);
            mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
            mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);
        } else {
            mRequestOff = requestOff;
            if (mRequestOff.getCompanyPay() == null) {
                mRequestOff.setCompanyPay(new CompanyPay());
            }
            if (mRequestOff.getInsuranceCoverage() == null) {
                mRequestOff.setInsuranceCoverage(new InsuranceCoverage());
            }

            mStartDateHaveSalary = mRequestOff.getStartDayHaveSalary().getOffPaidFrom();
            mEndDateHaveSalary = mRequestOff.getEndDayHaveSalary().getOffPaidTo();
            mStartDateNoSalary = mRequestOff.getStartDayNoSalary().getOffFrom();
            mEndDateNoSalary = mRequestOff.getEndDayNoSalary().getOffTo();
            if (mRequestOff.getNumberDayOffNormal() == null) {
                mAnnualLeave = "";
            } else {
                mAnnualLeave = String.valueOf(mRequestOff.getNumberDayOffNormal());
            }
            mLeaveForMarriage = mRequestOff.getLeaveForMarriage();
            mLeaveForChildMarriage = mRequestOff.getLeaveForChildMarriage();
            mFuneralLeave = mRequestOff.getFuneralLeave();
            mLeaveForCareOfSickChild = mRequestOff.getLeaveForCareOfSickChild();
            mPregnancyExaminationLeave = mRequestOff.getPregnancyExaminationLeave();
            mSickLeave = mRequestOff.getSickLeave();
            mMiscarriageLeave = mRequestOff.getMiscarriageLeave();
            mMaternityLeave = mRequestOff.getMaternityLeave();
            mWifeLaborLeave = mRequestOff.getWifeLaborLeave();

            if ("".equals(mStartDateHaveSalary)) {
                setVisibleLayoutNoSalary(true);
                mCurrentPositionOffType = PositionOffType.OFF_NO_SALARY;
                mCurrentOffType = mContext.getString(R.string.off_no_salary);
                if (am.equals(mRequestOff.getStartDayNoSalary().getOffFromPeriod())) {
                    mCurrentDaySessionStartDayNoSalary = am;
                    mCurrentPositionDaySessionStartDayNoSalary = DaySession.AM;
                } else {
                    mCurrentDaySessionStartDayNoSalary = pm;
                    mCurrentPositionDaySessionStartDayNoSalary = DaySession.PM;
                }

                if (am.equals(mRequestOff.getEndDayNoSalary().getOffToPeriod())) {
                    mCurrentDaySessionEndDayNoSalary = am;
                    mCurrentPositionDaySessionEndDayNoSalary = DaySession.AM;
                } else {
                    mCurrentDaySessionEndDayNoSalary = pm;
                    mCurrentPositionDaySessionEndDayNoSalary = DaySession.PM;
                }
                mCurrentDaySessionStartDayHaveSalary = am;
                mCurrentPositionDaySessionStartDayHaveSalary = DaySession.AM;
                mCurrentDaySessionEndDayHaveSalary = am;
                mCurrentPositionDaySessionEndDayHaveSalary = DaySession.AM;
            } else {
                if (layoutCompanyPayIsVisible()) {
                    setVisibleLayoutCompanyPay(true);
                    mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
                    mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);
                } else {
                    setVisibleLayoutInsuranceCoverage(true);
                    mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_INSURANCE_COVERAGE;
                    mCurrentOffType =
                            mContext.getString(R.string.off_have_salary_insurance_coverage);
                }
                if (am.equals(mRequestOff.getStartDayHaveSalary().getPaidFromPeriod())) {
                    mCurrentDaySessionStartDayHaveSalary = am;
                    mCurrentPositionDaySessionStartDayHaveSalary = DaySession.AM;
                } else {
                    mCurrentDaySessionStartDayHaveSalary = pm;
                    mCurrentPositionDaySessionStartDayHaveSalary = DaySession.PM;
                }

                if (am.equals(mRequestOff.getEndDayHaveSalary().getPaidToPeriod())) {
                    mCurrentDaySessionEndDayHaveSalary = am;
                    mCurrentPositionDaySessionEndDayHaveSalary = DaySession.AM;
                } else {
                    mCurrentDaySessionEndDayHaveSalary = pm;
                    mCurrentPositionDaySessionEndDayHaveSalary = DaySession.PM;
                }
                mCurrentDaySessionStartDayNoSalary = am;
                mCurrentPositionDaySessionStartDayNoSalary = DaySession.AM;
                mCurrentDaySessionEndDayNoSalary = am;
                mCurrentPositionDaySessionEndDayNoSalary = DaySession.AM;
            }
            if (Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getAnnualLeave())) {
                mRequestOff.setAnnualLeave("");
            }
        }
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
        getAmountOffDayCompany(mUser);
        getAmountOffDayInsurance(mUser);
        if (mActionType == ActionType.ACTION_CREATE) {
            setCurrentBranch();
            setCurrentGroup();
            return;
        }
        setBranchAndGroupWhenEdit(mUser);
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
        if (!view.isShown()) {
            return;
        }
        if (year == 0) {
            if (mFlagDate == FLAG_START_DATE) {
                setStartDate(null);
            } else {
                setEndDate(null);
            }
        }
        String date = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        int dayOfWeek = DateTimeUtils.getDayOfWeek(year, month, dayOfMonth);
        if (mFlagDate == FLAG_START_DATE) {
            if (DateTimeUtils.convertStringToDate(date)
                    .after(DateTimeUtils.currentMonthWorking())) {
                if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                    setEndDate(null);
                    setStartDate(date);
                    return;
                }
                showErrorDialogWithButtonRetry(
                        mContext.getString(R.string.time_must_be_a_working_date));
            } else {
                setStartDate(null);
                showErrorDialogWithButtonRetry(mContext.getString(R.string.you_can_not_access));
            }
            mCalendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH) + ONE_MONTH);
            return;
        }
        validateEndDate(date);
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
        return mIsVisibleLayoutNoSalary ? DateTimeUtils.convertUiFormatToDataFormat(
                mRequestOff.getStartDayNoSalary().getOffFrom(), DATE_FORMAT_YYYY_MM_DD, FORMAT_DATE)
                : DateTimeUtils.convertUiFormatToDataFormat(
                        mRequestOff.getStartDayHaveSalary().getOffPaidFrom(),
                        DATE_FORMAT_YYYY_MM_DD, FORMAT_DATE);
    }

    private void setStartDate(String startDate) {
        if (mIsVisibleLayoutNoSalary) {
            mStartDateNoSalary = startDate;
            OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            offNoSalaryFrom.setOffFrom(mStartDateNoSalary);
            mRequestOff.setStartDayNoSalary(offNoSalaryFrom);
        } else {
            mStartDateHaveSalary = startDate;
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom(mStartDateHaveSalary);
            mRequestOff.setStartDayHaveSalary(offHaveSalaryFrom);
        }
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return mIsVisibleLayoutNoSalary ? DateTimeUtils.convertUiFormatToDataFormat(
                mRequestOff.getEndDayNoSalary().getOffTo(), DATE_FORMAT_YYYY_MM_DD, FORMAT_DATE)
                : DateTimeUtils.convertUiFormatToDataFormat(
                        mRequestOff.getEndDayHaveSalary().getOffPaidTo(), DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE);
    }

    private void setEndDate(String endDate) {
        if (mIsVisibleLayoutNoSalary) {
            mEndDateNoSalary = endDate;
            OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();
            offNoSalaryTo.setOffTo(mEndDateNoSalary);
            mRequestOff.setEndDayNoSalary(offNoSalaryTo);
        } else {
            mEndDateHaveSalary = endDate;
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo(mEndDateHaveSalary);
            mRequestOff.setEndDayHaveSalary(offHaveSalaryTo);
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
        Branch branch = mUser.getBranches().get(mCurrentBranchPosition);
        mRequestOff.setWorkSpaceId(branch.getBranchId());
        mRequestOff.setBranch(branch);
        notifyPropertyChanged(BR.requestOff);
    }

    private void setCurrentGroup() {
        Group group = mUser.getGroups().get(mCurrentGroupPosition);
        mRequestOff.setGroupId(group.getGroupId());
        mRequestOff.setGroup(mUser.getGroups().get(mCurrentGroupPosition));
        notifyPropertyChanged(BR.requestOff);
    }

    @Bindable
    public String getAnnualError() {
        if (mAnnualLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getAnnualLeave() != null ? String.format(
                mContext.getString(R.string.annual_leave_error), mAnnualLeaveAmount) : null;
    }

    @Bindable
    public String getLeaveForMarriageError() {
        if (mLeaveForMarriageAmount == null) {
            return "";
        }
        return mRequestOff.getLeaveForMarriage() != null ? String.format(
                mContext.getString(R.string.leave_for_marriage_error), mLeaveForMarriageAmount)
                : null;
    }

    @Bindable
    public String getLeaveForChildMarriageError() {
        if (mLeaveForChildMarriageAmount == null) {
            return "";
        }
        return mRequestOff.getLeaveForChildMarriage() != null ? String.format(
                mContext.getString(R.string.leave_for_child_marriage_error),
                mLeaveForChildMarriageAmount) : null;
    }

    @Bindable
    public String getFuneralLeaveError() {
        if (mFuneralLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getFuneralLeave() != null ? String.format(
                mContext.getString(R.string.funeral_leave_error), mFuneralLeaveAmount) : null;
    }

    @Bindable
    public String getLeaveForCareOfSickChildError() {
        if (mLeaveForCareOfSickChildAmount == null) {
            return "";
        }
        return mRequestOff.getLeaveForCareOfSickChild() != null ? String.format(
                mContext.getString(R.string.leave_for_care_of_sick_error),
                mLeaveForCareOfSickChildAmount) : null;
    }

    @Bindable
    public String getPregnancyExaminationLeaveError() {
        if (mPregnancyExaminationLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getPregnancyExaminationLeave() != null ? String.format(
                mContext.getString(R.string.pregnacy_examination_leave_error),
                mPregnancyExaminationLeaveAmount) : null;
    }

    @Bindable
    public String getSickLeaveError() {
        if (mSickLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getSickLeave() != null ? String.format(
                mContext.getString(R.string.sick_leave_error), mSickLeaveAmount) : null;
    }

    @Bindable
    public String getMiscarriageLeaveError() {
        if (mMiscarriageLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getMiscarriageLeave() != null ? String.format(
                mContext.getString(R.string.miscarriage_leave_error), mMiscarriageLeaveAmount)
                : null;
    }

    @Bindable
    public String getMaternityLeavedError() {
        if (mMaternityLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getMaternityLeave() != null ? String.format(
                mContext.getString(R.string.maternity_leave_error), mMaternityLeaveAmount) : null;
    }

    @Bindable
    public String getWifeLaborLeaveError() {
        if (mWifeLaborLeaveAmount == null) {
            return "";
        }
        return mRequestOff.getWifeLaborLeave() != null ? String.format(
                mContext.getString(R.string.wife_labor_leave_error), mWifeLaborLeaveAmount) : null;
    }

    @Bindable
    public String getAnnualLeave() {
        return mAnnualLeave;
    }

    public void setAnnualLeave(String annualLeave) {
        mAnnualLeave = annualLeave;
        mRequestOff.setAnnualLeave(mAnnualLeave);
        notifyPropertyChanged(BR.annualLeave);
    }

    @Bindable
    public String getLeaveForMarriage() {
        return mLeaveForMarriage;
    }

    public void setLeaveForMarriage(String leaveForMarriage) {
        mLeaveForMarriage = leaveForMarriage;
        mRequestOff.setLeaveForMarriage(mLeaveForMarriage);
        notifyPropertyChanged(BR.leaveForMarriage);
    }

    @Bindable
    public String getLeaveForChildMarriage() {
        return mLeaveForChildMarriage;
    }

    public void setLeaveForChildMarriage(String leaveForChildMarriage) {
        mLeaveForChildMarriage = leaveForChildMarriage;
        mRequestOff.setLeaveForChildMarriage(mLeaveForChildMarriage);
        notifyPropertyChanged(BR.leaveForChildMarriage);
    }

    @Bindable
    public String getFuneralLeave() {
        return mFuneralLeave;
    }

    public void setFuneralLeave(String funeralLeave) {
        mFuneralLeave = funeralLeave;
        mRequestOff.setFuneralLeave(mFuneralLeave);
        notifyPropertyChanged(BR.funeralLeave);
    }

    @Bindable
    public String getLeaveForCareOfSickChild() {
        return mLeaveForCareOfSickChild;
    }

    public void setLeaveForCareOfSickChild(String leaveForCareOfSickChild) {
        mLeaveForCareOfSickChild = leaveForCareOfSickChild;
        mRequestOff.setLeaveForCareOfSickChild(mLeaveForCareOfSickChild);
        notifyPropertyChanged(BR.leaveForCareOfSickChild);
    }

    @Bindable
    public String getPregnancyExaminationLeave() {
        return mPregnancyExaminationLeave;
    }

    public void setPregnancyExaminationLeave(String pregnancyExaminationLeave) {
        mPregnancyExaminationLeave = pregnancyExaminationLeave;
        mRequestOff.setPregnancyExaminationLeave(mPregnancyExaminationLeave);
        notifyPropertyChanged(BR.pregnancyExaminationLeave);
    }

    @Bindable
    public String getSickLeave() {
        return mSickLeave;
    }

    public void setSickLeave(String sickLeave) {
        mSickLeave = sickLeave;
        mRequestOff.setSickLeave(mSickLeave);
        notifyPropertyChanged(BR.sickLeave);
    }

    @Bindable
    public String getMiscarriageLeave() {
        return mMiscarriageLeave;
    }

    public void setMiscarriageLeave(String miscarriageLeave) {
        mMiscarriageLeave = miscarriageLeave;
        mRequestOff.setMiscarriageLeave(mMiscarriageLeave);
        notifyPropertyChanged(BR.miscarriageLeave);
    }

    @Bindable
    public String getMaternityLeave() {
        return mMaternityLeave;
    }

    public void setMaternityLeave(String maternityLeave) {
        mMaternityLeave = maternityLeave;
        mRequestOff.setMaternityLeave(mMaternityLeave);
        notifyPropertyChanged(BR.maternityLeave);
    }

    @Bindable
    public String getWifeLaborLeave() {
        return mWifeLaborLeave;
    }

    public void setWifeLaborLeave(String wifeLaborLeave) {
        mWifeLaborLeave = wifeLaborLeave;
        mRequestOff.setWifeLaborLeave(mWifeLaborLeave);
        notifyPropertyChanged(BR.wifeLaborLeave);
    }

    @Bindable
    public String getAnnualLeaveAmount() {
        return String.format(mContext.getString(R.string.annual_leave), mAnnualLeaveAmount);
    }

    private void setAnnualLeaveAmount(String annualLeaveAmount) {
        mAnnualLeaveAmount = annualLeaveAmount;
        notifyPropertyChanged(BR.annualLeaveAmount);
    }

    @Bindable
    public String getLeaveForMarriageAmount() {
        return String.format(mContext.getString(R.string.leave_for_marriage),
                mLeaveForMarriageAmount);
    }

    private void setLeaveForMarriageAmount(String leaveForMarriageAmount) {
        mLeaveForMarriageAmount = leaveForMarriageAmount;
        notifyPropertyChanged(BR.leaveForMarriageAmount);
    }

    @Bindable
    public String getLeaveForChildMarriageAmount() {
        return String.format(mContext.getString(R.string.leave_for_child_marriage),
                mLeaveForChildMarriageAmount);
    }

    private void setLeaveForChildMarriageAmount(String leaveForChildMarriageAmount) {
        mLeaveForChildMarriageAmount = leaveForChildMarriageAmount;
        notifyPropertyChanged(BR.leaveForChildMarriageAmount);
    }

    @Bindable
    public String getFuneralLeaveAmount() {
        return String.format(mContext.getString(R.string.funeral_leave), mFuneralLeaveAmount);
    }

    private void setFuneralLeaveAmount(String funeralLeaveAmount) {
        mFuneralLeaveAmount = funeralLeaveAmount;
        notifyPropertyChanged(BR.funeralLeaveAmount);
    }

    @Bindable
    public String getLeaveForCareOfSickChildAmount() {
        return String.format(mContext.getString(R.string.leave_for_care_of_sick_child),
                mLeaveForCareOfSickChildAmount);
    }

    private void setLeaveForCareOfSickChildAmount(String leaveForCareOfSickChildAmount) {
        mLeaveForCareOfSickChildAmount = leaveForCareOfSickChildAmount;
        notifyPropertyChanged(BR.leaveForCareOfSickChildAmount);
    }

    @Bindable
    public String getPregnancyExaminationLeaveAmount() {
        return String.format(mContext.getString(R.string.pregnancy_examination_leave),
                mPregnancyExaminationLeaveAmount);
    }

    private void setPregnancyExaminationLeaveAmount(String pregnancyExaminationLeaveAmount) {
        mPregnancyExaminationLeaveAmount = pregnancyExaminationLeaveAmount;
        notifyPropertyChanged(BR.pregnancyExaminationLeaveAmount);
    }

    @Bindable
    public String getSickLeaveAmount() {
        return String.format(mContext.getString(R.string.sick_leave), mSickLeaveAmount);
    }

    private void setSickLeaveAmount(String sickLeaveAmount) {
        mSickLeaveAmount = sickLeaveAmount;
        notifyPropertyChanged(BR.sickLeaveAmount);
    }

    @Bindable
    public String getMiscarriageLeaveAmount() {
        return String.format(mContext.getString(R.string.miscarriage_leave),
                mMiscarriageLeaveAmount);
    }

    private void setMiscarriageLeaveAmount(String miscarriageLeaveAmount) {
        mMiscarriageLeaveAmount = miscarriageLeaveAmount;
        notifyPropertyChanged(BR.miscarriageLeaveAmount);
    }

    @Bindable
    public String getMaternityLeaveAmount() {
        return String.format(mContext.getString(R.string.maternity_leave), mMaternityLeaveAmount);
    }

    private void setMaternityLeaveAmount(String maternityLeaveAmount) {
        mMaternityLeaveAmount = maternityLeaveAmount;
        notifyPropertyChanged(BR.maternityLeaveAmount);
    }

    @Bindable
    public String getWifeLaborLeaveAmount() {
        return String.format(mContext.getString(R.string.wife_labor_leave), mWifeLaborLeaveAmount);
    }

    private void setWifeLaborLeaveAmount(String wifeLaborLeaveAmount) {
        mWifeLaborLeaveAmount = wifeLaborLeaveAmount;
        notifyPropertyChanged(BR.wifeLaborLeaveAmount);
    }

    private void getAmountOffDayCompany(User user) {
        List<OffType> offTypesCompanyPay = user.getTypesCompany();
        for (int i = 0; i < offTypesCompanyPay.size(); i++) {
            if (offTypesCompanyPay.get(i).getName().equals(ANNUAL)) {
                setAnnualLeaveAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.LEAVE_FOR_MARRIAGE)) {
                setLeaveForMarriageAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE)) {
                setLeaveForChildMarriageAmount(
                        String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
            if (offTypesCompanyPay.get(i).getId() == Integer.parseInt(TypeOfDays.FUNERAL_LEAVE)) {
                setFuneralLeaveAmount(String.valueOf(offTypesCompanyPay.get(i).getAmount()));
            }
        }
    }

    private void getAmountOffDayInsurance(User user) {
        List<OffType> offTypesInsurancePay = user.getTypesInsurance();
        for (int i = 0; i < offTypesInsurancePay.size(); i++) {
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD)) {
                setLeaveForCareOfSickChildAmount(
                        String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.PREGNANCY_EXAMINATON)) {
                setPregnancyExaminationLeaveAmount(
                        String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(TypeOfDays.SICK_LEAVE)) {
                setSickLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.MISCARRIAGE_LEAVE)) {
                setMiscarriageLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.MATERNTY_LEAVE)) {
                setMaternityLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
            if (offTypesInsurancePay.get(i).getId() == Integer.parseInt(
                    TypeOfDays.WIFE_LABOR_LEAVE)) {
                setWifeLaborLeaveAmount(String.valueOf(offTypesInsurancePay.get(i).getAmount()));
            }
        }
    }

    private boolean layoutCompanyPayIsVisible() {
        return (mRequestOff.getAnnualLeave() != null
                && !"".equals(mRequestOff.getAnnualLeave())
                && !Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getAnnualLeave()))
                || (mRequestOff.getLeaveForChildMarriage() != null
                && !"".equals(mRequestOff.getLeaveForChildMarriage()))
                || (mRequestOff.getLeaveForMarriage() != null && !"".equals(
                mRequestOff.getLeaveForMarriage()))
                || (mRequestOff.getFuneralLeave() != null && !"".equals(
                mRequestOff.getFuneralLeave()));
    }

    private boolean dayOffTypeListNoneEmpty() {
        return mRequestOff != null
                && mRequestOff.getRequestDayOffTypes() != null
                && mRequestOff.getRequestDayOffTypes().size() != 0;
    }

    private boolean annualLeaveNoneEmpty() {
        return mRequestOff.getAnnualLeave() != null
                && !"".equals(mRequestOff.getAnnualLeave())
                && !Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getAnnualLeave());
    }

    private boolean leaveForMarriageIsEmpty() {
        return mRequestOff.getLeaveForMarriage() == null || "".equals(
                mRequestOff.getLeaveForMarriage()) || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestOff.getLeaveForMarriage());
    }

    private boolean leaveForChildMarriageIsEmpty() {
        return mRequestOff.getLeaveForChildMarriage() == null || "".equals(
                mRequestOff.getLeaveForChildMarriage()) || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestOff.getLeaveForChildMarriage());
    }

    private boolean maternityLeaveIsEmpty() {
        return mRequestOff.getMaternityLeave() == null
                || "".equals(mRequestOff.getMaternityLeave())
                || Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getMaternityLeave());
    }

    private boolean leaveForCareOfSickChildIsEmpty() {
        return mRequestOff.getLeaveForCareOfSickChild() == null || "".equals(
                mRequestOff.getLeaveForCareOfSickChild()) || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestOff.getLeaveForCareOfSickChild());
    }

    private boolean funeralLeaveIsEmpty() {
        return mRequestOff.getFuneralLeave() == null
                || "".equals(mRequestOff.getFuneralLeave())
                || Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getFuneralLeave());
    }

    private boolean wifeLaborLeaveIsEmpty() {
        return mRequestOff.getWifeLaborLeave() == null
                || "".equals(mRequestOff.getWifeLaborLeave())
                || Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getWifeLaborLeave());
    }

    private boolean miscarriageLeaveIsEmpty() {
        return mRequestOff.getMiscarriageLeave() == null || "".equals(
                mRequestOff.getMiscarriageLeave()) || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestOff.getMiscarriageLeave());
    }

    private boolean sickLeaveIsEmpty() {
        return mRequestOff.getSickLeave() == null
                || "".equals(mRequestOff.getSickLeave())
                || Constant.DEFAULT_DOUBLE_VALUE.equals(mRequestOff.getSickLeave());
    }

    private boolean pregnancyExaminationLeaveIsEmpty() {
        return mRequestOff.getPregnancyExaminationLeave() == null || "".equals(
                mRequestOff.getPregnancyExaminationLeave()) || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestOff.getPregnancyExaminationLeave());
    }

    private boolean validateAllNumberDayHaveSalary() {
        return validateAnnualLeave(mAnnualLeave)
                && validateLeaveForMarriage(mLeaveForMarriage)
                && validateLeaveForChildMarriage(mLeaveForChildMarriage)
                && validateFuneralLeave(mFuneralLeave)
                && validateLeaveForCareOfSickChild(mLeaveForCareOfSickChild)
                && validatePregnancyExaminationLeave(mPregnancyExaminationLeave)
                && validateSickLeave(mSickLeave)
                && validateMiscarriageLeave(mMiscarriageLeave)
                && validateMaternityLeave(mMaternityLeave)
                && validateWifeLaborLeave(mWifeLaborLeave);
    }

    private boolean validateAnnualLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mAnnualLeaveAmount);
    }

    private boolean validateLeaveForMarriage(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mLeaveForMarriageAmount);
    }

    private boolean validateLeaveForChildMarriage(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mLeaveForChildMarriageAmount);
    }

    private boolean validateFuneralLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mFuneralLeaveAmount);
    }

    private boolean validateLeaveForCareOfSickChild(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mLeaveForCareOfSickChildAmount);
    }

    private boolean validatePregnancyExaminationLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mPregnancyExaminationLeaveAmount);
    }

    private boolean validateSickLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mSickLeaveAmount);
    }

    private boolean validateMiscarriageLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mMiscarriageLeaveAmount);
    }

    private boolean validateMaternityLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mMaternityLeaveAmount);
    }

    private boolean validateWifeLaborLeave(String input) {
        return StringUtils.convertStringToDouble(input) <= StringUtils.convertStringToDouble(
                mWifeLaborLeaveAmount);
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.request_off);
        }
        return mContext.getString(R.string.edit_request_off);
    }

    public void validateNumberDayHaveSalary() {
        if (!validateAnnualLeave(mAnnualLeave)) {
            notifyPropertyChanged(BR.annualError);
        }
        if (!validateLeaveForMarriage(mLeaveForMarriage)) {
            notifyPropertyChanged(BR.leaveForMarriageError);
        }
        if (!validateLeaveForChildMarriage(mLeaveForChildMarriage)) {
            notifyPropertyChanged(BR.leaveForChildMarriageError);
        }
        if (!validateFuneralLeave(mFuneralLeave)) {
            notifyPropertyChanged(BR.funeralLeaveError);
        }
        if (!validateLeaveForCareOfSickChild(mLeaveForCareOfSickChild)) {
            notifyPropertyChanged(BR.leaveForCareOfSickChildError);
        }
        if (!validatePregnancyExaminationLeave(mPregnancyExaminationLeave)) {
            notifyPropertyChanged(BR.pregnancyExaminationLeaveError);
        }
        if (!validateSickLeave(mSickLeave)) {
            notifyPropertyChanged(BR.sickLeaveError);
        }
        if (!validateMiscarriageLeave(mMiscarriageLeave)) {
            notifyPropertyChanged(BR.miscarriageLeaveError);
        }
        if (!validateMaternityLeave(mMaternityLeave)) {
            notifyPropertyChanged(BR.maternityLeavedError);
        }
        if (!validateWifeLaborLeave(mWifeLaborLeave)) {
            notifyPropertyChanged(BR.wifeLaborLeaveError);
        }
    }

    private double getSumDateOffHaveSalary() {
        return StringUtils.convertStringToDouble(mRequestOff.getAnnualLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getLeaveForMarriage())
                + StringUtils.convertStringToDouble(mRequestOff.getFuneralLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getLeaveForChildMarriage())
                + StringUtils.convertStringToDouble(mRequestOff.getLeaveForCareOfSickChild())
                + StringUtils.convertStringToDouble(mRequestOff.getSickLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getMaternityLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getPregnancyExaminationLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getMiscarriageLeave())
                + StringUtils.convertStringToDouble(mRequestOff.getWifeLaborLeave());
    }

    private void setDestroyDayOffTypesAttribute() {
        if (mRequestDayOffTypesAttribute.getNumberDayOff() == null
                || "".equals(mRequestDayOffTypesAttribute.getNumberDayOff())
                || Constant.DEFAULT_DOUBLE_VALUE.equals(
                mRequestDayOffTypesAttribute.getNumberDayOff())) {
            mRequestDayOffTypesAttribute.setDestroy(TRUE);
        } else {
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
        }
    }

    private void setAttributeOffType(String specialDayOffSettingId) {
        mRequestDayOffTypesAttribute = new OffRequest.RequestDayOffTypesAttribute();
        mRequestDayOffTypesAttribute.setId(null);
        mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(specialDayOffSettingId);
        mRequestDayOffTypesAttribute.setDestroy(FALSE);
        if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_MARRIAGE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForMarriage());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForChildMarriage());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.MATERNTY_LEAVE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getMaternityLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForCareOfSickChild());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.FUNERAL_LEAVE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getFuneralLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.WIFE_LABOR_LEAVE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getWifeLaborLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.MISCARRIAGE_LEAVE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getMiscarriageLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.SICK_LEAVE)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getSickLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (specialDayOffSettingId.equals(TypeOfDays.PREGNANCY_EXAMINATON)) {
            mRequestDayOffTypesAttribute.setNumberDayOff(
                    mRequestOff.getPregnancyExaminationLeave());
            setDestroyDayOffTypesAttribute();
        }
        if (dayOffTypeListNoneEmpty()) {
            for (int i = 0; i < mRequestOff.getRequestDayOffTypes().size(); i++) {
                if (mRequestOff.getRequestDayOffTypes().get(i).getSpecialDayOffSettingId()
                        == Integer.parseInt(specialDayOffSettingId)) {
                    mRequestDayOffTypesAttribute.setId(
                            mRequestOff.getRequestDayOffTypes().get(i).getId());
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_MARRIAGE)) {
                if (leaveForMarriageIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE)) {
                if (leaveForChildMarriageIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.MATERNTY_LEAVE)) {
                if (maternityLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD)) {
                if (leaveForCareOfSickChildIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.FUNERAL_LEAVE)) {
                if (funeralLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.WIFE_LABOR_LEAVE)) {
                if (wifeLaborLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.MISCARRIAGE_LEAVE)) {
                if (miscarriageLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.SICK_LEAVE)) {
                if (sickLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
            if (specialDayOffSettingId.equals(TypeOfDays.PREGNANCY_EXAMINATON)) {
                if (pregnancyExaminationLeaveIsEmpty()) {
                    mRequestDayOffTypesAttribute.setDestroy(TRUE);
                }
            }
        }
        mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
    }

    private void setRequestDayOffTypesAttribute() {
        mRequestDayOffTypesAttributes.clear();
        if (annualLeaveNoneEmpty()) {
            mRequestOff.setNumberDayOffNormal(Double.parseDouble(mRequestOff.getAnnualLeave()));
        }
        setAttributeOffType(TypeOfDays.LEAVE_FOR_MARRIAGE);
        setAttributeOffType(TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE);
        setAttributeOffType(TypeOfDays.MATERNTY_LEAVE);
        setAttributeOffType(TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD);
        setAttributeOffType(TypeOfDays.FUNERAL_LEAVE);
        setAttributeOffType(TypeOfDays.WIFE_LABOR_LEAVE);
        setAttributeOffType(TypeOfDays.MISCARRIAGE_LEAVE);
        setAttributeOffType(TypeOfDays.SICK_LEAVE);
        setAttributeOffType(TypeOfDays.PREGNANCY_EXAMINATON);
        mRequestOff.setRequestDayOffTypesAttributes(mRequestDayOffTypesAttributes);
    }

    private void setRequestOff() {
        if (mStartDateHaveSalary != null) {
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom(mStartDateHaveSalary);
            offHaveSalaryFrom.setPaidFromPeriod(mCurrentDaySessionStartDayHaveSalary);
            mRequestOff.setStartDayHaveSalary(offHaveSalaryFrom);
        }
        if (mEndDateHaveSalary != null) {
            OffRequest.OffHaveSalaryTo offHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            offHaveSalaryTo.setOffPaidTo(mEndDateHaveSalary);
            offHaveSalaryTo.setPaidToPeriod(mCurrentDaySessionEndDayHaveSalary);
            mRequestOff.setEndDayHaveSalary(offHaveSalaryTo);
        }
        if (mStartDateNoSalary != null) {
            OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            offNoSalaryFrom.setOffFrom(mStartDateNoSalary);
            offNoSalaryFrom.setOffFromPeriod(mCurrentDaySessionStartDayNoSalary);
            mRequestOff.setStartDayNoSalary(offNoSalaryFrom);
        }
        if (mEndDateNoSalary != null) {
            OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();
            offNoSalaryTo.setOffTo(mEndDateNoSalary);
            offNoSalaryTo.setOffToPeriod(mCurrentDaySessionEndDayNoSalary);
            mRequestOff.setEndDayNoSalary(offNoSalaryTo);
        }
        setRequestDayOffTypesAttribute();
    }

    private void setBranchAndGroupWhenEdit(User user) {
        if (user.getBranches() == null || user.getBranches().size() == 0) {
            return;
        }
        for (int i = 0; i < user.getBranches().size(); i++) {
            String branchName = user.getBranches().get(i).getBranchName();
            if (branchName.equals(mRequestOff.getBranch().getBranchName())) {
                mCurrentBranchPosition = i;
                mRequestOff.setWorkSpaceId(user.getBranches().get(i).getBranchId());
            }
        }
        if (user.getGroups() == null || user.getGroups().size() == 0) {
            return;
        }
        for (int i = 0; i < user.getGroups().size(); i++) {
            String groupName = user.getGroups().get(i).getGroupName();
            if (groupName.equals(mRequestOff.getGroup().getGroupName())) {
                mCurrentGroupPosition = i;
                mRequestOff.setGroupId(user.getGroups().get(i).getGroupId());
            }
        }
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
                mCurrentGroupPosition, new MaterialDialog.ListCallbackSingleChoice() {
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

    private void showErrorDialogWithButtonRetry(String errorMessage) {
        mDialogManager.dialogError(errorMessage, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mDialogManager.showDatePickerDialog();
            }
        });
    }

    private void showErrorDialog(String errorMessage) {
        mDialogManager.dialogError(errorMessage);
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

    private void validateEndDate(String endDate) {
        if (mIsVisibleLayoutNoSalary) {
            if (DateTimeUtils.convertStringToDate(endDate)
                    .before(DateTimeUtils.convertStringToDate(mStartDateNoSalary))) {
                setEndDate(null);
                showErrorDialogWithButtonRetry(
                        mContext.getString(R.string.end_date_must_greater_than_start_day));
            } else {
                setEndDate(endDate);
            }
            return;
        }
        if (DateTimeUtils.convertStringToDate(endDate)
                .before(DateTimeUtils.convertStringToDate(mStartDateHaveSalary))) {
            setEndDate(null);
            showErrorDialogWithButtonRetry(
                    mContext.getString(R.string.end_date_must_greater_than_start_day));
            return;
        }
        setEndDate(endDate);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickNext(View view) {
        if (!mPresenter.validateData(mRequestOff)) {
            showErrorDialog(mContext.getString(R.string.the_field_required_can_not_be_blank));
            return;
        }
        if (!validateAllNumberDayHaveSalary()) {
            return;
        }

        setRequestOff();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, mActionType);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequestOff);

        if (getSumDateOffHaveSalary() > 0) {
            if (StringUtils.isBlank(mEndDateHaveSalary)) {
                showErrorDialog(mContext.getString(R.string.the_field_required_can_not_be_blank));
                return;
            }
            if (mStartDateNoSalary != null && mEndDateNoSalary == null) {
                showErrorDialog(mContext.getString(R.string.the_field_required_can_not_be_blank));
                return;
            }
            mNavigator.startActivityForResult(ConfirmRequestOffActivity.class, bundle,
                    Constant.RequestCode.REQUEST_OFF);
        } else {
            if (StringUtils.isBlank(mEndDateHaveSalary) && StringUtils.isBlank(
                    mStartDateHaveSalary)) {
                if (StringUtils.isBlank(mEndDateNoSalary) || StringUtils.isBlank(
                        mStartDateNoSalary)) {
                    showErrorDialog(
                            mContext.getString(R.string.the_field_required_can_not_be_blank));
                    return;
                } else {
                    if (StringUtils.isNotBlank(mEndDateHaveSalary) || StringUtils.isNotBlank(
                            mEndDateHaveSalary)) {
                        showErrorDialog(
                                mContext.getString(R.string.the_field_required_can_not_be_blank));
                        return;
                    }
                    mNavigator.startActivityForResult(ConfirmRequestOffActivity.class, bundle,
                            Constant.RequestCode.REQUEST_OFF);
                    return;
                }
            }
            showErrorDialog(mContext.getString(R.string.the_number_of_days_allowed));
        }
    }

    @Bindable
    public OffRequest getRequestOff() {
        return mRequestOff;
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
    public @interface DaySession {
        int AM = 0;
        int PM = 1;
    }

    @StringDef({
            TypeOfDays.ANNUAL_LEAVE, TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE,
            TypeOfDays.LEAVE_FOR_MARRIAGE, TypeOfDays.FUNERAL_LEAVE,
            TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD, TypeOfDays.PREGNANCY_EXAMINATON,
            TypeOfDays.SICK_LEAVE, TypeOfDays.MISCARRIAGE_LEAVE, TypeOfDays.MATERNTY_LEAVE,
            TypeOfDays.WIFE_LABOR_LEAVE
    })
    public @interface TypeOfDays {
        String ANNUAL_LEAVE = "";
        String LEAVE_FOR_CHILD_MARRIAGE = "3";
        String LEAVE_FOR_MARRIAGE = "2";
        String FUNERAL_LEAVE = "10";
        String LEAVE_FOR_CARE_OF_SICK_CHILD = "9";
        String PREGNANCY_EXAMINATON = "22";
        String SICK_LEAVE = "19";
        String MISCARRIAGE_LEAVE = "17";
        String MATERNTY_LEAVE = "6";
        String WIFE_LABOR_LEAVE = "14";
    }
}
