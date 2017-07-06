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
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRequestLeave;
import com.framgia.wsm.screen.confirmrequestleave.ConfirmRequestLeaveActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
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

import static com.framgia.wsm.utils.Constant.TimeConst.EIGHT_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.ELEVEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.FIFTEEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.FIFTEEN_MINUTES;
import static com.framgia.wsm.utils.Constant.TimeConst.FORTY_FIVE_MINUTES;
import static com.framgia.wsm.utils.Constant.TimeConst.FOURTEEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.NIGHT_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.SEVEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.SIXTEEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.TEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.THIRTEEN_HOUR;
import static com.framgia.wsm.utils.Constant.TimeConst.TWELVE_HOUR;
import static com.framgia.wsm.utils.common.DateTimeUtils.convertDateTimeToString;

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
        if (mActionType == ActionType.ACTION_CREATE) {
            mCurrentLeaveTypeName = "In late (M)";
        } else {
            mCurrentLeaveTypeName = mRequest.getLeaveType().getName();
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
                }
            }
        } else {
            mRequest = new LeaveRequest();
            mRequest.setCompensationRequest(new LeaveRequest.Compensation());
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
        setCurrentLeaveType();
        setCurrentBranch();
        setCurrentGroup();
        setTitleExampleLeave();
        mRequest.setCompanyId(mUser.getCompany().getId());
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
        mCurrentDate = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        mDialogManager.showTimePickerDialog();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String currentDateTime = convertDateTimeToString(mCurrentDate, hourOfDay, minute);
        switch (mCurrentLeaveType) {
            case LeaveType.FORGOT_CARD_IN:
            case LeaveType.FORGOT_TO_CHECK_IN:
            case LeaveType.IN_LATE_WOMAN_M:
            case LeaveType.IN_LATE_WOMAN_A:
                validateCheckinTime(currentDateTime);
                break;
            case LeaveType.FORGOT_CARD_OUT:
            case LeaveType.FORGOT_TO_CHECK_OUT:
            case LeaveType.LEAVE_EARLY_WOMAN_A:
            case LeaveType.LEAVE_EARLY_WOMAN_M:
                validateCheckoutTime(currentDateTime);
                break;
            case LeaveType.IN_LATE_A:
            case LeaveType.IN_LATE_M:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_IN) {
                    validateCheckinTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.COMPENSATION_FROM) {
                    validateCompensationFromTime(currentDateTime);
                }
                break;
            case LeaveType.LEAVE_EARLY_A:
            case LeaveType.LEAVE_EARLY_M:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_OUT) {
                    validateCheckoutTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.COMPENSATION_FROM) {
                    validateCompensationFromTime(currentDateTime);
                }
                break;
            case LeaveType.FORGOT_CARD_ALL_DAY:
            case LeaveType.FORGOT_CHECK_ALL_DAY:
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_IN) {
                    validateCheckinTime(currentDateTime);
                    break;
                }
                if (mCurrentTimeSelected == CurrentTimeSelected.CHECK_OUT) {
                    validateCheckoutTime(currentDateTime);
                }
                break;
            case LeaveType.LEAVE_OUT:
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
            case LeaveType.IN_LATE_WOMAN_M:
            case LeaveType.IN_LATE_M:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_m),
                        mContext.getString(R.string.exmple_in_late_m));
                break;
            case LeaveType.IN_LATE_WOMAN_A:
            case LeaveType.IN_LATE_A:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_in_late_a),
                        mContext.getString(R.string.exmple_in_late_a));
                break;
            case LeaveType.LEAVE_EARLY_WOMAN_M:
            case LeaveType.LEAVE_EARLY_M:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_m),
                        mContext.getString(R.string.exmple_leave_early_m));
                break;
            case LeaveType.LEAVE_EARLY_WOMAN_A:
            case LeaveType.LEAVE_EARLY_A:
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_early_a),
                        mContext.getString(R.string.exmple_leave_early_a));
                break;
            case LeaveType.LEAVE_OUT:
            case LeaveType.FORGOT_CHECK_ALL_DAY:
            case LeaveType.FORGOT_CARD_ALL_DAY:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_leave_out_from),
                        mContext.getString(R.string.exmple_leave_out_from));
                setTitleExampleLeaveOut(mContext.getString(R.string.title_leave_out_to),
                        mContext.getString(R.string.exmple_leave_out_to));
                break;
            case LeaveType.FORGOT_CARD_IN:
            case LeaveType.FORGOT_TO_CHECK_IN:
                setTitleExampleLeaveIn(mContext.getString(R.string.title_forgot_in),
                        mContext.getString(R.string.exmple_forgot_in));
                break;
            case LeaveType.FORGOT_CARD_OUT:
            case LeaveType.FORGOT_TO_CHECK_OUT:
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

    public void onPickTypeRequest(View view) {
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
        mCurrentTimeSelected = CurrentTimeSelected.CHECK_IN;
        mDialogManager.showDatePickerDialog();
    }

    public void onClickCheckoutTime(View view) {
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
    }

    public void onClickCompensationFrom(View view) {
        switch (mCurrentLeaveType) {
            case LeaveType.IN_LATE_A:
            case LeaveType.IN_LATE_M:
                if (StringUtils.isBlank(mCheckinTime)) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_in_time_first));
                    break;
                }
                mCurrentTimeSelected = CurrentTimeSelected.COMPENSATION_FROM;
                mDialogManager.showDatePickerDialog();
                break;
            case LeaveType.LEAVE_EARLY_A:
            case LeaveType.LEAVE_EARLY_M:
                if (StringUtils.isBlank(mCheckoutTime)) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_out_time_first));
                    break;
                }
                mCurrentTimeSelected = CurrentTimeSelected.COMPENSATION_FROM;
                mDialogManager.showDatePickerDialog();
                break;
            case LeaveType.LEAVE_OUT:
                if (StringUtils.isBlank(mCheckinTime)) {
                    showDialogError(mContext.getString(
                            R.string.you_have_to_choose_the_check_in_time_first));
                    break;
                }
                if (StringUtils.isBlank(mCheckoutTime)) {
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
        return mCurrentLeaveType == (LeaveType.LEAVE_EARLY_A)
                || mCurrentLeaveType == (LeaveType.LEAVE_EARLY_M)
                || mCurrentLeaveType == (LeaveType.LEAVE_EARLY_WOMAN_A)
                || mCurrentLeaveType == (LeaveType.LEAVE_EARLY_WOMAN_M)
                || mCurrentLeaveType == (LeaveType.FORGOT_CARD_OUT)
                || mCurrentLeaveType == (LeaveType.FORGOT_TO_CHECK_OUT);
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
        String currentTime = DateTimeUtils.convertToString(new Date(),
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        switch (mCurrentLeaveType) {
            case LeaveType.FORGOT_CARD_ALL_DAY:
                validateCheckinForgotCardAllDay(checkinTime, currentTime);
                break;
            case LeaveType.FORGOT_CARD_IN:
                validateForgotCardIn(checkinTime, currentTime);
                break;
            case LeaveType.FORGOT_TO_CHECK_IN:
                validateForgotCheckin(checkinTime, currentTime);
                break;
            case LeaveType.FORGOT_CHECK_ALL_DAY:
                validateCheckinForgotCheckAllDay(checkinTime, currentTime);
                break;
            case LeaveType.IN_LATE_M:
                validateInLateM(checkinTime);
                break;
            case LeaveType.IN_LATE_A:
                validateInLateA(checkinTime);
                break;
            case LeaveType.IN_LATE_WOMAN_M:
                validateInLateWomanM(checkinTime);
                break;
            case LeaveType.IN_LATE_WOMAN_A:
                validateInLateWomanA(checkinTime);
                break;
            case LeaveType.LEAVE_OUT:
                validateCheckinLeaveOut(checkinTime);
                break;
            default:
                break;
        }
    }

    private void validateCheckinLeaveOut(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, SEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, ELEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, TWELVE_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, SIXTEEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateInLateWomanA(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, TWELVE_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, THIRTEEN_HOUR, FIFTEEN_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.can_not_be_greater_than_0_5_hours_from_the_work_shift));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, FIFTEEN_HOUR,
                FIFTEEN_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, SEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.time_into_company_illegal));
    }

    private void validateInLateWomanM(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, SEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(R.string.time_into_company_illegal));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, EIGHT_HOUR, FIFTEEN_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.can_not_be_greater_than_0_5_hours_from_the_work_shift));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, TEN_HOUR, FIFTEEN_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, ELEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_morning_shift));
    }

    private void validateInLateA(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, TWELVE_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, FOURTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkinTime, SIXTEEN_HOUR,
                    FORTY_FIVE_MINUTES));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, SIXTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_afternoon_shift));
    }

    private void validateInLateM(String checkinTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkinTime, SEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.this_is_form_request_late_time_in_is_incorrect));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, NIGHT_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkinTime, SIXTEEN_HOUR,
                    FORTY_FIVE_MINUTES));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, ELEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        validateErrorDialog(mContext.getString(R.string.check_in_time_must_be_in_morning_shift));
    }

    private void validateCheckinForgotCheckAllDay(String checkinTime, String currentTime) {
        if (!DateTimeUtils.convertStringToDate(checkinTime)
                .after(DateTimeUtils.convertStringToDate(currentTime))
                && DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, ELEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateForgotCheckin(String checkinTime, String currentTime) {
        if (!DateTimeUtils.convertStringToDate(checkinTime)
                .after(DateTimeUtils.convertStringToDate(currentTime))) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.time_request_overtime_invalid));
    }

    private void validateForgotCardIn(String checkinTime, String currentTime) {
        if (DateTimeUtils.convertDateTimeToDate(checkinTime)
                .equals(DateTimeUtils.convertDateTimeToDate(currentTime))) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateCheckinForgotCardAllDay(String checkinTime, String currentTime) {
        if (!DateTimeUtils.convertDateTimeToDate(checkinTime)
                .equals(DateTimeUtils.convertDateTimeToDate(currentTime))) {
            validateErrorDialog(mContext.getString(R.string.form_overdue));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkinTime, ELEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckinTime(checkinTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateCheckoutTime(String checkoutTime) {
        String currentTime = DateTimeUtils.convertToString(new Date(),
                DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM);
        switch (mCurrentLeaveType) {
            case LeaveType.FORGOT_CARD_ALL_DAY:
                validateCheckoutForgotCardAllDay(checkoutTime, currentTime);
                break;
            case LeaveType.FORGOT_CARD_OUT:
                validateForgotCardOut(checkoutTime, currentTime);
                break;
            case LeaveType.FORGOT_TO_CHECK_OUT:
                validateForgotCheckout(checkoutTime, currentTime);
                break;
            case LeaveType.FORGOT_CHECK_ALL_DAY:
                validateCheckoutForgotCheckAllDay(checkoutTime, currentTime);
                break;
            case LeaveType.LEAVE_EARLY_M:
                validateLeaveEarlyM(checkoutTime);
                break;
            case LeaveType.LEAVE_EARLY_A:
                validateLeaveEarlyA(checkoutTime);
                break;
            case LeaveType.LEAVE_EARLY_WOMAN_M:
                validateLeaveEarlyWomanM(checkoutTime);
                break;
            case LeaveType.LEAVE_EARLY_WOMAN_A:
                validateLeaveEarlyWomanA(checkoutTime);
                break;
            case LeaveType.LEAVE_OUT:
                validateCheckoutLeaveOut(checkoutTime);
                break;
            default:
                break;
        }
    }

    private void validateCheckoutLeaveOut(String checkoutTime) {
        if (DateTimeUtils.convertStringToDateTime(checkoutTime)
                .before(DateTimeUtils.convertStringToDateTime(mCheckinTime))) {
            validateErrorDialog(mContext.getString(
                    R.string.request_time_to_can_not_greater_than_request_time_from));
            return;
        }
        if (!DateTimeUtils.convertDateTimeToDate(checkoutTime)
                .equals(DateTimeUtils.convertDateTimeToDate(mCheckinTime))) {
            validateErrorDialog(mContext.getString(R.string.time_must_be_is_in_a_day));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, SIXTEEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, SIXTEEN_HOUR,
                    FORTY_FIVE_MINUTES));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateLeaveEarlyWomanA(String checkoutTime) {
        if (validateLeaveEarlyABase(checkoutTime)) {
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, FOURTEEN_HOUR,
                FIFTEEN_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }

        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, SIXTEEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
        } else {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
        }
    }

    private boolean validateLeaveEarlyABase(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, SEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return true;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, ELEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_out_time_must_be_in_afternoon_shift));
            return true;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, TWELVE_HOUR, FORTY_FIVE_MINUTES)) {
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
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, ELEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateLeaveEarlyMBaseTwo(checkoutTime);
    }

    private boolean validateLeaveEarlyMBaseOne(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, SEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_time_can_not_be_sooner_than_time_in_of_company));
            return true;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, NIGHT_HOUR, FIFTEEN_MINUTES)) {
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
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, FOURTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(mContext.getString(
                    R.string.your_amount_tim_can_not_greater_than_max_allow_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, SIXTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, SIXTEEN_HOUR,
                    FORTY_FIVE_MINUTES));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateLeaveEarlyM(String checkoutTime) {
        if (validateLeaveEarlyMBaseOne(checkoutTime)) {
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, ELEVEN_HOUR, FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
            setCompensationTime(DateTimeUtils.changeTimeOfDateString(checkoutTime, SIXTEEN_HOUR,
                    FORTY_FIVE_MINUTES));
            return;
        }
        validateLeaveEarlyMBaseTwo(checkoutTime);
    }

    private void validateLeaveEarlyMBaseTwo(String checkoutTime) {
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, TWELVE_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThanOrEqual(checkoutTime, SIXTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.check_out_time_must_be_in_morning_shift));
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.your_time_can_not_be_later_than_time_out_company));
    }

    private void validateCheckoutForgotCheckAllDay(String checkoutTime, String currentTime) {
        if (!DateTimeUtils.convertStringToDate(checkoutTime)
                .after(DateTimeUtils.convertStringToDate(currentTime))
                && !DateTimeUtils.checkHourOfDateLessThan(checkoutTime, TWELVE_HOUR,
                FORTY_FIVE_MINUTES)) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(
                mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
    }

    private void validateForgotCheckout(String checkoutTime, String currentTime) {
        if (!DateTimeUtils.convertStringToDate(checkoutTime)
                .after(DateTimeUtils.convertStringToDate(currentTime))) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateForgotCardOut(String checkoutTime, String currentTime) {
        if (DateTimeUtils.convertDateTimeToDate(checkoutTime)
                .equals(DateTimeUtils.convertDateTimeToDate(currentTime))) {
            setCheckoutTime(checkoutTime);
            return;
        }
        validateErrorDialog(mContext.getString(R.string.form_overdue));
    }

    private void validateCheckoutForgotCardAllDay(String checkoutTime, String currentTime) {
        if (!DateTimeUtils.convertDateTimeToDate(mCheckinTime)
                .equals(DateTimeUtils.convertDateTimeToDate(checkoutTime))) {
            validateErrorDialog(mContext.getString(R.string.time_must_be_is_in_a_day));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(checkoutTime, TWELVE_HOUR, FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.the_working_time_dose_not_fit_to_the_request));
            return;
        }
        setCheckoutTime(checkoutTime);
    }

    private void validateCompensationFromTime(String compensationFromTime) {
        switch (mCurrentLeaveType) {
            case LeaveType.IN_LATE_A:
            case LeaveType.IN_LATE_M:
            case LeaveType.LEAVE_EARLY_A:
            case LeaveType.LEAVE_EARLY_M:
            case LeaveType.LEAVE_OUT:
                validateCompensationTime(compensationFromTime);
                break;
            default:
                break;
        }
    }

    private void validateCompensationTime(String compensationFromTime) {
        if (StringUtils.isNotBlank(mCheckinTime)) {
            if (DateTimeUtils.convertStringToDate(compensationFromTime)
                    .before(DateTimeUtils.convertStringToDate(mCheckinTime))) {
                validateErrorDialog(
                        mContext.getString(R.string.compensation_time_is_not_in_the_past));
                return;
            }
        } else {
            if (DateTimeUtils.convertStringToDate(compensationFromTime)
                    .before(DateTimeUtils.convertStringToDate(mCheckoutTime))) {
                validateErrorDialog(
                        mContext.getString(R.string.compensation_time_is_not_in_the_past));
                return;
            }
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, ELEVEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_compensation_time_can_not_in_working_time));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, TWELVE_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_time_can_not_in_lunch_break_time_of_company));
            return;
        }
        if (DateTimeUtils.checkHourOfDateLessThan(compensationFromTime, SIXTEEN_HOUR,
                FORTY_FIVE_MINUTES)) {
            validateErrorDialog(
                    mContext.getString(R.string.your_compensation_time_can_not_in_working_time));
            return;
        }
        setCompensationTime(compensationFromTime);
    }

    private void setCompensationTime(String compensationFromTime) {
        setCompensationFromTime(compensationFromTime);
        String workingTime;
        switch (mCurrentLeaveType) {
            case LeaveType.IN_LATE_M:
                workingTime = DateTimeUtils.changeTimeOfDateString(mCheckinTime, SEVEN_HOUR,
                        FORTY_FIVE_MINUTES);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        DateTimeUtils.getMinutesBetweenTwoDate(mCheckinTime, workingTime)));
                break;
            case LeaveType.IN_LATE_A:
                workingTime = DateTimeUtils.changeTimeOfDateString(mCheckinTime, TWELVE_HOUR,
                        FORTY_FIVE_MINUTES);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        DateTimeUtils.getMinutesBetweenTwoDate(mCheckinTime, workingTime)));
                break;
            case LeaveType.LEAVE_EARLY_M:
                workingTime = DateTimeUtils.changeTimeOfDateString(mCheckoutTime, ELEVEN_HOUR,
                        FORTY_FIVE_MINUTES);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        DateTimeUtils.getMinutesBetweenTwoDate(workingTime, mCheckoutTime)));
                break;
            case LeaveType.LEAVE_EARLY_A:
                workingTime = DateTimeUtils.changeTimeOfDateString(mCheckoutTime, SIXTEEN_HOUR,
                        FORTY_FIVE_MINUTES);
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        DateTimeUtils.getMinutesBetweenTwoDate(workingTime, mCheckoutTime)));
                break;
            case LeaveType.LEAVE_OUT:
                setCompensationToTime(DateTimeUtils.addMinutesToStringDate(mCompensationFromTime,
                        DateTimeUtils.getMinutesBetweenTwoDate(mCheckoutTime, mCheckinTime)));
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
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, mRequest);
        mActionType = ActionType.ACTION_CONFIRM_CREATE;
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, mActionType);
        mNavigator.startActivityForResult(ConfirmRequestLeaveActivity.class, bundle,
                Constant.RequestCode.REQUEST_LEAVE);
    }

    public void clearTime() {
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
            return "";
        }
        return mRequest.getWorkspaceName();
    }

    @Bindable
    public String getCurrentGroup() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mCurrentGroup;
        }
        if (mRequest.getCompanyName() != null) {
            return "";
        }
        return mRequest.getCompanyName();
    }

    @Bindable
    public String getCurrentLeaveType() {
        return mCurrentLeaveTypeName;
    }

    private void setCurrentLeaveType() {
        mCurrentLeaveType = mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getId();
        mRequest.setLeaveTypeId(mUser.getLeaveTypes().get(mCurrentLeaveTypePosition).getId());
        mRequest.setLeaveType(mUser.getLeaveTypes().get(mCurrentLeaveTypePosition));
        notifyPropertyChanged(BR.currentLeaveType);
    }

    public void setCurrentBranch() {
        mCurrentBranch = mUser.getBranches().get(mCurrentBranchPosition).getBranchName();
        mRequest.setWorkpaceId(mUser.getBranches().get(mCurrentBranchPosition).getBranchId());
        mRequest.setWorkspaceName(mCurrentBranch);
        notifyPropertyChanged(BR.currentBranch);
    }

    public void setCurrentGroup() {
        mCurrentGroup = mUser.getGroups().get(mCurrentGroupPosition).getGroupName();
        mRequest.setGroupId(mUser.getGroups().get(mCurrentGroupPosition).getGroupId());
        mRequest.setCompanyName(mCurrentGroup);
        notifyPropertyChanged(BR.currentGroup);
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
        return mRequest.getCompensationRequest().getFromTime();
    }

    public void setCompensationFromTime(String compensationFromTime) {
        mCompensationFromTime = compensationFromTime;
        mRequest.getCompensationRequest().setFromTime(compensationFromTime);
        notifyPropertyChanged(BR.compensationFromTime);
    }

    @Bindable
    public String getCompensationToTime() {
        return mRequest.getCompensationRequest().getToTime();
    }

    public void setCompensationToTime(String compensationToTime) {
        mCompensationToTime = compensationToTime;
        mRequest.getCompensationRequest().setToTime(compensationToTime);
        notifyPropertyChanged(BR.compensationToTime);
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
            LeaveType.IN_LATE_M, LeaveType.IN_LATE_A, LeaveType.LEAVE_EARLY_M,
            LeaveType.LEAVE_EARLY_A, LeaveType.LEAVE_OUT, LeaveType.FORGOT_CHECK_ALL_DAY,
            LeaveType.FORGOT_TO_CHECK_IN, LeaveType.FORGOT_TO_CHECK_OUT,
            LeaveType.FORGOT_CARD_ALL_DAY, LeaveType.FORGOT_CARD_IN, LeaveType.FORGOT_CARD_OUT,
            LeaveType.IN_LATE_WOMAN_M, LeaveType.IN_LATE_WOMAN_A, LeaveType.LEAVE_EARLY_WOMAN_M,
            LeaveType.LEAVE_EARLY_WOMAN_A
    })

    public @interface LeaveType {
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
