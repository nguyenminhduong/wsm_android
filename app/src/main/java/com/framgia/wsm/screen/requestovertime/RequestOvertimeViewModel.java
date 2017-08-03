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
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Branch;
import com.framgia.wsm.data.model.Group;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
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
    private RequestOverTime mRequestOverTime;
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
    private int mCurrentBranchPosition;
    private int mCurrentGroupPosition;
    private int mActionType;

    RequestOvertimeViewModel(Context context, RequestOvertimeContract.Presenter presenter,
            Navigator navigator, DialogManager dialogManager, RequestOverTime requestOverTime) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mNavigator = navigator;
        mPresenter.getUser();
        mDialogManager = dialogManager;
        mDialogManager.dialogDatePicker(this);
        mDialogManager.dialogTimePicker(this);
        initRequest(requestOverTime);
    }

    private void initRequest(RequestOverTime requestOverTime) {
        if (requestOverTime != null) {
            mRequestOverTime = requestOverTime;
        } else {
            mRequestOverTime = new RequestOverTime();
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
        if (year == 0) {
            if (mIsFromTimeSelected) {
                setFromTime(null);
            } else {
                setToTime(null);
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
        if (mIsFromTimeSelected) {
            setToTime(null);
            validateFromTime(currentDateTime);
        } else {
            validateToTime(currentDateTime);
        }
    }

    public String getTitleToolbar() {
        if (mActionType == ActionType.ACTION_CREATE) {
            return mContext.getString(R.string.request_overtime);
        }
        return mContext.getString(R.string.edit_request_overtime);
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    @Bindable
    public RequestOverTime getRequestOverTime() {
        return mRequestOverTime;
    }

    private void setCurrentBranch() {
        Branch branch = mUser.getBranches().get(mCurrentBranchPosition);
        mRequestOverTime.setBranchId(branch.getBranchId());
        mRequestOverTime.setBranch(branch);
        notifyPropertyChanged(BR.requestOverTime);
    }

    private void setCurrentGroup() {
        Group group = mUser.getGroups().get(mCurrentGroupPosition);
        mRequestOverTime.setGroupId(group.getGroupId());
        mRequestOverTime.setGroup(mUser.getGroups().get(mCurrentGroupPosition));
        notifyPropertyChanged(BR.requestOverTime);
    }

    @Bindable
    public String getFromTime() {
        return mRequestOverTime.getFromTime();
    }

    public void setFromTime(String fromTime) {
        mRequestOverTime.setFromTime(fromTime);
        notifyPropertyChanged(BR.requestOverTime);
    }

    @Bindable
    public String getToTime() {
        return mRequestOverTime.getToTime();
    }

    public void setToTime(String toTime) {
        mRequestOverTime.setToTime(toTime);
        notifyPropertyChanged(BR.requestOverTime);
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

    public void setActionType(int actionType) {
        mActionType = actionType;
    }

    public boolean isEditGroupEnable() {
        return mActionType == ActionType.ACTION_CREATE;
    }

    public void onCickFromTime(View view) {
        mIsFromTimeSelected = true;
        mDialogManager.showDatePickerDialog();
    }

    public void onCickToTime(View view) {
        if (mRequestOverTime.getFromTime() != null) {
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
        if (DateTimeUtils.getDayOfMonth(toTime) == DateTimeUtils.getDayOfMonth(
                mRequestOverTime.getFromTime()) && DateTimeUtils.convertStringToDateTime(
                mRequestOverTime.getFromTime())
                .before(DateTimeUtils.convertStringToDateTime(toTime))) {
            setToTime(toTime);
        } else {
            validateErrorDialog(mContext.getString(R.string.end_time_is_less_than_start_time));
        }
    }

    private void setBranchAndGroupWhenEdit(User user) {
        if (user.getBranches() == null || user.getBranches().size() == 0) {
            return;
        }
        for (int i = 0; i < user.getBranches().size(); i++) {
            String branchName = user.getBranches().get(i).getBranchName();
            if (branchName.equals(mRequestOverTime.getBranch().getBranchName())) {
                mCurrentBranchPosition = i;
                mRequestOverTime.setBranchId(user.getBranches().get(i).getBranchId());
            }
        }
        if (user.getGroups() == null || user.getGroups().size() == 0) {
            return;
        }
        for (int i = 0; i < user.getGroups().size(); i++) {
            String groupName = user.getGroups().get(i).getFullName();
            if (groupName.equals(mRequestOverTime.getGroup().getFullName())) {
                mCurrentGroupPosition = i;
                mRequestOverTime.setGroupId(user.getGroups().get(i).getGroupId());
            }
        }
    }

    public void onClickNext(View view) {
        if (!mPresenter.validateDataInput(mRequestOverTime)) {
            return;
        }
        if (mRequestOverTime.getBranch() == null) {
            mDialogManager.dialogError(mContext.getString(R.string.branch_is_emty));
            return;
        }
        if (mRequestOverTime.getGroup() == null) {
            mDialogManager.dialogError(mContext.getString(R.string.group_is_emty));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, mActionType);
        bundle.putParcelable(EXTRA_REQUEST_OVERTIME, mRequestOverTime);
        mNavigator.startActivityForResult(ConfirmOvertimeActivity.class, bundle,
                Constant.RequestCode.REQUEST_OVERTIME);
    }

    public void onCickArrowBack(View view) {
        mNavigator.finishActivity();
    }
}
