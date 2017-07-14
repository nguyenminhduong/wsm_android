package com.framgia.wsm.screen.listrequest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.screen.confirmrequestleave.ConfirmRequestLeaveActivity;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.MaterialDialog;
import java.util.Calendar;
import java.util.List;

/**
 * Exposes the data to be used in the ListRequest screen.
 */

public class ListRequestViewModel extends BaseObservable
        implements ListRequestContract.ViewModel, DatePickerDialog.OnDateSetListener,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> {
    private static final String TAG = "ListRequestViewModel";
    private static final int FORMAT_MONTH = 10;

    private Context mContext;
    private ListRequestContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private int mCurrentPositionStatus;
    private String mCurrentStatus;
    private String mMonthYear;
    private int mYear;
    private int mMonth;
    private Calendar mCalendar;
    private ListRequestAdapter mListRequestAdapter;
    private Navigator mNavigator;
    @RequestType
    private int mRequestType;

    ListRequestViewModel(Context context, ListRequestContract.Presenter presenter,
            DialogManager dialogManager, ListRequestAdapter listRequestAdapter,
            Navigator navigator) {
        mContext = context;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDialogManager.dialogMonthYearPicker(this, mYear, mMonth);
        mListRequestAdapter = listRequestAdapter;
        mListRequestAdapter.setItemClickListener(this);
        mNavigator = navigator;
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
    public void onItemRecyclerViewClick(Object object) {
        Bundle bundle = new Bundle();
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                bundle.putParcelable(Constant.EXTRA_REQUEST_LEAVE, (LeaveRequest) object);
                bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
                mNavigator.startActivityForResultFromFragment(ConfirmRequestLeaveActivity.class,
                        bundle, Constant.RequestCode.REQUEST_LEAVE);
                break;
            case RequestType.REQUEST_OFF:
                bundle.putParcelable(Constant.EXTRA_REQUEST_OFF, (OffRequest) object);
                bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
                mNavigator.startActivityForResultFromFragment(ConfirmRequestOffActivity.class,
                        bundle, Constant.RequestCode.REQUEST_OFF);
                break;
            case RequestType.REQUEST_OVERTIME:
                bundle.putParcelable(Constant.EXTRA_REQUEST_OVERTIME, (RequestOverTime) object);
                bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_DETAIL);
                mNavigator.startActivityForResultFromFragment(ConfirmOvertimeActivity.class, bundle,
                        Constant.RequestCode.REQUEST_OVERTIME);
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetListRequestError(BaseException e) {
        mNavigator.showToast(e.getMessage());
    }

    @Override
    public void onGetListRequestSuccess(int requestType, Object object) {
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                mListRequestAdapter.updateDataRequestOverTime((List<RequestOverTime>) object);
                break;
            case RequestType.REQUEST_OFF:
                mListRequestAdapter.updateDataRequestOff((List<OffRequest>) object);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                mListRequestAdapter.updateDataRequest((List<LeaveRequest>) object);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReloadData(int requestType) {
        mPresenter.getListAllRequest(requestType);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        updateMonthYear();
    }

    private void setCurrentPositionStatus(int currentPositionStatus) {
        mCurrentPositionStatus = currentPositionStatus;
    }

    @Bindable
    public String getCurrentStatus() {
        return mCurrentStatus;
    }

    private void setCurrentStatus(String currentStatus) {
        mCurrentStatus = currentStatus;
        notifyPropertyChanged(BR.currentStatus);
    }

    @Bindable
    public String getMonthYear() {
        return mMonthYear;
    }

    private void setMonthYear(String monthYear) {
        mMonthYear = monthYear;
        notifyPropertyChanged(BR.monthYear);
    }

    public ListRequestAdapter getListRequestAdapter() {
        return mListRequestAdapter;
    }

    public void setRequestType(int requestType) {
        mRequestType = requestType;
    }

    public void onPickMonthYear(View view) {
        mDialogManager.showMonthYearPickerDialog();
    }

    private void updateMonthYear() {
        // Default value mMonth = 0 (From 0 to 11) => localMonth = 1 (From 1 to 12)
        int localMonth = (mMonth + 1);
        // Format month 01/2017...instead 1/2017...
        String month = localMonth < FORMAT_MONTH ? "0" + localMonth : Integer.toString(localMonth);
        String year = Integer.toString(mYear);
        setMonthYear(String.valueOf(
                new StringBuilder().append(month).append("/").append(year).append("")));
    }

    public void onPickTypeStatus(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.status), R.array.status,
                mCurrentPositionStatus, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setCurrentPositionStatus(positionType);
                        setCurrentStatus(String.valueOf(charSequence));
                        return true;
                    }
                });
    }

    public void onSearchRequest(View view) {
        switch (mRequestType) {
            case RequestType.REQUEST_OVERTIME:
                if (mMonthYear != null) {
                    mPresenter.getListRequestOverTimeWithStatusAndTime(mCurrentPositionStatus,
                            mMonthYear);
                } else if (mCurrentPositionStatus == 0) {
                    mPresenter.getListAllRequest(mRequestType);
                } else {
                    mPresenter.getListRequestOverTimeWithStatusAndTime(mCurrentPositionStatus, "");
                }
                break;
            case RequestType.REQUEST_LATE_EARLY:
                if (mMonthYear != null) {
                    mPresenter.getListRequestLeaveWithStatusAndTime(mCurrentPositionStatus,
                            mMonthYear);
                } else if (mCurrentPositionStatus == 0) {
                    mPresenter.getListAllRequest(mRequestType);
                } else {
                    mPresenter.getListRequestLeaveWithStatusAndTime(mCurrentPositionStatus, "");
                }
                break;
            case RequestType.REQUEST_OFF:
                if (mMonthYear != null) {
                    mPresenter.getListRequestOffWithStatusAndTime(mCurrentPositionStatus,
                            mMonthYear);
                } else if (mCurrentPositionStatus == 0) {
                    mPresenter.getListAllRequest(mRequestType);
                } else {
                    mPresenter.getListRequestOffWithStatusAndTime(mCurrentPositionStatus, "");
                }
                break;
            default:
                break;
        }
    }

    public void onRemoveMonth(View view) {
        setMonthYear(null);
        mCurrentPositionStatus = PositionStatus.FORWARDED;
        setCurrentStatus(null);
    }

    @IntDef({
            PositionStatus.FORWARDED, PositionStatus.ACCEPTED, PositionStatus.PENDING,
            PositionStatus.REJECTED
    })
    @interface PositionStatus {
        int FORWARDED = 0;
        int ACCEPTED = 1;
        int PENDING = 2;
        int REJECTED = 3;
    }
}
