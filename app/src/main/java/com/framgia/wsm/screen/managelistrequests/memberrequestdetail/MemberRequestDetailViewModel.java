package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.Shifts;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.TypeToast;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import java.util.Locale;

/**
 * Created by ths on 11/07/2017.
 */

public class MemberRequestDetailViewModel extends BaseObservable
        implements MemberRequestDetailContract.ViewModel {

    private static final String TAG = "MemberRequestDetailViewModel";
    private static final String ANNUAL = "Annual";

    private Context mContext;
    private MemberRequestDetailContract.Presenter mPresenter;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private OffRequest mOffRequest;
    private LeaveRequest mLeaveRequest;
    private RequestOverTime mOverTimeRequest;
    private DismissDialogListener mDismissDialogListener;
    private ActionRequest mActionRequest;
    private boolean mIsVisibleApprove;
    private boolean mIsVisibleReject;
    private boolean mIsStatusAccept;
    private boolean mIsStatusReject;
    private boolean mIsStatusPending;
    private boolean mIsStatusForward;
    private boolean mIsStatusCancel;
    private int mPosition;
    private ApproveOrRejectListener mResultListener;
    private int mCurrentRequestType;
    private User mUser;
    private boolean mIsForward;

    MemberRequestDetailViewModel(Context context, Fragment fragment,
            MemberRequestDetailContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager, Object item, int position,
            ApproveOrRejectListener resultListener) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mPresenter.getUser();
        mNavigator = navigator;
        mDismissDialogListener = (DismissDialogListener) fragment;
        mDialogManager = dialogManager;
        mOffRequest = new OffRequest();
        mLeaveRequest = new LeaveRequest();
        mOverTimeRequest = new RequestOverTime();
        mActionRequest = new ActionRequest();
        setItemMemberRequest(item);
        setIsVisibleApprove(StatusCode.ACCEPT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mOverTimeRequest.getStatus()));
        setIsVisibleReject(StatusCode.REJECT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mOverTimeRequest.getStatus()));
        setStatusAccept(StatusCode.ACCEPT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mOverTimeRequest.getStatus()));
        setStatusReject(StatusCode.REJECT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mOverTimeRequest.getStatus()));
        setStatusPending(StatusCode.PENDING_CODE.equals(mOffRequest.getStatus())
                || StatusCode.PENDING_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.PENDING_CODE.equals(mOverTimeRequest.getStatus()));
        setStatusCancel(
                StatusCode.CANCELED_CODE.equals(mOffRequest.getStatus()) || StatusCode.CANCELED_CODE
                        .equals(mLeaveRequest.getStatus()) || StatusCode.CANCELED_CODE.equals(
                        mOverTimeRequest.getStatus()));
        mPosition = position;
        mResultListener = resultListener;
    }

    private void setItemMemberRequest(Object item) {
        if (item instanceof LeaveRequest) {
            mLeaveRequest = (LeaveRequest) item;
            mActionRequest.setTypeRequest(TypeRequest.LEAVE);
            mCurrentRequestType = RequestType.REQUEST_LATE_EARLY;
            setForward((!mLeaveRequest.isCanApproveReject() || StatusCode.CANCELED_CODE.equals(
                    mLeaveRequest.getStatus())));
            if (!mLeaveRequest.isCanApproveReject() && StatusCode.FORWARD_CODE.equals(
                    mLeaveRequest.getStatus())) {
                setStatusForward(true);
            }
        } else if (item instanceof RequestOverTime) {
            mOverTimeRequest = (RequestOverTime) item;
            mActionRequest.setTypeRequest(TypeRequest.OT);
            mCurrentRequestType = RequestType.REQUEST_OVERTIME;
            setForward((!mOverTimeRequest.isCanApproveReject() || StatusCode.CANCELED_CODE.equals(
                    mOverTimeRequest.getStatus())));
            if (!mOverTimeRequest.isCanApproveReject() && StatusCode.FORWARD_CODE.equals(
                    mOverTimeRequest.getStatus())) {
                setStatusForward(true);
            }
        } else {
            mOffRequest = (OffRequest) item;
            mActionRequest.setTypeRequest(TypeRequest.OFF);
            mCurrentRequestType = RequestType.REQUEST_OFF;
            setForward((!mOffRequest.isCanApproveReject() || StatusCode.CANCELED_CODE.equals(
                    mOffRequest.getStatus())));
            if (!mOffRequest.isCanApproveReject() && StatusCode.FORWARD_CODE.equals(
                    mOffRequest.getStatus())) {
                setStatusForward(true);
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
    public void onError(BaseException e) {
        mNavigator.showToastCustom(TypeToast.ERROR, e.getMessage());
    }

    @Override
    public void onDismissProgressDialog() {
        mDialogManager.dismissProgressDialog();
    }

    @Override
    public void onShowIndeterminateProgressDialog() {
        mDialogManager.showIndeterminateProgressDialog();
    }

    @Override
    public void onApproveOrRejectRequestSuccess(ActionRequestResponse actionRequestResponse) {
        mResultListener.onApproveOrRejectListener(mCurrentRequestType, mPosition,
                actionRequestResponse);
        if ((actionRequestResponse.getStatus().equals(StatusCode.ACCEPT_CODE))
                || actionRequestResponse.getStatus().equals(StatusCode.FORWARD_CODE)) {
            mNavigator.showToastCustom(TypeToast.SUCCESS,
                    mContext.getString(R.string.approve_success));
        }
        if (actionRequestResponse.getStatus().equals(StatusCode.REJECT_CODE)) {
            mNavigator.showToastCustom(TypeToast.SUCCESS,
                    mContext.getString(R.string.reject_success));
        }
        mDismissDialogListener.onDismissDialog();
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null) {
            return;
        }
        mUser = user;
        notifyPropertyChanged(BR.numberHour);
    }

    public boolean isVisibleOffRequest() {
        return mOffRequest.getId() != 0;
    }

    public boolean isVisibleLeaveRequest() {
        return mLeaveRequest.getId() != null;
    }

    public boolean isVisibleOverTimeRequest() {
        return mOverTimeRequest.getId() != 0;
    }

    public LeaveRequest getLeaveRequest() {
        return mLeaveRequest;
    }

    public RequestOverTime getOverTimeRequest() {
        return mOverTimeRequest;
    }

    public OffRequest getOffRequest() {
        return mOffRequest;
    }

    public String getCreateAtOffRequest() {
        if (mOffRequest.getCreatedAt() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOffRequest.getCreatedAt(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public String getCreateAtLeaveRequest() {
        if (mLeaveRequest.getCreateAt() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCreateAt(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public String getCreateAtOverTimeRequest() {
        if (mOverTimeRequest.getCreatedAt() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getCreatedAt(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public String getToTime() {
        if (mOverTimeRequest.getToTime() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getToTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    public String getFromTime() {
        if (mOverTimeRequest.getFromTime() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getFromTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
    }

    @Bindable
    public String getNumberHour() {
        if (mOverTimeRequest.getFromTime() == null
                || mOverTimeRequest.getToTime() == null
                || mUser == null) {
            return "";
        }
        Shifts shifts = new Shifts();
        for (Branch branch : mUser.getBranches()) {
            if (mOverTimeRequest.getBranch().getBranchName().equals(branch.getBranchName())) {
                shifts = branch.getShifts().get(0);
            }
        }
        float numberHourOvertime = DateTimeUtils.getHourBetweenTwoDate(getToTime(), getFromTime(),
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        if (isContainLunchBreak(shifts)) {
            return String.format(Locale.getDefault(), DateTimeUtils.HOUR_FORMAT_2F,
                    numberHourOvertime - shifts.getNumberHourLunch());
        }
        return String.format(Locale.getDefault(), DateTimeUtils.HOUR_FORMAT_2F, numberHourOvertime);
    }

    private boolean isContainLunchBreak(Shifts shifts) {
        shifts.setTimeLunch(DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeLunch(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
        shifts.setTimeAfternoon(DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeAfternoon(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
        int hourOfTimeAfternoon = DateTimeUtils.getHourOfDay(shifts.getTimeAfternoon());
        int minuteOfTimeAfternoon = DateTimeUtils.getMinute(shifts.getTimeAfternoon());
        int hourOfTimeLunch = DateTimeUtils.getHourOfDay(shifts.getTimeLunch());
        int minuteOfTimeLunch = DateTimeUtils.getMinute(shifts.getTimeLunch());
        return DateTimeUtils.checkHourOfDateLessThanOrEqual(getFromTime(), hourOfTimeLunch,
                minuteOfTimeLunch)
                && !DateTimeUtils.checkHourOfDateLessThan(getToTime(), hourOfTimeAfternoon,
                minuteOfTimeAfternoon)
                && DateTimeUtils.getHourBetweenTwoDate(getToTime(), getFromTime(),
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM) > Constant.TimeConst.FOUR_HOUR;
    }

    @Bindable
    public boolean isVisibleApprove() {
        return mIsVisibleApprove;
    }

    private void setIsVisibleApprove(boolean visibleApprove) {
        mIsVisibleApprove = visibleApprove;
        notifyPropertyChanged(BR.visibleApprove);
    }

    @Bindable
    public boolean isVisibleReject() {
        return mIsVisibleReject;
    }

    private void setIsVisibleReject(boolean visibleReject) {
        mIsVisibleReject = visibleReject;
        notifyPropertyChanged(BR.visibleReject);
    }

    public boolean isVisiableLayoutCompanyPay() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        if (mOffRequest.getNumberDayOffNormal() != null
                && mOffRequest.getNumberDayOffNormal() != 0) {
            return true;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if ((offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_MARRIAGE
                    || offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CHILD_IS_MARRIAGE
                    || offType.getSpecialDayOffSettingId() == OffTypeId.FUNERAL_LEAVE)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableLayoutInsurance() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if ((offType.getSpecialDayOffSettingId() == OffTypeId.MATERNITY_LEAVE
                    || offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CARE_OF_SICK_CHILD
                    || offType.getSpecialDayOffSettingId() == OffTypeId.WIFE_IS_LABOR_LEAVE
                    || offType.getSpecialDayOffSettingId() == OffTypeId.MISCARRIAGE_LEAVE
                    || offType.getSpecialDayOffSettingId() == OffTypeId.SICK_LEAVE
                    || offType.getSpecialDayOffSettingId()
                    == OffTypeId.PREGNANCY_EXAMINATION_LEAVE)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableLayoutTimeCompensation() {
        if (mLeaveRequest.getId() != null) {
            return mLeaveRequest.getCompensation().getFromTime() != null;
        }
        return false;
    }

    public boolean isVisiableLayoutHaveSalary() {
        if (mOffRequest.getId() != 0) {
            return StringUtils.isBlank(mOffRequest.getStartDayHaveSalary().getOffPaidFrom());
        }
        return false;
    }

    public boolean isVisiableLayoutOffNoSalary() {
        if (mOffRequest.getId() != 0) {
            return StringUtils.isBlank(mOffRequest.getStartDayNoSalary().getOffFrom());
        }
        return false;
    }

    public boolean isVisiableAnnualLeave() {
        return mOffRequest.getNumberDayOffNormal() != null
                && mOffRequest.getNumberDayOffNormal() != 0;
    }

    public boolean isVisiableLeaveForChildMarriage() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CHILD_IS_MARRIAGE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableLeaveForMarriage() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_MARRIAGE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableFuneralLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.FUNERAL_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableLeaveForCareOfSickChild() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CARE_OF_SICK_CHILD) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableSickLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.SICK_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableMaternityLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.MATERNITY_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiablePregnancyExaminationLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.PREGNANCY_EXAMINATION_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableMiscarriageLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.MISCARRIAGE_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisiableWifeLaborLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return false;
        }
        for (OffRequest.RequestDayOffType offType : mOffRequest.getRequestDayOffTypes()) {
            if (offType.getSpecialDayOffSettingId() == OffTypeId.WIFE_IS_LABOR_LEAVE) {
                return true;
            }
        }
        return false;
    }

    public String getDateWifeLaborLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.WIFE_IS_LABOR_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateMiscarriageLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.MISCARRIAGE_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDatePregnancyExaminationLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.PREGNANCY_EXAMINATION_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateMaternityLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.MATERNITY_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateSickLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.SICK_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateLeaveForCareOfSickChild() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CARE_OF_SICK_CHILD) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateFuneralLeave() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.FUNERAL_LEAVE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateLeaveAnnual() {
        if (mOffRequest.getNumberDayOffNormal() == null) {
            return "";
        }
        return String.valueOf(mOffRequest.getNumberDayOffNormal());
    }

    public String getDateLeaveForChildIsMarriage() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_CHILD_IS_MARRIAGE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getDateLeaveForMarriage() {
        if (mOffRequest.getRequestDayOffTypes() == null) {
            return "";
        }
        for (OffRequest.RequestDayOffType offRequest : mOffRequest.getRequestDayOffTypes()) {
            if (offRequest.getSpecialDayOffSettingId() == OffTypeId.LEAVE_FOR_MARRIAGE) {
                return String.valueOf(offRequest.getNumberDayOff());
            }
        }
        return "";
    }

    public String getCompensationTime() {
        if (mLeaveRequest.getCompensation() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(
                mLeaveRequest.getCompensation().getFromTime(), DateTimeUtils.INPUT_TIME_FORMAT,
                DateTimeUtils.TIME_FORMAT_HH_MM)
                + Constant.BLANK_DASH_BLANK
                + DateTimeUtils.convertUiFormatToDataFormat(
                mLeaveRequest.getCompensation().getToTime(), DateTimeUtils.INPUT_TIME_FORMAT,
                DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2);
    }

    public String getStartDayHaveSalary() {
        if (mOffRequest.getStartDayHaveSalary() == null) {
            return "";
        }
        return mOffRequest.getStartDayHaveSalary().getOffPaidFrom()
                + Constant.BLANK
                + mOffRequest.getStartDayHaveSalary().getPaidFromPeriod();
    }

    public String getEndDayHaveSalary() {
        if (mOffRequest.getEndDayHaveSalary() == null) {
            return "";
        }
        return mOffRequest.getEndDayHaveSalary().getOffPaidTo()
                + Constant.BLANK
                + mOffRequest.getEndDayHaveSalary().getPaidToPeriod();
    }

    public String getStartDayNoSalary() {
        if (mOffRequest.getStartDayNoSalary() == null) {
            return "";
        }
        return mOffRequest.getStartDayNoSalary().getOffFrom()
                + Constant.BLANK
                + mOffRequest.getStartDayNoSalary().getOffFromPeriod();
    }

    public String getEndDayNoSalary() {
        if (mOffRequest.getEndDayNoSalary() == null) {
            return "";
        }
        return mOffRequest.getEndDayNoSalary().getOffTo()
                + Constant.BLANK
                + mOffRequest.getEndDayNoSalary().getOffToPeriod();
    }

    public String getTimeLeaveRequest() {
        if (mLeaveRequest.getId() != null) {
            final String requestCheckInByTimeAndDate = DateTimeUtils.convertUiFormatToDataFormat(
                    mLeaveRequest.getCheckInTime() + Constant.DATE_TIME_SPACE,
                    DateTimeUtils.INPUT_TIME_FORMAT,
                    DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2);
            final String requestCheckOutByTimeAndDate =
                    DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT,
                            DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY_2);
            final String requestCheckOutByTime =
                    DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String requestCheckInByTime =
                    DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCheckInTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.TIME_FORMAT_HH_MM);
            final String requestCheckOutByDate =
                    DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCheckOutTime(),
                            DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.FORMAT_DATE);
            switch (mLeaveRequest.getLeaveType().getId()) {
                case LeaveType.IN_LATE_M:
                case LeaveType.IN_LATE_A:
                    return Constant.BEGIN_MORNING_TIME
                            + Constant.BLANK_DASH_BLANK
                            + requestCheckInByTimeAndDate;
                case LeaveType.LEAVE_EARLY_M:
                    return requestCheckOutByTime
                            + Constant.DATE_TIME_SPACE
                            + Constant.END_MORNING_TIME
                            + Constant.BLANK
                            + requestCheckOutByDate;
                case LeaveType.LEAVE_OUT:
                case LeaveType.FORGOT_CARD_ALL_DAY:
                case LeaveType.FORGOT_CHECK_ALL_DAY:
                    return requestCheckInByTime
                            + Constant.DATE_TIME_SPACE
                            + Constant.BLANK
                            + requestCheckOutByTimeAndDate;
                case LeaveType.FORGOT_CARD_IN:
                case LeaveType.IN_LATE_WOMAN_M:
                case LeaveType.IN_LATE_WOMAN_A:
                case LeaveType.FORGOT_TO_CHECK_IN:
                    return requestCheckInByTimeAndDate;
                case LeaveType.FORGOT_CARD_OUT:
                case LeaveType.LEAVE_EARLY_A:
                case LeaveType.LEAVE_EARLY_WOMAN_M:
                case LeaveType.LEAVE_EARLY_WOMAN_A:
                case LeaveType.FORGOT_TO_CHECK_OUT:
                    return requestCheckOutByTimeAndDate;
                default:
                    return "";
            }
        }
        return "";
    }

    @Bindable
    public boolean isStatusCancel() {
        return mIsStatusCancel;
    }

    public void setStatusCancel(boolean statusCancel) {
        mIsStatusCancel = statusCancel;
        notifyPropertyChanged(BR.statusCancel);
    }

    @Bindable
    public boolean isAcceptStatus() {
        return mIsStatusAccept;
    }

    private void setStatusAccept(boolean isStatusAccept) {
        mIsStatusAccept = isStatusAccept;
        notifyPropertyChanged(BR.acceptStatus);
    }

    @Bindable
    public boolean isPendingStatus() {
        return mIsStatusPending;
    }

    private void setStatusPending(boolean statusPending) {
        mIsStatusPending = statusPending;
        notifyPropertyChanged(BR.pendingStatus);
    }

    private void setStatusReject(boolean isStatusReject) {
        mIsStatusReject = isStatusReject;
        notifyPropertyChanged(BR.rejectStatus);
    }

    @Bindable
    public boolean isRejectStatus() {
        return mIsStatusReject;
    }

    public String getAnnualLeaveRemaining() {
        return mContext.getString(R.string.annual_leave_detail);
    }

    public String getLeaveForMarriageRemaining() {
        return mContext.getString(R.string.leave_for_marriage_detail);
    }

    public String getLeaveForChildMarriageRemaining() {
        return mContext.getString(R.string.leave_for_child_marriage_detail);
    }

    public String getFuneralLeaveRemaining() {
        return mContext.getString(R.string.funeral_leave_detail);
    }

    public String getLeaveForCareOfSickChildRemaining() {
        return mContext.getString(R.string.leave_for_care_of_sick_child_detail);
    }

    public String getPregnancyExaminationLeaveRemaining() {
        return mContext.getString(R.string.pregnancy_examination_leave_detail);
    }

    public String getSickLeaveRemaining() {
        return mContext.getString(R.string.sick_leave_detail);
    }

    public String getMiscarriageLeaveRemaining() {
        return mContext.getString(R.string.miscarriage_leave_detail);
    }

    public String getMaternityLeaveRemaining() {
        return mContext.getString(R.string.maternity_leave_detail);
    }

    public String getWifeLaborLeaveRemaining() {
        return mContext.getString(R.string.wife_labor_leave_detail);
    }

    public boolean isVisibleLayoutOffRequestTitle() {
        return (isVisiableLayoutCompanyPay() || isVisiableLayoutInsurance());
    }

    @Bindable
    public boolean isForward() {
        return mIsForward;
    }

    private void setForward(boolean forward) {
        mIsForward = forward;
        notifyPropertyChanged(BR.forward);
    }

    @Bindable
    public boolean isStatusForward() {
        return mIsStatusForward;
    }

    public void setStatusForward(boolean statusForward) {
        mIsStatusForward = statusForward;
        notifyPropertyChanged(BR.statusForward);
    }

    public void onClickArrowBack() {
        mDismissDialogListener.onDismissDialog();
    }

    public void onClickRejected() {
        if (mOffRequest.getId() != 0) {
            mActionRequest.setStatus(StatusCode.REJECT_CODE);
            mActionRequest.setRequestId(mOffRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        } else if (mOverTimeRequest.getId() != 0) {
            mActionRequest.setStatus(StatusCode.REJECT_CODE);
            mActionRequest.setRequestId(mOverTimeRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        } else {
            mActionRequest.setStatus(StatusCode.REJECT_CODE);
            mActionRequest.setRequestId(mLeaveRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        }
    }

    public void onClickApprove() {
        if (mOffRequest.getId() != 0) {
            mActionRequest.setStatus(StatusCode.ACCEPT_CODE);
            mActionRequest.setRequestId(mOffRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        } else if (mOverTimeRequest.getId() != 0) {
            mActionRequest.setStatus(StatusCode.ACCEPT_CODE);
            mActionRequest.setRequestId(mOverTimeRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        } else {
            mActionRequest.setStatus(StatusCode.ACCEPT_CODE);
            mActionRequest.setRequestId(mLeaveRequest.getId());
            mPresenter.approveOrRejectRequest(mActionRequest);
        }
    }

    @IntDef({
            LeaveType.IN_LATE_M, LeaveType.IN_LATE_A, LeaveType.LEAVE_EARLY_M,
            LeaveType.LEAVE_EARLY_A, LeaveType.LEAVE_OUT, LeaveType.FORGOT_CHECK_ALL_DAY,
            LeaveType.FORGOT_TO_CHECK_IN, LeaveType.FORGOT_TO_CHECK_OUT,
            LeaveType.FORGOT_CARD_ALL_DAY, LeaveType.FORGOT_CARD_IN, LeaveType.FORGOT_CARD_OUT,
            LeaveType.IN_LATE_WOMAN_M, LeaveType.IN_LATE_WOMAN_A, LeaveType.LEAVE_EARLY_WOMAN_M,
            LeaveType.LEAVE_EARLY_WOMAN_A
    })
    public @interface LeaveType {
        int IN_LATE_M = 1;
        int LEAVE_EARLY_M = 2;
        int LEAVE_OUT = 3;
        int IN_LATE_A = 4;
        int IN_LATE_WOMAN_M = 5;
        int FORGOT_CHECK_ALL_DAY = 7;
        int FORGOT_TO_CHECK_IN = 12;
        int FORGOT_TO_CHECK_OUT = 13;
        int LEAVE_EARLY_A = 14;
        int IN_LATE_WOMAN_A = 15;
        int LEAVE_EARLY_WOMAN_M = 16;
        int LEAVE_EARLY_WOMAN_A = 17;
        int FORGOT_CARD_IN = 19;
        int FORGOT_CARD_OUT = 20;
        int FORGOT_CARD_ALL_DAY = 21;
    }

    @IntDef({
            OffTypeId.LEAVE_FOR_MARRIAGE, OffTypeId.LEAVE_FOR_CHILD_IS_MARRIAGE,
            OffTypeId.MATERNITY_LEAVE, OffTypeId.LEAVE_FOR_CARE_OF_SICK_CHILD,
            OffTypeId.FUNERAL_LEAVE, OffTypeId.WIFE_IS_LABOR_LEAVE, OffTypeId.MISCARRIAGE_LEAVE,
            OffTypeId.SICK_LEAVE, OffTypeId.PREGNANCY_EXAMINATION_LEAVE
    })
    public @interface OffTypeId {
        int LEAVE_FOR_MARRIAGE = 2;
        int LEAVE_FOR_CHILD_IS_MARRIAGE = 3;
        int MATERNITY_LEAVE = 6;
        int LEAVE_FOR_CARE_OF_SICK_CHILD = 9;
        int FUNERAL_LEAVE = 10;
        int WIFE_IS_LABOR_LEAVE = 14;
        int MISCARRIAGE_LEAVE = 17;
        int SICK_LEAVE = 19;
        int PREGNANCY_EXAMINATION_LEAVE = 22;
    }

    @StringDef({
            TypeRequest.LEAVE, TypeRequest.OFF, TypeRequest.OT
    })
    @interface TypeRequest {
        String LEAVE = "leave";
        String OFF = "off";
        String OT = "ot";
    }

    public interface ApproveOrRejectListener {
        void onApproveOrRejectListener(@RequestType int requestType, int itemPosition,
                ActionRequestResponse actionRequestResponse);
    }
}
