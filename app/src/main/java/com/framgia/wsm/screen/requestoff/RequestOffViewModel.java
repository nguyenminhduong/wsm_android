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
    private OffRequest.OffHaveSalaryFrom mOffHaveSalaryFrom;
    private OffRequest.OffHaveSalaryTo mOffHaveSalaryTo;
    private OffRequest.OffNoSalaryFrom mOffNoSalaryFrom;
    private OffRequest.OffNoSalaryTo mOffNoSalaryTo;
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
    private OffRequest.RequestDayOffTypesAttribute mRequestDayOffTypesAttribute =
            new OffRequest.RequestDayOffTypesAttribute();

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
        initData(requestOff);
        mActionType = actionType;
    }

    private void initData(OffRequest requestOff) {
        String am = mContext.getString(R.string.am);
        String pm = mContext.getString(R.string.pm);
        if (requestOff == null) {
            mRequestOff = new OffRequest();
            mOffHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            mRequestOff.setStartDayHaveSalary(mOffHaveSalaryFrom);
            mOffHaveSalaryTo = new OffRequest.OffHaveSalaryTo();
            mRequestOff.setEndDayHaveSalary(mOffHaveSalaryTo);
            mOffNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            mRequestOff.setStartDayNoSalary(mOffNoSalaryFrom);
            mOffNoSalaryTo = new OffRequest.OffNoSalaryTo();
            mRequestOff.setEndDayNoSalary(mOffNoSalaryTo);

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

            String startDayHaveSalary = mRequestOff.getStartDayHaveSalary().getOffPaidFrom();
            String endDayHaveSalary = mRequestOff.getEndDayHaveSalary().getOffPaidTo();
            String startDayNoSalary = mRequestOff.getStartDayNoSalary().getOffFrom();
            String endDayNoSalary = mRequestOff.getEndDayNoSalary().getOffTo();

            if ("".equals(startDayHaveSalary)) {
                setVisibleLayoutNoSalary(true);
                mCurrentPositionOffType = PositionOffType.OFF_NO_SALARY;
                mCurrentOffType = mContext.getString(R.string.off_no_salary);
                mStartDateNoSalary = startDayNoSalary;
                mEndDateNoSalary = endDayNoSalary;
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
            } else {
                setVisibleLayoutCompanyPay(true);
                mCurrentPositionOffType = PositionOffType.OFF_HAVE_SALARY_COMPANY_PAY;
                mCurrentOffType = mContext.getString(R.string.off_have_salary_company_pay);
                mStartDateHaveSalary = startDayHaveSalary;
                mEndDateHaveSalary = endDayHaveSalary;
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
    public void onInputNumberDayHaveSalaryError(String typeOfDay) {
        switch (typeOfDay) {
            case TypeOfDays.ANNUAL_LEAVE:
                notifyPropertyChanged(BR.annualError);
                break;
            case TypeOfDays.LEAVE_FOR_MARRIAGE:
                notifyPropertyChanged(BR.leaveForMarriageError);
                break;
            case TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE:
                notifyPropertyChanged(BR.leaveForChildMarriageError);
                break;
            case TypeOfDays.FUNERAL_LEAVE:
                notifyPropertyChanged(BR.funeralLeaveError);
                break;
            case TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD:
                notifyPropertyChanged(BR.leaveForCareOfSickChildError);
                break;
            case TypeOfDays.PREGNANCY_EXAMINATON:
                notifyPropertyChanged(BR.pregnancyExaminationLeaveError);
                break;
            case TypeOfDays.MISCARRIAGE_LEAVE:
                notifyPropertyChanged(BR.miscarriageLeaveError);
                break;
            case TypeOfDays.SICK_LEAVE:
                notifyPropertyChanged(BR.sickLeaveError);
                break;
            case TypeOfDays.MATERNTY_LEAVE:
                notifyPropertyChanged(BR.maternityLeavedError);
                break;
            case TypeOfDays.WIFE_LABOR_LEAVE:
                notifyPropertyChanged(BR.wifeLaborLeaveError);
                break;
            default:
                break;
        }
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
        return mIsVisibleLayoutNoSalary ? mRequestOff.getStartDayNoSalary().getOffFrom()
                : mRequestOff.getStartDayHaveSalary().getOffPaidFrom();
    }

    private void setStartDate(String startDate) {
        String startDateFormat =
                DateTimeUtils.convertUiFormatToDataFormat(startDate, DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE);
        if (mIsVisibleLayoutNoSalary) {
            mStartDateNoSalary = startDateFormat;
            OffRequest.OffNoSalaryFrom offNoSalaryFrom = new OffRequest.OffNoSalaryFrom();
            offNoSalaryFrom.setOffFrom(mStartDateNoSalary);
            mRequestOff.setStartDayNoSalary(offNoSalaryFrom);
        } else {
            mStartDateHaveSalary = startDateFormat;
            OffRequest.OffHaveSalaryFrom offHaveSalaryFrom = new OffRequest.OffHaveSalaryFrom();
            offHaveSalaryFrom.setOffPaidFrom(mStartDateHaveSalary);
            mRequestOff.setStartDayHaveSalary(offHaveSalaryFrom);
        }
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return mIsVisibleLayoutNoSalary ? mRequestOff.getEndDayNoSalary().getOffTo()
                : mRequestOff.getEndDayHaveSalary().getOffPaidTo();
    }

    private void setEndDate(String endDate) {
        String endDateFormat =
                DateTimeUtils.convertUiFormatToDataFormat(endDate, DATE_FORMAT_YYYY_MM_DD,
                        FORMAT_DATE);
        if (mIsVisibleLayoutNoSalary) {
            mEndDateNoSalary = endDateFormat;
            OffRequest.OffNoSalaryTo offNoSalaryTo = new OffRequest.OffNoSalaryTo();
            offNoSalaryTo.setOffTo(mEndDateNoSalary);
            mRequestOff.setEndDayNoSalary(offNoSalaryTo);
        } else {
            mEndDateHaveSalary = endDateFormat;
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
        return mRequestOff.getCompanyPay().getAnnualLeave() != null ? mContext.getString(
                R.string.annual_leave_is_not_greater_than_14_5_day) : null;
    }

    @Bindable
    public String getLeaveForMarriageError() {
        return mRequestOff.getCompanyPay().getLeaveForMarriage() != null ? mContext.getString(
                R.string.leave_for_marriage_is_not_greater_than_3_day) : null;
    }

    @Bindable
    public String getLeaveForChildMarriageError() {
        return mRequestOff.getCompanyPay().getLeaveForChildMarriage() != null ? mContext.getString(
                R.string.leave_for_child_marriage_is_not_greater_than_1_day) : null;
    }

    @Bindable
    public String getFuneralLeaveError() {
        return mRequestOff.getCompanyPay().getFuneralLeave() != null ? mContext.getString(
                R.string.funeral_leave_is_not_greater_than_3_day) : null;
    }

    @Bindable
    public String getLeaveForCareOfSickChildError() {
        return mRequestOff.getInsuranceCoverage().getLeaveForCareOfSickChild() != null
                ? mContext.getString(R.string.leave_for_care_of_sick_is_not_greater_than_20_day)
                : null;
    }

    @Bindable
    public String getPregnancyExaminationLeaveError() {
        return mRequestOff.getInsuranceCoverage().getPregnancyExaminationLeave() != null
                ? mContext.getString(
                R.string.pregnacy_examination_leave_is_not_greater_than_14_5_day) : null;
    }

    @Bindable
    public String getSickLeaveError() {
        return mRequestOff.getInsuranceCoverage().getSickLeave() != null ? mContext.getString(
                R.string.sick_leave_is_not_greater_than_60_day) : null;
    }

    @Bindable
    public String getMiscarriageLeaveError() {
        return mRequestOff.getInsuranceCoverage().getMiscarriageLeave() != null
                ? mContext.getString(R.string.miscarriage_leave_is_not_greater_than_50_day) : null;
    }

    @Bindable
    public String getMaternityLeavedError() {
        return mRequestOff.getInsuranceCoverage().getMaternityLeave() != null ? mContext.getString(
                R.string.maternity_leave_is_not_greater_than_180_day) : null;
    }

    @Bindable
    public String getWifeLaborLeaveError() {
        return mRequestOff.getInsuranceCoverage().getWifeLaborLeave() != null ? mContext.getString(
                R.string.wife_labor_leave_is_not_greater_than_14_day) : null;
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.request_off);
        }
        return mContext.getString(R.string.edit_request_off);
    }

    public void validateNumberDayHaveSalary() {
        mPresenter.validateNumberDayHaveSalary(mRequestOff);
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

    private void setRequestDayOffTypesAttribute() {
        mRequestDayOffTypesAttributes.clear();
        if (mRequestOff.getAnnualLeave() != null && !"".equals(mRequestOff.getAnnualLeave())) {
            mRequestOff.setNumberDayOffNormal(Integer.parseInt(mRequestOff.getAnnualLeave()));
        }
        if (mRequestOff.getLeaveForMarriage() != null && !"".equals(
                mRequestOff.getLeaveForMarriage())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.LEAVE_FOR_MARRIAGE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForMarriage());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getLeaveForChildMarriage() != null && !"".equals(
                mRequestOff.getLeaveForChildMarriage())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(
                    TypeOfDays.LEAVE_FOR_CHILD_MARRIAGE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForChildMarriage());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getMaternityLeave() != null && !"".equals(
                mRequestOff.getMaternityLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.MATERNTY_LEAVE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getMaternityLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getLeaveForCareOfSickChild() != null && !"".equals(
                mRequestOff.getLeaveForCareOfSickChild())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(
                    TypeOfDays.LEAVE_FOR_CARE_OF_SICK_CHILD);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getLeaveForCareOfSickChild());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getFuneralLeave() != null && !"".equals(mRequestOff.getFuneralLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.FUNERAL_LEAVE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getFuneralLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getWifeLaborLeave() != null && !"".equals(
                mRequestOff.getWifeLaborLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.WIFE_LABOR_LEAVE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getWifeLaborLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getMiscarriageLeave() != null && !"".equals(
                mRequestOff.getMiscarriageLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.MISCARRIAGE_LEAVE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getMiscarriageLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getSickLeave() != null && !"".equals(mRequestOff.getSickLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.SICK_LEAVE);
            mRequestDayOffTypesAttribute.setNumberDayOff(mRequestOff.getSickLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
        if (mRequestOff.getPregnancyExaminationLeave() != null && !"".equals(
                mRequestOff.getPregnancyExaminationLeave())) {
            mRequestDayOffTypesAttribute.setSpecialDayOffSettingId(TypeOfDays.PREGNANCY_EXAMINATON);
            mRequestDayOffTypesAttribute.setNumberDayOff(
                    mRequestOff.getPregnancyExaminationLeave());
            mRequestDayOffTypesAttribute.setDestroy(FALSE);
            mRequestDayOffTypesAttributes.add(mRequestDayOffTypesAttribute);
        }
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
        if (!mPresenter.validateAllNumberDayHaveSalary(mRequestOff)) {
            return;
        }

        setRequestOff();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, mActionType);
        bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, mRequestOff);

        if (getSumDateOffHaveSalary() > 0) {
            if (mEndDateHaveSalary == null) {
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
            if (mEndDateHaveSalary == null) {
                if (mEndDateNoSalary == null) {
                    showErrorDialog(
                            mContext.getString(R.string.the_field_required_can_not_be_blank));
                    return;
                }
                mNavigator.startActivityForResult(ConfirmRequestOffActivity.class, bundle,
                        Constant.RequestCode.REQUEST_OFF);
                return;
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
    @interface TypeOfDays {
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
