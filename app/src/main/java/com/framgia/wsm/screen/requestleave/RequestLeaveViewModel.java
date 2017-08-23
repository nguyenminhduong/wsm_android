package com.framgia.wsm.screen.requestleave;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.LeaveType;
import com.framgia.wsm.data.model.Shifts;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRequestLeave;
import com.framgia.wsm.screen.confirmrequestleave.ConfirmRequestLeaveActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.UserType;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.Date;
import java.util.List;

import static com.framgia.wsm.utils.Constant.TimeConst.FIFTEEN_MINUTES;
import static com.framgia.wsm.utils.Constant.TimeConst.THIRDTY_MINUTES;
import static com.framgia.wsm.utils.common.DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM;
import static com.framgia.wsm.utils.common.DateTimeUtils.INPUT_TIME_FORMAT;

/**
 * Exposes the data to be used in the LeaveRequest screen.
 */
public class RequestLeaveViewModel extends BaseRequestLeave
        implements RequestLeaveContract.ViewModel, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "RequestLeaveActivity";
    private RequestLeaveContract.Presenter mPresenter;
    private Context mContext;
    private Navigator mNavigator;
    private User mUser;
    private String mCurrentBranch;
    private String mCurrentGroup;
    private int mCurrentLeaveType;
    private String mCurrentLeaveTypeName;
    private int mCurrentBranchPosition;
    private int mCurrentGroupPosition;
    private int mCurrentLeaveTypePosition;
    private boolean mIsVisibleLayoutCompensation;
    private boolean mIsVisibleLayoutCheckin;
    private boolean mIsVisibleLayoutCheckout;
    private DialogManager mDialogManager;
    private String mTitleLeaveInType;
    private String mExampleLeaveInType;
    private String mTitleLeaveOutType;
    private String mExampleLeaveOutType;
    private LeaveRequest mRequest;
    private String mProjectNameError;
    private String mReasonError;
    private String mCheckinTimeError;
    private String mCheckoutTimeError;
    private String mCompensationFromTimeError;
    private String mCompensationToTimeError;
    private String mCurrentDate;
    private int mCurrentTimeSelected;
    private int mActionType;
    private String mProjectName;
    private boolean mIsVisibleGroup;
    private boolean mIsCheckInTimeSelected;
    private boolean mIsCheckOutTimeSelected;
    private boolean mIsCompensationFromTimeSelected;
    private List<LeaveType> mLeaveTypes;
    private int mCurrentLeaveTypeId;
    private Shifts mCurrentShifts;
    private int mHourOfTimeIn;
    private int mMinuteOfTimeIn;
    private int mHourOfTimeOut;
    private int mMinuteOfTimeOut;
    private int mHourOfTimeAfternoon;
    private int mMinuteOfTimeAfternoon;
    private int mHourOfTimeLunch;
    private int mMinuteOfTimeLunch;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mReason;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCheckinTime;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCheckoutTime;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCompensationFromTime;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mCompensationToTime;

    RequestLeaveViewModel(Context context, Navigator navigator,
            RequestLeaveContract.Presenter presenter, DialogManager dialogManager,
            LeaveRequest requestLeave, int actionType) {
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mContext = context;
        mNavigator = navigator;
        mDialogManager = dialogManager;
        mDialogManager.dialogDatePicker(this);
        mDialogManager.dialogTimePicker(this);
        setVisibleLayoutCompensation(true);
        setVisibleLayoutCheckin(true);
        mPresenter.getUser();
        initRequest(requestLeave);
        mActionType = actionType;
        setVisibleGroup(mActionType == ActionType.ACTION_CREATE);
        if (mActionType == ActionType.ACTION_CREATE) {
            mCurrentLeaveTypeName = mContext.getString(R.string.leave_type_in_late_m);
        } else {
            mActionType = ActionType.ACTION_EDIT;
            mCurrentLeaveTypeName = mRequest.getLeaveType().getName();
            setLayoutLeaveType(mCurrentLeaveType);
            mRequest.setCompanyName(mRequest.getGroup().getFullName());
        }
    }

    private void initRequest(LeaveRequest requestLeave) {
        if (requestLeave != null) {
            mRequest = requestLeave;
            if (mRequest.getCompensationRequest() == null) {
                mRequest.setCompensationRequest(new LeaveRequest.Compensation());
                if (mRequest.getLeaveTypeId() != null) {
                    mCurrentLeaveType = mRequest.getLeaveTypeId();
                } else {
                    mCurrentLeaveType = mRequest.getLeaveType().getId();
                    mRequest.setLeaveTypeId(mRequest.getLeaveType().getId());
                }
            }
        } else {
            mRequest = new LeaveRequest();
            mRequest.setCompensationRequest(new LeaveRequest.Compensation());
        }
    }

    private int searchLeaveTypePosition(List<LeaveType> leaveTypesList, int currentLeaveTypeId) {
        for (int i = 0; i < leaveTypesList.size(); i++) {
            if (leaveTypesList.get(i).getId() == currentLeaveTypeId) {
                return i;
            }
        }

        return 0;
    }

    private int searchBranchPosition(List<Branch> branchList, String branchName) {
        for (int i = 0; i < branchList.size(); i++) {
            if (branchList.get(i).getBranchName().equals(branchName)) {
                return i;
            }
        }
        return 0;
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
        if (mActionType == ActionType.ACTION_CREATE) {
            setCurrentLeaveType();
            mCurrentBranchPosition =
                    (searchBranchPosition(mUser.getBranches(), mRequest.getWorkspaceName()));
            setCurrentGroup();
        }
        setCurrentBranch();
        setTitleExampleLeave();
        if (mActionType == ActionType.ACTION_EDIT) {
            mCurrentBranchPosition = (searchBranchPosition(mUser.getBranches(),
                    mRequest.getBranch().getBranchName()));
        }
        mRequest.setCompanyId(mUser.getCompany().getId());
        mCurrentLeaveTypePosition =
                (searchLeaveTypePosition(mUser.getLeaveTypes(), mCurrentLeaveType));
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            mCurrentLeaveTypeId = mRequest.getLeaveTypeId();
        }
        if (mUser.getLeaveTypes() == null || mUser.getLeaveTypes().size() == 0) {
            return;
        }
        mLeaveTypes = mUser.getLeaveTypes();
        initShifts();
        setCurrentShifts(mUser.getBranches().get(mCurrentBranchPosition).getShifts().get(0));
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void onInputReasonError(String errorMessage) {
        mReasonError = errorMessage;
        notifyPropertyChanged(BR.reasonError);
    }

    @Override
    public void onInputProjectNameError(String errorMessage) {
        mProjectNameError = errorMessage;
        notifyPropertyChanged(BR.projectNameError);
    }

    @Override
    public void onInputCheckinTimeError(String errorMessage) {
        mCheckinTimeError = errorMessage;
        notifyPropertyChanged(BR.checkinTimeError);
    }

    @Override
    public void onInputCheckoutTimeError(String errorMessage) {
        mCheckoutTimeError = errorMessage;
        notifyPropertyChanged(BR.checkoutTimeError);
    }

    @Override
    public void onInputCompensationFromError(String errorMessage) {
        mCompensationFromTimeError = errorMessage;
        notifyPropertyChanged(BR.compensationFromTimeError);
    }

    @Override
    public void onInputCompensationToError(String errorMessage) {
        mCompensationToTimeError = errorMessage;
        notifyPropertyChanged(BR.compensationToTimeError);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (year == 0) {
            if (mIsCheckInTimeSelected) {
                setCheckinTime(null);
            }
            if (mIsCheckOutTimeSelected) {
                setCheckoutTime(null);
            }
            if (mIsCompensationFromTimeSelected) {
                setCompensationFromTime("");
                setCompensationToTime("");
            }
        }
        if (view.isShown()) {
            mCurrentDate = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
            mDialogManager.showTimePickerDialog();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (!view.isShown()) {
            return;
        }
        String currentDateTime =
                DateTimeUtils.convertDateTimeToString(mCurrentDate, hourOfDay, minute);
        switch (mCurrentLeaveType) {
            case LeaveTypeId.FORGOT_CARD_IN:
            case LeaveTypeId.FORGOT_TO_CHECK_IN:
            case LeaveTypeId.IN_LATE_WOMAN_M:
            case LeaveTypeId.IN_LATE_WOMAN_A:
                validateCheckinTime(currentDateTime);
                break;
            case LeaveTypeId.FORGOT_CARD_OUT:
            case LeaveTypeId.FORGOT_TO_CHECK_OUT:
            case LeaveTypeId.LEAVE_EARLY_WOMAN_A:
            case LeaveTypeId.LEAVE_EARLY_WOMAN_M:
                validateCheckoutTime(currentDateTime);
                break;
            case LeaveTypeId.IN_LATE_A:
            case LeaveTypeId.IN_LATE_M:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_IN) {
                    validateCheckinTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.COMPENSATION_FROM) {
                    validateCompensationFromTime(currentDateTime);
                }
                break;
            case LeaveTypeId.LEAVE_EARLY_A:
            case LeaveTypeId.LEAVE_EARLY_M:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_OUT) {
                    validateCheckoutTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.COMPENSATION_FROM) {
                    validateCompensationFromTime(currentDateTime);
                }
                break;
            case LeaveTypeId.FORGOT_CARD_ALL_DAY:
            case LeaveTypeId.FORGOT_CHECK_ALL_DAY:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_IN) {
                    validateCheckinTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_OUT) {
                    validateCheckoutTime(currentDateTime);
                }
                break;
            case LeaveTypeId.LEAVE_OUT:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_IN) {
                    validateCheckinTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_OUT) {
                    validateCheckoutTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.COMPENSATION_FROM) {
                    validateCompensationFromTime(currentDateTime);
                }
                break;
            default:
                break;
        }
    }

    private void setTitleExampleLeave() {
        switch (mCurrentLeaveType) {
            case LeaveTypeId.IN_LATE_WOMAN_M:
            case LeaveTypeId.IN_LATE_M:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case LeaveTypeId.IN_LATE_WOMAN_A:
            case LeaveTypeId.IN_LATE_A:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;
            case LeaveTypeId.LEAVE_EARLY_WOMAN_M:
            case LeaveTypeId.LEAVE_EARLY_M:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
            case LeaveTypeId.LEAVE_EARLY_WOMAN_A:
            case LeaveTypeId.LEAVE_EARLY_A:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_a),
                        mContext.getString(R.string.exmple_leave_early_a));
                break;
            case LeaveTypeId.LEAVE_OUT:
            case LeaveTypeId.FORGOT_CHECK_ALL_DAY:
            case LeaveTypeId.FORGOT_CARD_ALL_DAY:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_leave_out_from),
                        mContext.getString(R.string.exmple_leave_out_from));
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_out_to),
                        mContext.getString(R.string.exmple_leave_out_to));
                break;
            case LeaveTypeId.FORGOT_CARD_IN:
            case LeaveTypeId.FORGOT_TO_CHECK_IN:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_forgot_in),
                        mContext.getString(R.string.exmple_forgot_in));
                break;
            case LeaveTypeId.FORGOT_CARD_OUT:
            case LeaveTypeId.FORGOT_TO_CHECK_OUT:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_forgot_out),
                        mContext.getString(R.string.exmple_forgot_out));
                break;
            default:
                break;
        }
    }

    public void onPickBranch(View view) {
        if (mUser.getBranches() == null || mUser.getBranches().size() == 0) {
            return;
        }
        final String[] branches = new String[mUser.getBranches().size()];
        for (int i = 0; i < branches.length; i++) {
            branches[i] = mUser.getBranches().get(i).getBranchName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.branch), branches,
                mCurrentBranchPosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int position, CharSequence charSequence) {
                        mCurrentBranchPosition = position;
                        setCurrentShifts(
                                mUser.getBranches().get(mCurrentBranchPosition).getShifts().get(0));

                        if (mActionType == ActionType.ACTION_CREATE
                                || mActionType == ActionType.ACTION_CONFIRM_CREATE) {
                            setCurrentBranch();
                        }
                        if (mActionType == ActionType.ACTION_CONFIRM_EDIT
                                || mActionType == ActionType.ACTION_EDIT) {
                            mRequest.getBranch().setBranchName(branches[position]);
                            mRequest.setWorkspaceName(branches[position]);
                            mRequest.setWorkpaceId(
                                    mUser.getBranches().get(mCurrentBranchPosition).getBranchId());
                            notifyPropertyChanged(BR.currentBranch);
                        }
                        return true;
                    }
                });
    }

    public void onPickGroup(View view) {
        if (mUser.getGroups() == null || mUser.getGroups().size() == 0) {
            return;
        }
        if (!isVisibleGroup()) {
            mDialogManager.dialogError(mContext.getString(R.string.can_not_edit_group));
            return;
        }
        String[] groups = new String[mUser.getGroups().size()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = mUser.getGroups().get(i).getFullName();
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

    public void onPickTypeRequest(View view) {
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            showDialogError(mContext.getString(R.string.can_not_edit_request_type));
            return;
        }
        if (mUser.getLeaveTypes() == null || mUser.getLeaveTypes().size() == 0) {
            return;
        }
        final String[] leaveTypes = new String[mUser.getLeaveTypes().size()];
        for (int i = 0; i < leaveTypes.length; i++) {
            leaveTypes[i] = mUser.getLeaveTypes().get(i).getName();
        }
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.leave_type), leaveTypes,
                mCurrentLeaveTypePosition, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        mCurrentLeaveTypePosition = positionType;
                        setCurrentLeaveType();
                        setTitleExampleLeave();
                        setLayoutLeaveType(mCurrentLeaveType);
                        clearTime();
                        mCurrentLeaveTypeName = leaveTypes[positionType];
                        return true;
                    }
                });
    }

    public void onClickCheckinTime(View view) {
        mIsCheckInTimeSelected = true;
        mIsCheckOutTimeSelected = false;
        mIsCompensationFromTimeSelected = false;
        mCurrentTimeSelected = CurrentTimeSelected.CHECK_IN;
        mDialogManager.showDatePickerDialog();
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            mActionType = ActionType.ACTION_DETAIL;
        }
    }

    public void onClickCheckoutTime(View view) {
        mIsCheckInTimeSelected = false;
        mIsCheckOutTimeSelected = true;
        mIsCompensationFromTimeSelected = false;
        if (checkIfOnlyHaveCheckoutTime()) {
            mCurrentTimeSelected = CurrentTimeSelected.CHECK_OUT;
            mDialogManager.showDatePickerDialog();
            return;
        }
        if (StringUtils.isNotBlank(mCheckinTime)) {
            mCurrentTimeSelected = CurrentTimeSelected.CHECK_OUT;
            mDialogManager.showDatePickerDialog();
            return;
        }
        showDialogError(mContext.getString(R.string.you_have_to_choose_the_check_in_time_first));
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            mActionType = ActionType.ACTION_DETAIL;
        }
    }

    public void onClickCompensationFrom(View view) {
        mIsCheckInTimeSelected = false;
        mIsCheckOutTimeSelected = false;
        mIsCompensationFromTimeSelected = true;
        switch (mCurrentLeaveType) {
            case LeaveTypeId.IN_LATE_A:
            case LeaveTypeId.IN_LATE_M:
                if (StringUtils.isBlank(getCheckinTime())) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_in_time_first));
                    break;
                }
                mCurrentTimeSelected = CurrentTimeSelected.COMPENSATION_FROM;
                mDialogManager.showDatePickerDialog();
                break;
            case LeaveTypeId.LEAVE_EARLY_A:
            case LeaveTypeId.LEAVE_EARLY_M:
                if (StringUtils.isBlank(getCheckoutTime())) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_out_time_first));
                    break;
                }
                mCurrentTimeSelected = CurrentTimeSelected.COMPENSATION_FROM;
                mDialogManager.showDatePickerDialog();
                break;
            case LeaveTypeId.LEAVE_OUT:
                if (StringUtils.isBlank(getCheckinTime())) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_in_time_first));
                    break;
                }
                if (StringUtils.isBlank(getCheckoutTime())) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_out_time_first));
                    break;
                }
                mCurrentTimeSelected = CurrentTimeSelected.COMPENSATION_FROM;
                mDialogManager.showDatePickerDialog();
                break;
            default:
                break;
        }
    }

    public void onClickCompensationTo(View view) {
        showDialogError(
                mContext.getString(R.string.you_just_need_choose_the_compensation_from_time));
    }

    private void showDialogError(String message) {
        mDialogManager.dialogError(message);
    }

    private boolean checkIfOnlyHaveCheckoutTime() {
        return mCurrentLeaveType == (LeaveTypeId.LEAVE_EARLY_A)
                || mCurrentLeaveType == (LeaveTypeId.LEAVE_EARLY_M)
                || mCurrentLeaveType == (LeaveTypeId.LEAVE_EARLY_WOMAN_A)
                || mCurrentLeaveType == (LeaveTypeId.LEAVE_EARLY_WOMAN_M)
                || mCurrentLeaveType == (LeaveTypeId.FORGOT_CARD_OUT)
                || mCurrentLeaveType == (LeaveTypeId.FORGOT_TO_CHECK_OUT);
    }

    private void validateErrorDialog(String error) {
        mDialogManager.dialogError(error, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                mDialogManager.showDatePickerDialog();
            }
        });
    }

    private void validateCheckinTime(String checkinTime) {
        String currentTime =
                DateTimeUtils.convertToString(new Date(), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        switch (mCurrentLeaveType) {
            case LeaveTypeId.FORGOT_CARD_ALL_DAY:
                validateCheckinForgotCardAllDay(checkinTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_CARD_IN:
                validateForgotCardIn(checkinTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_TO_CHECK_IN:
                validateForgotCheckin(checkinTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_CHECK_ALL_DAY:
                validateCheckinForgotCheckAllDay(checkinTime, currentTime);
                break;
            case LeaveTypeId.IN_LATE_M:
                validateInLateM(checkinTime);
                break;
            case LeaveTypeId.IN_LATE_A:
                validateInLateA(checkinTime);
                break;
            case LeaveTypeId.IN_LATE_WOMAN_M:
                validateInLateWomanM(checkinTime);
                break;
            case LeaveTypeId.IN_LATE_WOMAN_A:
                validateInLateWomanA(checkinTime);
                break;
            case LeaveTypeId.LEAVE_OUT:
                validateCheckinLeaveOut(checkinTime);
                break;
            default:
                break;
        }
    }

    private void validateCheckinLeaveOut(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, mHourOfTimeIn, mMinuteOfTimeIn)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            setCheckinTime(checkinTime);
            setCheckoutTime("");
            setCompensationFromTime("");
            setCompensationToTime("");
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, mHourOfTimeOut, mMinuteOfTimeOut)) {
            setCheckinTime(checkinTime);
            setCheckoutTime("");
            setCompensationFromTime("");
            setCompensationToTime("");
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateInLateWomanA(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
            return;
        }
        String dateAfternoonAfterAddMinutes =
                DateTimeUtils.addMinutesToStringDate(mCurrentShifts.getTimeAfternoon(),
                        THIRDTY_MINUTES);
        int hour = DateTimeUtils.getHourOfDay(dateAfternoonAfterAddMinutes);
        int minute = DateTimeUtils.getMinute(dateAfternoonAfterAddMinutes);
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, hour, minute)) {
            validateErrorDialog(mContext.getString(
                    R.string.can_not_be_greater_than_0_5_hours_from_the_work_shift));
            return;
        }
        float maxDuration =
                mUser.getMaxDurationComeLate(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(checkinTime,
                mCurrentShifts.getTimeAfternoon(), maxDuration)) {
            setCheckinTime(checkinTime);
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeOut,
                mMinuteOfTimeOut)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateInLateWomanM(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, mHourOfTimeIn, mMinuteOfTimeIn)) {
            validateErrorDialog(mContext.getString(R.string.time_into_company_illegal));
            return;
        }
        String timeInAfterAddMinutes =
                DateTimeUtils.addMinutesToStringDate(mCurrentShifts.getTimeIn(), THIRDTY_MINUTES);
        int hour = DateTimeUtils.getHourOfDay(timeInAfterAddMinutes);
        int minute = DateTimeUtils.getMinute(timeInAfterAddMinutes);
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, hour, minute)) {
            validateErrorDialog(mContext.getString(
                    R.string.can_not_be_greater_than_0_5_hours_from_the_work_shift));
            return;
        }
        float maxDuration =
                mUser.getMaxDurationComeLate(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(checkinTime,
                mCurrentShifts.getTimeIn(), maxDuration)) {
            setCheckinTime(checkinTime);
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_morning_shift));
    }

    private void validateInLateA(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
            return;
        }
        float maxDuration =
                mUser.getMaxDurationComeLate(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(checkinTime,
                mCurrentShifts.getTimeAfternoon(), maxDuration)) {
            setCheckinTime(checkinTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkinTime, mHourOfTimeOut,
                    mMinuteOfTimeOut));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeOut,
                mMinuteOfTimeOut)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
    }

    private void validateInLateM(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeIn,
                mMinuteOfTimeIn)) {
            validateErrorDialog(
                    mContext.getString(R.string.this_is_form_request_late_time_in_is_incorrect));
            return;
        }
        float maxDuration =
                mUser.getMaxDurationComeLate(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(checkinTime,
                mCurrentShifts.getTimeIn(), maxDuration)) {
            setCheckinTime(checkinTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkinTime, mHourOfTimeOut,
                    mMinuteOfTimeOut));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_morning_shift));
    }

    private void validateCheckinForgotCheckAllDay(String checkinTime, String currentTime) {
        Date checkinTimeDate = DateTimeUtils.convertStringToDate(checkinTime);
        Date currentTimeDate = DateTimeUtils.convertStringToDate(currentTime);
        if (!checkinTimeDate.after(currentTimeDate) && DateTimeUtils.checkHourOfDateLessThanOrEqual(
                checkinTime, mHourOfTimeLunch, mMinuteOfTimeLunch)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateForgotCheckin(String checkinTime, String currentTime) {
        Date checkinTimeDate = DateTimeUtils.convertStringToDate(checkinTime);
        Date currentTimeDate = DateTimeUtils.convertStringToDate(currentTime);
        if (!checkinTimeDate.after(currentTimeDate)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.time_request_overtime_invalid));
    }

    private void validateForgotCardIn(String checkinTime, String currentTime) {
        String checkinTimeString = DateTimeUtils.convertDateTimeToDate(checkinTime);
        String currentTimeString = DateTimeUtils.convertDateTimeToDate(currentTime);
        if (checkinTimeString.equals(currentTimeString)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateCheckinForgotCardAllDay(String checkinTime, String currentTime) {
        String checkinTimeString = DateTimeUtils.convertDateTimeToDate(checkinTime);
        String currentTimeString = DateTimeUtils.convertDateTimeToDate(currentTime);
        if (!checkinTimeString.equals(currentTimeString)) {
            validateErrorDialog(mContext.getString(R.string.form_overdue));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateCheckoutTime(String checkoutTime) {
        String currentTime =
                DateTimeUtils.convertToString(new Date(), DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        switch (mCurrentLeaveType) {
            case LeaveTypeId.FORGOT_CARD_ALL_DAY:
                validateCheckoutForgotCardAllDay(checkoutTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_CARD_OUT:
                validateForgotCardOut(checkoutTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_TO_CHECK_OUT:
                validateForgotCheckout(checkoutTime, currentTime);
                break;
            case LeaveTypeId.FORGOT_CHECK_ALL_DAY:
                validateCheckoutForgotCheckAllDay(checkoutTime, currentTime);
                break;
            case LeaveTypeId.LEAVE_EARLY_M:
                validateLeaveEarlyM(checkoutTime);
                break;
            case LeaveTypeId.LEAVE_EARLY_A:
                validateLeaveEarlyA(checkoutTime);
                break;
            case LeaveTypeId.LEAVE_EARLY_WOMAN_M:
                validateLeaveEarlyWomanM(checkoutTime);
                break;
            case LeaveTypeId.LEAVE_EARLY_WOMAN_A:
                validateLeaveEarlyWomanA(checkoutTime);
                break;
            case LeaveTypeId.LEAVE_OUT:
                validateCheckoutLeaveOut(checkoutTime);
                break;
            default:
                break;
        }
    }

    private void validateCheckoutLeaveOut(String checkoutTime) {
        Date checkinTimeDate = DateTimeUtils.convertStringToDateTime(mCheckinTime);
        Date checkoutTimeDate = DateTimeUtils.convertStringToDateTime(checkoutTime);
        String checkinTimeString = DateTimeUtils.convertDateTimeToDate(mCheckinTime);
        String checkoutTimeString = DateTimeUtils.convertDateTimeToDate(checkoutTime);
        if (checkoutTimeDate.before(checkinTimeDate) || checkoutTimeDate.equals(checkinTimeDate)) {
            validateErrorDialog(mContext.getString(
                    R.string.request_time_to_can_not_greater_than_request_time_from));
            return;
        }
        if (!checkoutTimeString.equals(checkinTimeString)) {
            validateErrorDialog(mContext.getString(R.string.time_must_be_is_in_a_day));
            return;
        }
        float maxDuration =
                mUser.getMaxDurationLeave(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (!DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(checkoutTime, mCheckinTime,
                maxDuration)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, mHourOfTimeOut,
                mMinuteOfTimeOut)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, mHourOfTimeOut,
                    mMinuteOfTimeOut));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateLeaveEarlyWomanA(String checkoutTime) {
        if (validateLeaveEarlyABase(checkoutTime)) {
            return;
        }
        float maxDuration =
                mUser.getMaxDurationLeave(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (!DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(mCurrentShifts.getTimeOut(),
                checkoutTime, maxDuration)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeOut, mMinuteOfTimeOut)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private boolean validateLeaveEarlyABase(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeIn, mMinuteOfTimeIn)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return true;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_out_time_must_be_in_afternoon_shift));
            return true;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return true;
        }
        return false;
    }

    private void validateLeaveEarlyWomanM(String checkoutTime) {
        if (validateLeaveEarlyMBaseOne(checkoutTime)) {
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateLeaveEarlyMBaseTwo(checkoutTime);
    }

    private boolean validateLeaveEarlyMBaseOne(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeIn, mMinuteOfTimeIn)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return true;
        }
        float maxDuration =
                mUser.getMaxDurationLeave(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (!DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(mCurrentShifts.getTimeLunch(),
                checkoutTime, maxDuration)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return true;
        }
        return false;
    }

    private void validateLeaveEarlyA(String checkoutTime) {
        if (validateLeaveEarlyABase(checkoutTime)) {
            return;
        }
        float maxDuration =
                mUser.getMaxDurationLeave(mCurrentLeaveTypePosition, mCurrentBranchPosition);
        if (!DateTimeUtils.checkHourOfDateLessThanOrEqualWithDuration(mCurrentShifts.getTimeOut(),
                checkoutTime, maxDuration)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeOut, mMinuteOfTimeOut)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, mHourOfTimeOut,
                    mMinuteOfTimeOut));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateLeaveEarlyM(String checkoutTime) {
        if (validateLeaveEarlyMBaseOne(checkoutTime)) {
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, mHourOfTimeOut,
                    mMinuteOfTimeOut));
            return;
        }
        validateLeaveEarlyMBaseTwo(checkoutTime);
    }

    private void validateLeaveEarlyMBaseTwo(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, mHourOfTimeOut,
                mMinuteOfTimeOut)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_out_time_must_be_in_morning_shift));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateCheckoutForgotCheckAllDay(String checkoutTime, String currentTime) {
        Date currentTimeDate = DateTimeUtils.convertStringToDate(currentTime);
        Date checkoutTimeDate = DateTimeUtils.convertStringToDate(checkoutTime);
        if (!checkoutTimeDate.after(currentTimeDate) && !DateTimeUtils.checkHourOfDateLessThan(
                checkoutTime, mHourOfTimeAfternoon, mMinuteOfTimeAfternoon)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateForgotCheckout(String checkoutTime, String currentTime) {
        Date currentTimeDate = DateTimeUtils.convertStringToDate(currentTime);
        Date checkoutTimeDate = DateTimeUtils.convertStringToDate(checkoutTime);
        if (!checkoutTimeDate.after(currentTimeDate)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateForgotCardOut(String checkoutTime, String currentTime) {
        String currentTimeString = DateTimeUtils.convertDateTimeToDate(currentTime);
        String checkoutTimeString = DateTimeUtils.convertDateTimeToDate(checkoutTime);
        if (checkoutTimeString.equals(currentTimeString)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateCheckoutForgotCardAllDay(String checkoutTime, String currentTime) {
        String checkoutTimeString = DateTimeUtils.convertDateTimeToDate(checkoutTime);
        String checkinTimeString = DateTimeUtils.convertDateTimeToDate(mCheckinTime);
        if (!checkinTimeString.equals(checkoutTimeString)) {
            validateErrorDialog(mContext.getString(R.string.time_must_be_is_in_a_day));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
            return;
        }
        setCheckoutTime(checkoutTime);
    }

    private void validateCompensationFromTime(String compensationFromTime) {
        switch (mCurrentLeaveType) {
            case LeaveTypeId.IN_LATE_A:
            case LeaveTypeId.IN_LATE_M:
            case LeaveTypeId.LEAVE_EARLY_A:
            case LeaveTypeId.LEAVE_EARLY_M:
            case LeaveTypeId.LEAVE_OUT:
                validateCompensationTime(compensationFromTime);
                break;
            default:
                break;
        }
    }

    private void validateCompensationTime(String compensationFromTime) {
        Date compensationFromTimeDate = DateTimeUtils.convertStringToDate(compensationFromTime);
        Date checkinTimeDate = DateTimeUtils.convertStringToDate(getCheckinTime());
        Date checkoutTimeDate = DateTimeUtils.convertStringToDate(getCheckoutTime());
        if (StringUtils.isNotBlank(mCheckinTime)) {
            if (compensationFromTimeDate.before(checkinTimeDate)) {
                validateErrorDialog(
                        mContext.getString(R.string.compensation_time_is_not_in_the_past));
                return;
            }
        } else {
            if (compensationFromTimeDate.before(checkoutTimeDate)) {
                validateErrorDialog(
                        mContext.getString(R.string.compensation_time_is_not_in_the_past));
                return;
            }
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, mHourOfTimeLunch,
                mMinuteOfTimeLunch)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_compensation_time_can_not_in_working_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, mHourOfTimeAfternoon,
                mMinuteOfTimeAfternoon)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, mHourOfTimeOut,
                mMinuteOfTimeOut)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_compensation_time_can_not_in_working_time));
            return;
        }
        setCompensationTime(compensationFromTime);
    }

    private void setCompensationTime(String compensationFromTime) {
        setCompensationFromTime(compensationFromTime);
        String workingTime;
        int minutesBetweenTwoDate;
        switch (mCurrentLeaveType) {
            case LeaveTypeId.IN_LATE_M:
                workingTime = DateTimeUtils.changeTimeOfDateString(getCheckinTime(), mHourOfTimeIn,
                        mMinuteOfTimeIn);
                minutesBetweenTwoDate =
                        DateTimeUtils.getMinutesBetweenTwoDate(getCheckinTime(), workingTime,
                                mCurrentLeaveTypeId, mLeaveTypes);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        minutesBetweenTwoDate));
                break;
            case LeaveTypeId.IN_LATE_A:
                workingTime =
                        DateTimeUtils.changeTimeOfDateString(getCheckinTime(), mHourOfTimeAfternoon,
                                mMinuteOfTimeAfternoon);
                minutesBetweenTwoDate =
                        DateTimeUtils.getMinutesBetweenTwoDate(getCheckinTime(), workingTime,
                                mCurrentLeaveTypeId, mLeaveTypes);
                DateTimeUtils.getMinutesBetweenTwoDate(getCheckinTime(), workingTime,
                        mCurrentLeaveTypeId, mLeaveTypes);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        minutesBetweenTwoDate));
                break;
            case LeaveTypeId.LEAVE_EARLY_M:
                workingTime =
                        DateTimeUtils.changeTimeOfDateString(getCheckoutTime(), mHourOfTimeLunch,
                                mMinuteOfTimeLunch);
                minutesBetweenTwoDate =
                        DateTimeUtils.getMinutesBetweenTwoDate(workingTime, getCheckoutTime(),
                                mCurrentLeaveTypeId, mLeaveTypes);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        minutesBetweenTwoDate));
                break;
            case LeaveTypeId.LEAVE_EARLY_A:
                workingTime =
                        DateTimeUtils.changeTimeOfDateString(getCheckoutTime(), mHourOfTimeOut,
                                mMinuteOfTimeOut);
                minutesBetweenTwoDate =
                        DateTimeUtils.getMinutesBetweenTwoDate(workingTime, getCheckoutTime(),
                                mCurrentLeaveTypeId, mLeaveTypes);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        minutesBetweenTwoDate));
                break;
            case LeaveTypeId.LEAVE_OUT:
                minutesBetweenTwoDate =
                        DateTimeUtils.getMinutesBetweenTwoDate(getCheckoutTime(), getCheckinTime(),
                                mCurrentLeaveTypeId, mLeaveTypes);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        minutesBetweenTwoDate));
                break;
            default:
                break;
        }
    }

    public void onClickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    public void onClickCreate(View view) {
        if (!mPresenter.validateDataInput(mRequest)) {
            return;
        }
        if (mActionType == ActionType.ACTION_CREATE
                || mActionType == ActionType.ACTION_CONFIRM_CREATE) {
            mActionType = ActionType.ACTION_CONFIRM_CREATE;
        } else {
            mActionType = ActionType.ACTION_CONFIRM_EDIT;
        }
        if (StringUtils.isBlank(mRequest.getWorkspaceName())) {
            mDialogManager.dialogError(mContext.getString(R.string.branch_is_emty));
            return;
        }
        if (StringUtils.isBlank(mRequest.getCompanyName())) {
            mDialogManager.dialogError(mContext.getString(R.string.group_is_emty));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, mRequest);
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, mActionType);
        mNavigator.startActivityForResult(ConfirmRequestLeaveActivity.class, bundle,
                Constant.RequestCode.REQUEST_LEAVE);
    }

    private void clearTime() {
        setCheckinTime(null);
        setCheckoutTime(null);
        setCompensationFromTime(null);
        setCompensationToTime(null);
    }

    @Bindable
    public String getCurrentBranch() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mCurrentBranch;
        }
        if (mRequest.getWorkspaceName() == null) {
            return mRequest.getBranch().getBranchName();
        }
        return mRequest.getWorkspaceName();
    }

    @Bindable
    public String getCurrentGroup() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mCurrentGroup;
        }
        if (mRequest.getCompanyName() == null) {
            return "";
        }
        return mRequest.getCompanyName();
    }

    @Bindable
    public String getCurrentLeaveType() {
        return mCurrentLeaveTypeName;
    }

    private void setCurrentLeaveType() {
        mCurrentLeaveTypeId = mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getId();
        mCurrentLeaveType = mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getId();
        mRequest.setLeaveTypeId(mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getId());
        mRequest.setLeaveType(mUser.getLeaveTypes().get(mCurrentLeaveTypePosition));
        notifyPropertyChanged(BR.currentLeaveType);
    }

    private void setCurrentBranch() {
        if (mActionType == ActionType.ACTION_CREATE
                || mActionType == ActionType.ACTION_CONFIRM_CREATE) {
            mCurrentBranch = mUser.getBranches().get(mCurrentBranchPosition).getBranchName();
            mRequest.setWorkpaceId(mUser.getBranches().get(mCurrentBranchPosition).getBranchId());
        }
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT
                || mActionType == ActionType.ACTION_EDIT) {
            mCurrentBranch = mRequest.getBranch().getBranchName();
            mRequest.setWorkpaceId(mRequest.getBranch().getBranchId());
        }
        mRequest.setWorkspaceName(mCurrentBranch);
        notifyPropertyChanged(BR.currentBranch);
    }

    private void setCurrentGroup() {
        mCurrentGroup = mUser.getGroups().get(mCurrentGroupPosition).getFullName();
        mRequest.setGroupId(mUser.getGroups().get(mCurrentGroupPosition).getGroupId());
        mRequest.setCompanyName(mCurrentGroup);
        notifyPropertyChanged(BR.currentGroup);
    }

    @Bindable
    public boolean isVisibleGroup() {
        return mIsVisibleGroup;
    }

    private void setVisibleGroup(boolean visibleGroup) {
        mIsVisibleGroup = visibleGroup;
        notifyPropertyChanged(BR.visibleGroup);
    }

    @Bindable
    public boolean isVisibleLayoutCompensation() {
        return mIsVisibleLayoutCompensation;
    }

    @Override
    public void setVisibleLayoutCompensation(boolean visibleLayoutCompensation) {
        mIsVisibleLayoutCompensation = visibleLayoutCompensation;
        notifyPropertyChanged(BR.visibleLayoutCompensation);
    }

    @Bindable
    public boolean isVisibleLayoutCheckin() {
        return mIsVisibleLayoutCheckin;
    }

    public void setVisibleLayoutCheckin(boolean visibleLayoutCheckin) {
        mIsVisibleLayoutCheckin = visibleLayoutCheckin;
        notifyPropertyChanged(BR.visibleLayoutCheckin);
    }

    @Bindable
    public boolean isVisibleLayoutCheckout() {
        return mIsVisibleLayoutCheckout;
    }

    public void setVisibleLayoutCheckout(boolean visibleLayoutCheckout) {
        mIsVisibleLayoutCheckout = visibleLayoutCheckout;
        notifyPropertyChanged(BR.visibleLayoutCheckout);
    }

    @Bindable
    public String getTitleLeaveInType() {
        return mTitleLeaveInType;
    }

    private void setTitleExampleLeaveIn(String titleLeaveType, String exampleLeaveType) {
        mTitleLeaveInType = titleLeaveType;
        notifyPropertyChanged(BR.titleLeaveInType);
        mExampleLeaveInType = exampleLeaveType;
        notifyPropertyChanged(BR.exampleLeaveInType);
    }

    private void setTitleExampleLeaveOut(String titleLeaveType, String exampleLeaveType) {
        mTitleLeaveOutType = titleLeaveType;
        notifyPropertyChanged(BR.titleLeaveOutType);
        mExampleLeaveOutType = exampleLeaveType;
        notifyPropertyChanged(BR.exampleLeaveOutType);
    }

    @Bindable
    public String getExampleLeaveInType() {
        return mExampleLeaveInType;
    }

    @Bindable
    public String getTitleLeaveOutType() {
        return mTitleLeaveOutType;
    }

    @Bindable
    public String getExampleLeaveOutType() {
        return mExampleLeaveOutType;
    }

    @Bindable
    public String getProjectNameError() {
        return mProjectNameError;
    }

    @Bindable
    public String getReasonError() {
        return mReasonError;
    }

    @Bindable
    public String getCheckinTimeError() {
        return mCheckinTimeError;
    }

    @Bindable
    public String getCheckoutTimeError() {
        return mCheckoutTimeError;
    }

    @Bindable
    public String getCompensationFromTimeError() {
        return mCompensationFromTimeError;
    }

    @Bindable
    public String getCompensationToTimeError() {
        return mCompensationToTimeError;
    }

    @Bindable
    public String getProjectName() {
        return mRequest.getProjectName();
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
        mRequest.setProjectName(projectName);
        notifyPropertyChanged(BR.projectName);
    }

    @Bindable
    public String getReason() {
        return mRequest.getReason();
    }

    public void setReason(String reason) {
        mReason = reason;
        mRequest.setReason(reason);
        notifyPropertyChanged(BR.reason);
    }

    @Bindable
    public String getCheckinTime() {
        if (mActionType == ActionType.ACTION_EDIT) {
            mActionType = ActionType.ACTION_CONFIRM_EDIT;
            return DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckInTime(),
                    INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        }
        if (StringUtils.isBlank(mRequest.getCheckInTime())) {
            return "";
        }
        return mRequest.getCheckInTime();
    }

    public void setCheckinTime(String checkinTime) {
        mCurrentTimeSelected = CurrentTimeSelected.NONE;
        mCheckinTime = checkinTime;
        mRequest.setCheckInTime(checkinTime);
        notifyPropertyChanged(BR.checkinTime);
    }

    @Bindable
    public String getCheckoutTime() {
        if (DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(), INPUT_TIME_FORMAT,
                DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM) != null) {
            return DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCheckOutTime(),
                    INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        }

        return mRequest.getCheckOutTime();
    }

    public void setCheckoutTime(String checkoutTime) {
        mCurrentTimeSelected = CurrentTimeSelected.NONE;
        mCheckoutTime = checkoutTime;
        mRequest.setCheckOutTime(checkoutTime);
        notifyPropertyChanged(BR.checkoutTime);
    }

    @Bindable
    public String getCompensationFromTime() {
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            if (mRequest.getCompensationRequest().getFromTime() != null) {
                return mRequest.getCompensationRequest().getFromTime();
            } else {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mRequest.getCompensation().getFromTime(), INPUT_TIME_FORMAT,
                        DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
            }
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mRequest.getCompensationRequest().getFromTime();
        }
        return mRequest.getCompensationRequest().getFromTime();
    }

    public void setCompensationFromTime(String compensationFromTime) {
        mCompensationFromTime = compensationFromTime;
        mRequest.getCompensationRequest().setFromTime(compensationFromTime);
        notifyPropertyChanged(BR.compensationFromTime);
    }

    @Bindable
    public String getCompensationToTime() {
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            if (DateTimeUtils.convertUiFormatToDataFormat(mRequest.getCompensation().getToTime(),
                    INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM) != null) {
                return DateTimeUtils.convertUiFormatToDataFormat(
                        mRequest.getCompensation().getToTime(), INPUT_TIME_FORMAT,
                        DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
            } else {
                return mRequest.getCompensation().getToTime();
            }
        }
        if (mActionType == ActionType.ACTION_DETAIL) {
            return mRequest.getCompensationRequest().getToTime();
        }
        return mRequest.getCompensationRequest().getToTime();
    }

    public void setCompensationToTime(String compensationToTime) {
        mCompensationToTime = compensationToTime;
        mRequest.getCompensationRequest().setToTime(compensationToTime);
        if (mActionType == ActionType.ACTION_CONFIRM_EDIT) {
            mRequest.getCompensation().setToTime(compensationToTime);
        }
        notifyPropertyChanged(BR.compensationToTime);
    }

    private void initShifts() {
        if (mUser.getBranches() == null) {
            return;
        }
        for (Branch branch : mUser.getBranches()) {
            Shifts shifts = branch.getShifts().get(0);
            shifts.setTimeIn(
                    DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeIn(), INPUT_TIME_FORMAT,
                            DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
            shifts.setTimeOut(DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeOut(),
                    INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
            shifts.setTimeLunch(DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeLunch(),
                    INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
            shifts.setTimeAfternoon(
                    DateTimeUtils.convertUiFormatToDataFormat(shifts.getTimeAfternoon(),
                            INPUT_TIME_FORMAT, DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM));
            if (mUser.getSpecial() == UserType.CHILDREN) {
                shifts.setTimeIn(
                        DateTimeUtils.addMinutesToStringDate(shifts.getTimeIn(), FIFTEEN_MINUTES));
                shifts.setTimeOut(
                        DateTimeUtils.addMinutesToStringDate(shifts.getTimeOut(), FIFTEEN_MINUTES));
            }
        }
    }

    private void setCurrentShifts(Shifts shifts) {
        mCurrentShifts = shifts;
        mHourOfTimeIn = DateTimeUtils.getHourOfDay(mCurrentShifts.getTimeIn());
        mMinuteOfTimeIn = DateTimeUtils.getMinute(mCurrentShifts.getTimeIn());
        mHourOfTimeOut = DateTimeUtils.getHourOfDay(mCurrentShifts.getTimeOut());
        mMinuteOfTimeOut = DateTimeUtils.getMinute(mCurrentShifts.getTimeOut());
        mHourOfTimeAfternoon = DateTimeUtils.getHourOfDay(mCurrentShifts.getTimeAfternoon());
        mMinuteOfTimeAfternoon = DateTimeUtils.getMinute(mCurrentShifts.getTimeAfternoon());
        mHourOfTimeLunch = DateTimeUtils.getHourOfDay(mCurrentShifts.getTimeLunch());
        mMinuteOfTimeLunch = DateTimeUtils.getMinute(mCurrentShifts.getTimeLunch());
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @IntDef({
            CurrentTimeSelected.NONE, CurrentTimeSelected.CHECK_IN, CurrentTimeSelected.CHECK_OUT,
            CurrentTimeSelected.COMPENSATION_FROM, CurrentTimeSelected.COMPENSATION_TO
    })
    @interface CurrentTimeSelected {
        int NONE = 0;
        int CHECK_IN = 1;
        int CHECK_OUT = 2;
        int COMPENSATION_FROM = 3;
        int COMPENSATION_TO = 4;
    }

    @IntDef({
            LeaveTypeId.IN_LATE_M, LeaveTypeId.IN_LATE_A, LeaveTypeId.LEAVE_EARLY_M,
            LeaveTypeId.LEAVE_EARLY_A, LeaveTypeId.LEAVE_OUT, LeaveTypeId.FORGOT_CHECK_ALL_DAY,
            LeaveTypeId.FORGOT_TO_CHECK_IN, LeaveTypeId.FORGOT_TO_CHECK_OUT,
            LeaveTypeId.FORGOT_CARD_ALL_DAY, LeaveTypeId.FORGOT_CARD_IN,
            LeaveTypeId.FORGOT_CARD_OUT, LeaveTypeId.IN_LATE_WOMAN_M, LeaveTypeId.IN_LATE_WOMAN_A,
            LeaveTypeId.LEAVE_EARLY_WOMAN_M, LeaveTypeId.LEAVE_EARLY_WOMAN_A
    })

    public @interface LeaveTypeId {
        int IN_LATE_M = 1;
        int IN_LATE_A = 4;
        int LEAVE_EARLY_M = 2;
        int LEAVE_EARLY_A = 14;
        int LEAVE_OUT = 3;
        int FORGOT_CHECK_ALL_DAY = 7;
        int FORGOT_TO_CHECK_IN = 12;
        int FORGOT_TO_CHECK_OUT = 13;
        int FORGOT_CARD_ALL_DAY = 21;
        int FORGOT_CARD_IN = 19;
        int FORGOT_CARD_OUT = 20;
        int IN_LATE_WOMAN_M = 5;
        int IN_LATE_WOMAN_A = 15;
        int LEAVE_EARLY_WOMAN_M = 16;
        int LEAVE_EARLY_WOMAN_A = 17;
    }
}
