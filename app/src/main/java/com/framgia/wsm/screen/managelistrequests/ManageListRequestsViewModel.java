package com.framgia.wsm.screen.managelistrequests;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.screen.managelistrequests.memberrequestdetail
        .MemberRequestDetailDialogFragment;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.NavigateAnim;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.List;

/**
 * Exposes the data to be used in the Managelistrequests screen.
 */

public class ManageListRequestsViewModel extends BaseObservable
        implements ManageListRequestsContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>, ActionRequestListener,
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "ListRequestViewModel";

    private Context mContext;
    private ManageListRequestsContract.Presenter mPresenter;
    private DialogManager mDialogManager;
    private Navigator mNavigator;
    private ManageListRequestsAdapter mManageListRequestsAdapter;
    private int mRequestType;
    private QueryRequest mQueryRequest;
    private String mFromTime;
    private String mToTime;
    private String mCurrentStatus;
    private boolean mIsVisibleLayoutSearch;
    private boolean mIsFromTimeSelected;
    private int mCurrentPositionStatus;
    private String mUserName;

    ManageListRequestsViewModel(Context context, ManageListRequestsContract.Presenter presenter,
            DialogManager dialogManager, ManageListRequestsAdapter manageListRequestsAdapter,
            Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mManageListRequestsAdapter = manageListRequestsAdapter;
        mManageListRequestsAdapter.setItemClickListener(this);
        mManageListRequestsAdapter.setActionRequestListener(this);
        mDialogManager.dialogDatePicker(this);
        initData();
    }

    private void initData() {
        mFromTime = DateTimeUtils.dayFirstMonthWorking();
        mToTime = DateTimeUtils.dayLasttMonthWorking();
        mQueryRequest = new QueryRequest();
        mQueryRequest.setFromTime(mFromTime);
        mQueryRequest.setToTime(mToTime);
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
    public void onGetListRequestManageError(BaseException exception) {
        mNavigator.showToast(exception.getMessage());
    }

    @Override
    public void onGetListRequestManageSuccess(@RequestType int requestType, Object object) {
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> listOverTime = (List<RequestOverTime>) object;
                if (listOverTime.size() == 0) {
                    mNavigator.showToast(mContext.getString(R.string.can_not_find_data));
                }
                mManageListRequestsAdapter.updateDataRequestOverTime(listOverTime);
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> listOff = (List<OffRequest>) object;
                if (listOff.size() == 0) {
                    mNavigator.showToast(mContext.getString(R.string.can_not_find_data));
                }
                mManageListRequestsAdapter.updateDataRequestOff(listOff);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> listLeave = (List<LeaveRequest>) object;
                if (listLeave.size() == 0) {
                    mNavigator.showToast(mContext.getString(R.string.can_not_find_data));
                }
                mManageListRequestsAdapter.updateDataRequest(listLeave);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReloadData() {
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest);
    }

    public void setRequestType(@RequestType int requestType) {
        mRequestType = requestType;
        mQueryRequest.setRequestType(requestType);
    }

    @Override
    public void onApproveRequestSuccess(@RequestType int requestType, int itemPosition,
            Object object) {
        updateItemRequest(requestType, itemPosition, object);
    }

    @Override
    public void onApproveRequestError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onRejectRequestSuccess(@RequestType int requestType, int itemPosition,
            Object object) {
        updateItemRequest(requestType, itemPosition, object);
    }

    @Override
    public void onRejectRequestError(BaseException exception) {
        mDialogManager.dialogError(exception);
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
    public void onApproveRequest(int itemPosition, int requestId) {
        //        mPresenter.approveRequest(mRequestType, itemPosition, requestId);
        //TODO Remove later when have api

        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "approve");
                break;
            case RequestType.REQUEST_OFF:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "approve");
                break;
            case RequestType.REQUEST_OVERTIME:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "approve");
                break;
            default:
                break;
        }
        mNavigator.showToast(mContext.getString(R.string.approve_success));
    }

    @Override
    public void onRejectRequest(int itemPosition, int requestId) {
        //       mPresenter.rejectRequest(mRequestType, itemPosition, requestId);
        // TODO Remove later when have api

        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "discard");
                break;
            case RequestType.REQUEST_OFF:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "discard");
                break;
            case RequestType.REQUEST_OVERTIME:
                mManageListRequestsAdapter.updateItem(mRequestType, itemPosition, "discard");
                break;
            default:
                break;
        }
        mNavigator.showToast(mContext.getString(R.string.reject_success));
    }

    @Override
    public void onItemRecyclerViewClick(Object item) {
        mNavigator.showDialogFragment(R.id.layout_container,
                MemberRequestDetailDialogFragment.newInstance(item), false, NavigateAnim.NONE,
                MemberRequestDetailDialogFragment.TAG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateTime = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        if (mIsFromTimeSelected) {
            validateFromTime(dateTime);
        } else {
            setToTime(dateTime);
        }
    }

    public ManageListRequestsAdapter getManageListRequestsAdapter() {
        return mManageListRequestsAdapter;
    }

    private void updateItemRequest(@RequestType int requestType, int itemPosition, Object object) {
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        ((LeaveRequest) object).getStatus());
                break;
            case RequestType.REQUEST_OFF:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        ((OffRequest) object).getStatus());
                break;
            case RequestType.REQUEST_OVERTIME:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        ((RequestOverTime) object).getStatus());
                break;
            default:
                break;
        }
    }

    @Bindable
    public boolean isVisibleLayoutSearch() {
        return mIsVisibleLayoutSearch;
    }

    private void setVisibleLayoutSearch(boolean visibleLayoutSearch) {
        mIsVisibleLayoutSearch = visibleLayoutSearch;
        notifyPropertyChanged(BR.visibleLayoutSearch);
    }

    @Bindable
    public String getFromTime() {
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
        mQueryRequest.setFromTime(fromTime);
        notifyPropertyChanged(BR.fromTime);
    }

    @Bindable
    public String getToTime() {
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
        mQueryRequest.setToTime(toTime);
        notifyPropertyChanged(BR.toTime);
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
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        mQueryRequest.setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    private void validateFromTime(String fromTime) {
        if (DateTimeUtils.convertStringToDate(fromTime)
                .after((DateTimeUtils.convertStringToDate(mToTime)))) {
            mDialogManager.dialogError(
                    mContext.getString(R.string.start_time_can_not_greater_than_end_time),
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog,
                                @NonNull DialogAction dialogAction) {
                            mDialogManager.showDatePickerDialog();
                        }
                    });
            return;
        }
        setFromTime(fromTime);
    }

    public void onPickTypeStatus(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.status), R.array.status,
                mCurrentPositionStatus, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        mCurrentPositionStatus = positionType;
                        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
                        setCurrentStatus(String.valueOf(charSequence));
                        return true;
                    }
                });
    }

    public void onCickFromTime(View view) {
        mIsFromTimeSelected = true;
        mDialogManager.showDatePickerDialog();
    }

    public void onCickToTime(View view) {
        mIsFromTimeSelected = false;
        mDialogManager.showDatePickerDialog();
    }

    public void onClickFloatingButtonSearch(View view) {
        if (mIsVisibleLayoutSearch) {
            setVisibleLayoutSearch(false);
            return;
        }
        setVisibleLayoutSearch(true);
    }

    public void onClickClearDataFilter(View view) {
        setUserName(null);
        setCurrentStatus(null);
        mFromTime = DateTimeUtils.dayFirstMonthWorking();
        mToTime = DateTimeUtils.dayLasttMonthWorking();
        setFromTime(mFromTime);
        setToTime(mToTime);
        mQueryRequest.setStatus(null);
        mQueryRequest.setFromTime(mFromTime);
        mQueryRequest.setToTime(mToTime);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest);
    }

    public void onClickSearch(View view) {
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest);
    }
}
