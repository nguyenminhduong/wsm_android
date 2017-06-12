package com.framgia.wsm.screen.requestovertime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.android.databinding.library.baseAdapters.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.utils.validator.Rule;
import com.framgia.wsm.utils.validator.ValidType;
import com.framgia.wsm.utils.validator.Validation;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.Calendar;

import static com.framgia.wsm.utils.Constant.EXTRA_REQUEST_OVERTIME;

/**
 * Exposes the data to be used in the RequestOvertime screen.
 */
public class RequestOvertimeViewModel extends BaseObservable
        implements RequestOvertimeContract.ViewModel, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "RequestOvertimeViewModel";
    private static final int EIGHT_HOUR = 18;

    private RequestOvertimeContract.Presenter mPresenter;
    private Request mRequest;
    private User mUser;
    private Navigator mNavigator;
    private String mProjectNameError;
    private String mReasonError;
    private String mFromTimeError;
    private String mToTimeError;
    private DialogManager mDialogManager;
    private String mCurrentDate;
    private Context mContext;
    private boolean mIsFromTimeSelected = true;
    private String mCurrentBranch;
    private String mCurrentGroup;

    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mProjectName;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mReason;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mFromTime;
    @Validation({
            @Rule(types = ValidType.NON_EMPTY, message = R.string.is_empty)
    })
    private String mToTime;

    RequestOvertimeViewModel(Context context, RequestOvertimeContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mRequest = new Request();
        mPresenter.getUser();
        mDialogManager = dialogManager;
        mDialogManager.dialogDatePicker(this);
        mDialogManager.dialogTimePicker(this);
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
    }

    @Override
    public void onGetUserError(BaseException e) {
        Log.e(TAG, "RequestOvertimeViewModel", e);
    }

    @Override
    public void onInputProjectNameError(String projectNameError) {
        mProjectNameError = projectNameError;
        notifyPropertyChanged(BR.projectNameError);
    }

    @Override
    public void onInputReasonError(String reasonError) {
        mReasonError = reasonError;
        notifyPropertyChanged(BR.reasonError);
    }

    @Override
    public void onInputFromTimeError(String fromTImeError) {
        mFromTimeError = fromTImeError;
        notifyPropertyChanged(BR.fromTimeError);
    }

    @Override
    public void onInputToTimeError(String toTimeError) {
        mToTimeError = toTimeError;
        notifyPropertyChanged(BR.toTimeError);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCurrentDate = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        mDialogManager.showTimePickerDialog();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String currentDateTime =
                DateTimeUtils.convertDateTimeToString(mCurrentDate, hourOfDay, minute);
        if (mIsFromTimeSelected) {
            setToTime(null);
            validateFromTime(currentDateTime);
        } else {
            validateToTime(currentDateTime);
        }
    }

    public void onCickFromTime(View view) {
        mIsFromTimeSelected = true;
        mDialogManager.showDatePickerDialog();
    }

    public void onCickToTime(View view) {
        if (mFromTime != null) {
            mIsFromTimeSelected = false;
            mDialogManager.showDatePickerDialog();
        } else {
            mDialogManager.dialogError(
                    mContext.getString(R.string.you_have_to_choose_the_from_time_first),
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog,
                                @NonNull DialogAction dialogAction) {
                            materialDialog.dismiss();
                        }
                    });
        }
    }

    public void onClickNext(View view) {
        if (!mPresenter.validateDataInput(mRequest)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_REQUEST_OVERTIME, mRequest);
        mNavigator.startActivity(ConfirmOvertimeActivity.class, bundle);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }

    private void validateErrorDialog(String error) {
        mDialogManager.dialogError(error, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog,
                    @NonNull DialogAction dialogAction) {
                if (mIsFromTimeSelected) {
                    setFromTime(null);
                } else {
                    setToTime(null);
                }
                mDialogManager.showDatePickerDialog();
            }
        });
    }

    private void validateFromTime(String fromTime) {
        if (DateTimeUtils.getDayOfWeek(fromTime) != Calendar.SATURDAY
                && DateTimeUtils.getDayOfWeek(fromTime) != Calendar.SUNDAY) {
            if (DateTimeUtils.getHourOfDay(fromTime) >= EIGHT_HOUR) {
                setFromTime(fromTime);
            } else {
                validateErrorDialog(mContext.getString(R.string.time_request_overtime_invalid));
            }
        } else {
            setFromTime(fromTime);
        }
    }

    private void validateToTime(String toTime) {
        if (DateTimeUtils.getDayOfMonth(toTime) == DateTimeUtils.getDayOfMonth(mFromTime)
                && DateTimeUtils.getHourOfDay(toTime) >= DateTimeUtils.getHourOfDay(mFromTime)
                && DateTimeUtils.getMinute(toTime) > DateTimeUtils.getMinute(mFromTime)) {
            setToTime(toTime);
        } else {
            validateErrorDialog(mContext.getString(R.string.end_time_is_less_than_start_time));
        }
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
        mRequest.setProject(projectName);
    }

    @Bindable
    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
        mRequest.setReason(reason);
    }

    @Bindable
    public String getCurrentBranch() {
        return mCurrentBranch;
    }

    public void setCurrentBranch(String currentBranch) {
        mCurrentBranch = currentBranch;
        mRequest.getBranch().setBranchName(currentBranch);
        notifyPropertyChanged(BR.currentBranch);
    }

    @Bindable
    public String getCurrentGroup() {
        return mCurrentGroup;
    }

    public void setCurrentGroup(String currentGroup) {
        mCurrentGroup = currentGroup;
        mRequest.getGroup().setGroupName(currentGroup);
        notifyPropertyChanged(BR.currentGroup);
    }

    @Bindable
    public String getFromTime() {
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
        mRequest.setFromTime(fromTime);
        notifyPropertyChanged(BR.fromTime);
    }

    @Bindable
    public String getToTime() {
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
        mRequest.setToTime(toTime);
        notifyPropertyChanged(BR.toTime);
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
    public String getFromTimeError() {
        return mFromTimeError;
    }

    @Bindable
    public String getToTimeError() {
        return mToTimeError;
    }
}
