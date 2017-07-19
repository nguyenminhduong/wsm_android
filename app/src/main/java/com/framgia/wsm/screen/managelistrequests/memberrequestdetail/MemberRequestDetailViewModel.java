package com.framgia.wsm.screen.managelistrequests.memberrequestdetail;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;

/**
 * Created by ths on 11/07/2017.
 */

public class MemberRequestDetailViewModel extends BaseObservable
        implements MemberRequestDetailContract.ViewModel {

    private static final String TAG = "MemberRequestDetailViewModel";

    private Context mContext;
    private MemberRequestDetailContract.Presenter mPresenter;
    private Navigator mNavigator;
    private DialogManager mDialogManager;
    private OffRequest mOffRequest;
    private LeaveRequest mLeaveRequest;
    private RequestOverTime mOverTimeRequest;
    private DismissDialogListener mDismissDialogListener;

    MemberRequestDetailViewModel(Context context, Fragment fragment,
            MemberRequestDetailContract.Presenter presenter, Navigator navigator,
            DialogManager dialogManager, Object item) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mDismissDialogListener = (DismissDialogListener) fragment;
        mDialogManager = dialogManager;
        mOffRequest = new OffRequest();
        mLeaveRequest = new LeaveRequest();
        mOverTimeRequest = new RequestOverTime();
        setItemMemberRequest(item);
    }

    private void setItemMemberRequest(Object item) {
        if (item instanceof LeaveRequest) {
            mLeaveRequest = (LeaveRequest) item;
        } else if (item instanceof RequestOverTime) {
            mOverTimeRequest = (RequestOverTime) item;
        } else {
            mOffRequest = (OffRequest) item;
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
    public void onRejectedSuccess() {
        //TODO Edit later
        mDismissDialogListener.onDismissDialog();
        mNavigator.showToast(R.string.reject_success);
    }

    @Override
    public void onApproveSuccess() {
        //TODO Edit later
        mDismissDialogListener.onDismissDialog();
        mNavigator.showToast(R.string.approve_success);
    }

    @Override
    public void onRejectedOrAcceptedError(BaseException e) {
        mDialogManager.dialogError(e);
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
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public String getCreateAtLeaveRequest() {
        if (mLeaveRequest.getCreateAt() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mLeaveRequest.getCreateAt(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public String getCreateAtOverTimeRequest() {
        if (mOverTimeRequest.getCreatedAt() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getCreatedAt(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public String getToTime() {
        if (mOverTimeRequest.getToTime() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getToTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public String getFromTime() {
        if (mOverTimeRequest.getFromTime() == null) {
            return "";
        }
        return DateTimeUtils.convertUiFormatToDataFormat(mOverTimeRequest.getFromTime(),
                DateTimeUtils.INPUT_TIME_FORMAT, DateTimeUtils.DATE_TIME_FORMAT_HH_MM_DD_MM_YYYY);
    }

    public boolean isVisibleApprove() {
        return StatusCode.ACCEPT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mOverTimeRequest.getStatus());
    }

    public boolean isVisibleReject() {
        return StatusCode.REJECT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mOverTimeRequest.getStatus());
    }

    public boolean isVisiableLayoutCompanyPay() {
        return mOffRequest.getCompanyPay().getAnnualLeave() != null
                || mOffRequest.getCompanyPay().getLeaveForChildMarriage() != null
                || mOffRequest.getCompanyPay().getLeaveForMarriage() != null
                || mOffRequest.getCompanyPay().getFuneralLeave() != null;
    }

    public boolean isVisiableLayoutInsurance() {
        return mOffRequest.getInsuranceCoverage().getLeaveForCareOfSickChild() != null
                || mOffRequest.getInsuranceCoverage().getSickLeave() != null
                || mOffRequest.getInsuranceCoverage().getMaternityLeave() != null
                || mOffRequest.getInsuranceCoverage().getPregnancyExaminationLeave() != null
                || mOffRequest.getInsuranceCoverage().getMiscarriageLeave() != null
                || mOffRequest.getInsuranceCoverage().getWifeLaborLeave() != null;
    }

    public boolean isVisiableLayoutTimeCompensation() {
        if (mLeaveRequest.getId() != null) {
            return mLeaveRequest.getCompensation().getFromTime() != null;
        }
        return false;
    }

    public boolean isVisiableLayoutHaveSalary() {
        if (mOffRequest.getId() != 0) {
            return mOffRequest.getStartDayHaveSalary().getOffPaidFrom().isEmpty();
        }
        return false;
    }

    public boolean isVisiableLayoutOffNoSalary() {
        if (mOffRequest.getId() != 0) {
            return mOffRequest.getStartDayNoSalary().getOffFrom().isEmpty();
        }
        return false;
    }

    public boolean isVisiableAnnualLeave() {
        return mOffRequest.getCompanyPay().getAnnualLeave() != null;
    }

    public boolean isVisiableLeaveForChildMarriage() {
        return mOffRequest.getCompanyPay().getLeaveForChildMarriage() != null;
    }

    public boolean isVisiableLeaveForMarriage() {
        return mOffRequest.getCompanyPay().getLeaveForMarriage() != null;
    }

    public boolean isVisiableFuneralLeave() {
        return mOffRequest.getCompanyPay().getFuneralLeave() != null;
    }

    public boolean isVisiableLeaveForCareOfSickChild() {
        return mOffRequest.getInsuranceCoverage().getLeaveForCareOfSickChild() != null;
    }

    public boolean isVisiableSickLeave() {
        return mOffRequest.getInsuranceCoverage().getSickLeave() != null;
    }

    public boolean isVisiableMaternityLeave() {
        return mOffRequest.getInsuranceCoverage().getMaternityLeave() != null;
    }

    public boolean isVisiablePregnancyExaminationLeave() {
        return mOffRequest.getInsuranceCoverage().getPregnancyExaminationLeave() != null;
    }

    public boolean isVisiableMiscarriageLeave() {
        return mOffRequest.getInsuranceCoverage().getMiscarriageLeave() != null;
    }

    public boolean isVisiableWifeLaborLeave() {
        return mOffRequest.getInsuranceCoverage().getWifeLaborLeave() != null;
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

    public boolean isAcceptStatus() {
        return StatusCode.ACCEPT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.ACCEPT_CODE.equals(mOverTimeRequest.getStatus());
    }

    public boolean isPendingStatus() {
        return StatusCode.PENDING_CODE.equals(mOffRequest.getStatus())
                || StatusCode.PENDING_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.PENDING_CODE.equals(mOverTimeRequest.getStatus());
    }

    public boolean isRejectStatus() {
        return StatusCode.REJECT_CODE.equals(mOffRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mLeaveRequest.getStatus())
                || StatusCode.REJECT_CODE.equals(mOverTimeRequest.getStatus());
    }

    public void onClickClose() {
        mDismissDialogListener.onDismissDialog();
    }

    public void onClickArrowBack() {
        mDismissDialogListener.onDismissDialog();
    }

    public void onClickRejected() {
        if (mOffRequest.getId() != 0) {
            mPresenter.rejectRequest(mOffRequest.getId());
        } else if (mOverTimeRequest.getId() != 0) {
            mPresenter.rejectRequest(mOverTimeRequest.getId());
        } else {
            mPresenter.rejectRequest(mLeaveRequest.getId());
        }
    }

    public void onClickApprove() {
        if (mOffRequest.getId() != 0) {
            mPresenter.approveRequest(mOffRequest.getId());
        } else if (mOverTimeRequest.getId() != 0) {
            mPresenter.approveRequest(mOverTimeRequest.getId());
        } else {
            mPresenter.approveRequest(mLeaveRequest.getId());
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
}
