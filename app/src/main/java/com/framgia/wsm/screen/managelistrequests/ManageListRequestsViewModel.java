package com.framgia.wsm.screen.managelistrequests;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.model.User;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.screen.managelistrequests.memberrequestdetail
        .MemberRequestDetailDialogFragment;
import com.framgia.wsm.screen.managelistrequests.memberrequestdetail.MemberRequestDetailViewModel;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.TypeStatus;
import com.framgia.wsm.utils.TypeToast;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.StringUtils;
import com.framgia.wsm.utils.navigator.NavigateAnim;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import com.fstyle.library.DialogAction;
import com.fstyle.library.MaterialDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Exposes the data to be used in the Managelistrequests screen.
 */

public class ManageListRequestsViewModel extends BaseObservable
        implements ManageListRequestsContract.ViewModel, ItemRecyclerViewClickListener,
        ActionRequestListener, DatePickerDialog.OnDateSetListener,
        MemberRequestDetailViewModel.ApproveOrRejectListener {

    private static final String TAG = "ListRequestViewModel";
    private static final int CURRRENT_STATUS = 0;
    private static final int PAGE_ONE = 1;

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
    private boolean mIsFromTimeSelected;
    private int mCurrentPositionStatus;
    private String mUserName;
    private ActionRequest mActionRequest;
    private int mItemPosition;
    private int mAction;
    private boolean mIsLoading;
    private boolean isRefreshEnable;
    private int mPage;
    private boolean mIsShowProgress;
    private boolean mIsVisiableLayoutDataNotFound;
    private boolean mIsSelectAll;
    private boolean mIsLoadDataFirstTime;
    private String mTotalRequestSelected;
    private int mCutOffDate;
    private String mNotificationData;
    private boolean mEventStatusFromNotifications;

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
        mDialogManager.dialogDatePicker(this, Calendar.getInstance());
        initData();
    }

    private void initData() {
        mPresenter.getUser();
        setLoading(false);
        mPage = PAGE_ONE;
        mCurrentStatus = mContext.getString(R.string.pending);
        mCurrentPositionStatus = CURRRENT_STATUS;
        mActionRequest = new ActionRequest();
        mFromTime = formatDate(DateTimeUtils.dayFirstMonthWorking(mCutOffDate));
        mToTime = formatDate(DateTimeUtils.dayLastMonthWorking(mCutOffDate));
        mQueryRequest = new QueryRequest();
        mQueryRequest.setFromTime(mFromTime);
        mQueryRequest.setToTime(mToTime);
        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
        mQueryRequest.setPage(String.valueOf(mPage));
        setLoadDataFirstTime(true);
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
        mEventStatusFromNotifications = false;
        setNotificationData(mContext.getString(R.string.can_not_load_data));
        setShowProgress(false);
        setVisiableLayoutDataNotFound(true);
        mNavigator.showToastCustom(TypeToast.ERROR, exception.getMessage());
    }

    @Override
    public void onGetListRequestManageSuccess(Object object, boolean isLoadMore) {
        mEventStatusFromNotifications = false;
        setLoading(false);
        setShowProgress(false);
        setNotificationData(mContext.getString(R.string.can_not_find_request));
        switch (mRequestType) {
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> listOverTime = (List<RequestOverTime>) object;
                checkSizeListRequest(listOverTime.size(), isLoadMore);
                mManageListRequestsAdapter.updateDataRequestOverTime(listOverTime, isLoadMore);
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> listOff = (List<OffRequest>) object;
                checkSizeListRequest(listOff.size(), isLoadMore);
                mManageListRequestsAdapter.updateDataRequestOff(listOff, isLoadMore);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> listLeave = (List<LeaveRequest>) object;
                checkSizeListRequest(listLeave.size(), isLoadMore);
                mManageListRequestsAdapter.updateDataRequest(listLeave, isLoadMore);
                break;
            default:
                break;
        }
        setLoadDataFirstTime(false);
    }

    @Override
    public void setCurrentStatusFromNotifications() {
        mEventStatusFromNotifications = true;
        setPage(PAGE_ONE);
        setUserName(null);
        setCurrentStatus(null);
        setFromTimeNotGetData(null);
        setToTimeNotGetData(null);
        mCurrentPositionStatus = TypeStatus.NONE;
        mQueryRequest.setStatus(null);
        mQueryRequest.setFromTime(null);
        mQueryRequest.setToTime(null);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    @Override
    public void onReloadData() {
        if (!mEventStatusFromNotifications) {
            setPage(PAGE_ONE);
            mFromTime = formatDate(DateTimeUtils.dayFirstMonthWorking(mCutOffDate));
            mToTime = formatDate(DateTimeUtils.dayLastMonthWorking(mCutOffDate));
            setFromTimeNotGetData(mFromTime);
            setToTimeNotGetData(mToTime);
            mQueryRequest.setFromTime(mFromTime);
            mQueryRequest.setToTime(mToTime);
            setCurrentStatus(mContext.getString(R.string.pending));
            mCurrentPositionStatus = CURRRENT_STATUS;
            mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
            if (mIsLoadDataFirstTime) {
                mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
                return;
            }
            mPresenter.getListAllRequestManageNoProgressDialog(mRequestType, mQueryRequest, false);
        }
    }

    void setRequestType(@RequestType int requestType) {
        setPage(PAGE_ONE);
        mRequestType = requestType;
        mQueryRequest.setRequestType(requestType);
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mActionRequest.setTypeRequest(TypeRequest.LEAVE);
                break;
            case RequestType.REQUEST_OFF:
                mActionRequest.setTypeRequest(TypeRequest.OFF);
                break;
            case RequestType.REQUEST_OVERTIME:
                mActionRequest.setTypeRequest(TypeRequest.OT);
                break;
            default:
                break;
        }
    }

    @Override
    public void onApproveOrRejectRequestSuccess(ActionRequestResponse actionRequestResponse) {
        setLoading(false);
        mManageListRequestsAdapter.updateItem(mRequestType, mItemPosition, actionRequestResponse,
                mCurrentStatus);
        checkTotalRequestSelected();
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                checkCurrentSizeListRequest(
                        mManageListRequestsAdapter.getListLeaveRequest().size());
                break;
            case RequestType.REQUEST_OFF:
                checkCurrentSizeListRequest(mManageListRequestsAdapter.getListOffRequest().size());
                break;
            case RequestType.REQUEST_OVERTIME:
                checkCurrentSizeListRequest(
                        mManageListRequestsAdapter.getListOverTimeRequest().size());
                break;
            default:
                break;
        }
        if (mAction == TypeAction.APPROVE) {
            mNavigator.showToastCustom(TypeToast.SUCCESS,
                    mContext.getString(R.string.approve_success));
            return;
        }
        mNavigator.showToastCustom(TypeToast.ERROR, mContext.getString(R.string.reject_success));
    }

    @Override
    public void onApproveOrRejectRequestError(BaseException exception) {
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
    public void onLoadMoreListRequest() {
        setShowProgress(true);
        mPresenter.getListAllRequestManageNoProgressDialog(mRequestType, mQueryRequest, true);
    }

    @Override
    public void onApproveAllRequestSuccess(List<ActionRequestResponse> actionRequestResponseList) {
        setLoading(false);
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> leaveRequests = mManageListRequestsAdapter.getListLeaveRequest();
                for (int i = 0; i < leaveRequests.size(); i++) {
                    LeaveRequest leaveRequest = leaveRequests.get(i);
                    for (ActionRequestResponse actionRequestResponse : actionRequestResponseList) {
                        if (leaveRequest.getId() == actionRequestResponse.getRequestId()) {
                            mManageListRequestsAdapter.updateItem(mRequestType, i,
                                    actionRequestResponse, mCurrentStatus);
                            if (StringUtils.isNotBlank(mCurrentStatus)) {
                                i--;
                            }
                        }
                    }
                }
                checkCurrentSizeListRequest(
                        mManageListRequestsAdapter.getListLeaveRequest().size());
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> offRequests = mManageListRequestsAdapter.getListOffRequest();
                for (int i = 0; i < offRequests.size(); i++) {
                    OffRequest offRequest = offRequests.get(i);
                    for (ActionRequestResponse actionRequestResponse : actionRequestResponseList) {
                        if (offRequest.getId() == actionRequestResponse.getRequestId()) {
                            mManageListRequestsAdapter.updateItem(mRequestType, i,
                                    actionRequestResponse, mCurrentStatus);
                            if (StringUtils.isNotBlank(mCurrentStatus)) {
                                i--;
                            }
                        }
                    }
                }
                checkCurrentSizeListRequest(mManageListRequestsAdapter.getListOffRequest().size());
                break;
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> requestOverTimes =
                        mManageListRequestsAdapter.getListOverTimeRequest();
                for (int i = 0; i < requestOverTimes.size(); i++) {
                    RequestOverTime requestOverTime = requestOverTimes.get(i);
                    for (ActionRequestResponse actionRequestResponse : actionRequestResponseList) {
                        if (requestOverTime.getId() == actionRequestResponse.getRequestId()) {
                            mManageListRequestsAdapter.updateItem(mRequestType, i,
                                    actionRequestResponse, mCurrentStatus);
                            if (StringUtils.isNotBlank(mCurrentStatus)) {
                                i--;
                            }
                        }
                    }
                }
                checkCurrentSizeListRequest(
                        mManageListRequestsAdapter.getListOverTimeRequest().size());
                break;
            default:
                break;
        }
        checkSizeApproveAllListRequest(actionRequestResponseList.size());
    }

    @Override
    public void onApproveAllRequestError(BaseException exception) {
        mDialogManager.dialogError(exception);
    }

    @Override
    public void onGetUserSuccess(User user) {
        if (user == null || user.getCompany() == null) {
            return;
        }
        mCutOffDate = user.getCompany().getCutOffDate();
    }

    @Override
    public void onGetUserError(BaseException exception) {
        Log.e(TAG, "onGetUserError", exception);
    }

    @Override
    public void onApproveRequest(int itemPosition, int requestId) {
        mAction = TypeAction.APPROVE;
        approveOrRejectRequest(itemPosition, requestId, StatusCode.ACCEPT_CODE);
    }

    @Override
    public void onRejectRequest(int itemPosition, int requestId) {
        mAction = TypeAction.REJECT;
        approveOrRejectRequest(itemPosition, requestId, StatusCode.REJECT_CODE);
    }

    @Override
    public void onCheckedItem(int positionItem, boolean isChecked) {
        mManageListRequestsAdapter.updateCheckedItem(mRequestType, positionItem, !isChecked);
        checkTotalRequestSelected();
    }

    @Override
    public void onItemRecyclerViewClick(Object object, int position) {
        MemberRequestDetailDialogFragment dialogFragment =
                MemberRequestDetailDialogFragment.newInstance(object, position);
        dialogFragment.setApproveOrRejectListener(this);
        mNavigator.showDialogFragment(R.id.layout_container, dialogFragment, false,
                NavigateAnim.NONE, MemberRequestDetailDialogFragment.TAG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateTime = formatDate(DateTimeUtils.convertDateToString(year, month, dayOfMonth));
        if (year == 0) {
            if (mIsFromTimeSelected) {
                setFromTimeNotGetData(null);
            } else {
                setToTimeNotGetData(null);
            }
        }
        if (!view.isShown()) {
            return;
        }
        if (mIsFromTimeSelected) {
            validateFromTime(dateTime);
        } else {
            validateToTime(dateTime);
        }
    }

    public ManageListRequestsAdapter getManageListRequestsAdapter() {
        return mManageListRequestsAdapter;
    }

    private void approveOrRejectRequest(int itemPosition, int requestId, String statusCode) {
        mActionRequest.setApproveAll(false);
        mItemPosition = itemPosition;
        mActionRequest.setStatus(statusCode);
        mActionRequest.setRequestId(requestId);
        mPresenter.approveOrRejectRequest(mActionRequest);
    }

    private void checkCurrentSizeListRequest(int size) {
        if (size == 0) {
            setVisiableLayoutDataNotFound(true);
        }
    }

    private void checkSizeApproveAllListRequest(int size) {
        checkTotalRequestSelected();
        if (size == 0) {
            mNavigator.showToastCustom(TypeToast.ERROR,
                    mContext.getString(R.string.accept_all_unsuccess));
            return;
        }
        String message = mContext.getString(R.string.accept_all_success)
                + Constant.BLANK
                + size
                + Constant.BLANK
                + mContext.getString(R.string.requests);
        mNavigator.showToastCustom(TypeToast.SUCCESS, message);
    }

    private void checkSizeListRequest(int size, boolean isLoadMore) {
        mPage++;
        mQueryRequest.setPage(String.valueOf(mPage));
        if (isLoadMore) {
            checkTotalRequestSelected();
        } else {
            if (size == 0) {
                setVisiableLayoutDataNotFound(true);
            }
        }
    }

    private void checkSelectAll(int totalRequestSelected, int totalRequestCanSelect) {
        setTotalRequestSelected(String.valueOf(totalRequestSelected));
        if (totalRequestSelected == totalRequestCanSelect && totalRequestSelected > 0) {
            setSelectAll(true);
        } else {
            setSelectAll(false);
        }
    }

    private void checkTotalRequestSelected() {
        int totalRequestSelected = 0;
        int totalRequestCanSelect = 0;
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> listLeaveRequest =
                        mManageListRequestsAdapter.getListLeaveRequest();
                for (LeaveRequest leaveRequest : listLeaveRequest) {
                    if (leaveRequest.isChecked() && leaveRequest.isCanApproveReject()) {
                        totalRequestSelected++;
                    }
                    if (requestCanSelected(leaveRequest.getStatus(),
                            leaveRequest.isCanApproveReject())) {
                        totalRequestCanSelect++;
                    }
                }
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> listOffRequest = mManageListRequestsAdapter.getListOffRequest();
                for (OffRequest offRequest : listOffRequest) {
                    if (offRequest.isChecked()) {
                        totalRequestSelected++;
                    }
                    if (requestCanSelected(offRequest.getStatus(),
                            offRequest.isCanApproveReject())) {
                        totalRequestCanSelect++;
                    }
                }
                break;
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> listOverTime =
                        mManageListRequestsAdapter.getListOverTimeRequest();
                for (RequestOverTime requestOverTime : listOverTime) {
                    if (requestOverTime.isChecked()) {
                        totalRequestSelected++;
                    }
                    if (requestCanSelected(requestOverTime.getStatus(),
                            requestOverTime.isCanApproveReject())) {
                        totalRequestCanSelect++;
                    }
                }
                break;
            default:
                break;
        }
        checkSelectAll(totalRequestSelected, totalRequestCanSelect);
    }

    @Bindable
    public String getFromTime() {
        return mFromTime;
    }

    public void setFromTime(String fromTime) {
        mFromTime = fromTime;
        mQueryRequest.setFromTime(fromTime);
        notifyPropertyChanged(BR.fromTime);
        setPage(PAGE_ONE);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    @Bindable
    public String getToTime() {
        return mToTime;
    }

    public void setToTime(String toTime) {
        mToTime = toTime;
        mQueryRequest.setToTime(toTime);
        notifyPropertyChanged(BR.toTime);
        setPage(PAGE_ONE);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    private String formatDate(String dataInput) {
        return DateTimeUtils.convertUiFormatToDataFormat(dataInput,
                DateTimeUtils.DATE_FORMAT_YYYY_MM_DD,
                mContext.getString(R.string.format_date_yyyy_mm_dd));
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
    public String getNotificationData() {
        return mNotificationData;
    }

    public void setNotificationData(String notificationData) {
        mNotificationData = notificationData;
        notifyPropertyChanged(BR.notificationData);
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

    @Bindable
    public String getTotalRequestSelected() {
        return mContext.getString(R.string.you_have_selected)
                + Constant.BLANK
                + mTotalRequestSelected
                + Constant.BLANK
                + mContext.getString(R.string.requests);
    }

    private void setTotalRequestSelected(String totalRequestSelected) {
        mTotalRequestSelected = totalRequestSelected;
        notifyPropertyChanged(BR.totalRequestSelected);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    private void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isRefreshEnable() {
        return isRefreshEnable;
    }

    private void setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
        notifyPropertyChanged(BR.refreshEnable);
    }

    private void setFromTimeNotGetData(String fromTime) {
        mFromTime = fromTime;
        mQueryRequest.setFromTime(fromTime);
        notifyPropertyChanged(BR.fromTime);
    }

    private void setToTimeNotGetData(String toTime) {
        mToTime = toTime;
        mQueryRequest.setToTime(toTime);
        notifyPropertyChanged(BR.toTime);
    }

    public void setPage(int page) {
        mPage = page;
        mQueryRequest.setPage(String.valueOf(mPage));
        setVisiableLayoutDataNotFound(false);
        setSelectAll(false);
        setTotalRequestSelected(String.valueOf(0));
    }

    @Bindable
    public boolean isShowProgress() {
        return mIsShowProgress;
    }

    private void setShowProgress(boolean showProgress) {
        mIsShowProgress = showProgress;
        notifyPropertyChanged(BR.showProgress);
    }

    @Bindable
    public boolean isVisiableLayoutDataNotFound() {
        return mIsVisiableLayoutDataNotFound;
    }

    private void setVisiableLayoutDataNotFound(boolean visiableLayoutDataNotFound) {
        mIsVisiableLayoutDataNotFound = visiableLayoutDataNotFound;
        notifyPropertyChanged(BR.visiableLayoutDataNotFound);
    }

    @Bindable
    public boolean isSelectAll() {
        return mIsSelectAll;
    }

    private void setSelectAll(boolean selectAll) {
        mIsSelectAll = selectAll;
        notifyPropertyChanged(BR.selectAll);
    }

    private void setLoadDataFirstTime(boolean loadDataFirstTime) {
        mIsLoadDataFirstTime = loadDataFirstTime;
    }

    public AppBarLayout.OnOffsetChangedListener getOnOffsetChangedListener() {
        return new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                setRefreshEnable(verticalOffset == 0);
            }
        };
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLoading(true);
                setPage(PAGE_ONE);
                mPresenter.getListAllRequestManageNoProgressDialog(mRequestType, mQueryRequest,
                        false);
            }
        };
    }

    private void validateFromTime(String fromTime) {
        String dateFormat = mContext.getString(R.string.format_date_yyyy_mm_dd);
        if (StringUtils.isNotBlank(mToTime) && DateTimeUtils.convertStringToDate(fromTime,
                dateFormat).after((DateTimeUtils.convertStringToDate(mToTime, dateFormat)))) {
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

    private void validateToTime(String toTime) {
        String dateFormat = mContext.getString(R.string.format_date_yyyy_mm_dd);
        if (StringUtils.isNotBlank(mFromTime) && DateTimeUtils.convertStringToDate(toTime,
                dateFormat).before((DateTimeUtils.convertStringToDate(mFromTime, dateFormat)))) {
            mDialogManager.dialogError(
                    mContext.getString(R.string.end_time_can_not_greater_than_end_time),
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog materialDialog,
                                @NonNull DialogAction dialogAction) {
                            mDialogManager.showDatePickerDialog();
                        }
                    });
            return;
        }
        setToTime(toTime);
    }

    public void onPickTypeStatus(View view) {
        mDialogManager.dialogListSingleChoice(mContext.getString(R.string.status), R.array.status,
                mCurrentPositionStatus, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view,
                            int positionType, CharSequence charSequence) {
                        setPage(PAGE_ONE);
                        mCurrentPositionStatus = positionType;
                        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
                        setCurrentStatus(String.valueOf(charSequence));
                        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
                        return true;
                    }
                });
    }

    public void onCickFromTime(View view) {
        mIsFromTimeSelected = true;
        if (StringUtils.isNotBlank(getFromTime())) {
            mDialogManager.dialogDatePicker(this, DateTimeUtils.getCalendarFromDate(getFromTime(),
                    mContext.getString(R.string.format_date_yyyy_mm_dd)));
        }
        mDialogManager.showDatePickerDialog();
    }

    public void onCickToTime(View view) {
        mIsFromTimeSelected = false;
        if (StringUtils.isNotBlank(getToTime())) {
            mDialogManager.dialogDatePicker(this, DateTimeUtils.getCalendarFromDate(getToTime(),
                    mContext.getString(R.string.format_date_yyyy_mm_dd)));
        }
        mDialogManager.showDatePickerDialog();
    }

    public void onClickClearDataFilter(View view) {
        setPage(PAGE_ONE);
        setUserName(null);
        setCurrentStatus(null);
        setFromTimeNotGetData(null);
        setToTimeNotGetData(null);
        mCurrentPositionStatus = TypeStatus.NONE;
        mQueryRequest.setStatus(null);
        mQueryRequest.setFromTime(null);
        mQueryRequest.setToTime(null);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    public void onClickSearch(View view) {
        setPage(PAGE_ONE);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    @Override
    public void onApproveOrRejectListener(@RequestType int requestType, int itemPosition,
            ActionRequestResponse actionRequestResponse) {
        mManageListRequestsAdapter.updateItem(requestType, itemPosition, actionRequestResponse,
                mCurrentStatus);
    }

    private void setStatusSelectAll(int totalRequestSelected) {
        if (mIsSelectAll) {
            setTotalRequestSelected(String.valueOf(0));
        } else {
            setTotalRequestSelected(String.valueOf(totalRequestSelected));
        }
        if (totalRequestSelected > 0) {
            setSelectAll(!mIsSelectAll);
            return;
        }
        setSelectAll(false);
        mNavigator.showToastCustom(TypeToast.WARNING,
                mContext.getString(R.string.there_is_no_request_to_choose));
    }

    private boolean requestCanSelected(String status, boolean isCanApprove) {
        return StatusCode.PENDING_CODE.equals(status)
                || StatusCode.REJECT_CODE.equals(status) && isCanApprove;
    }

    public void onSelectAll(View view) {
        int totalRequestSelected = 0;
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> leaveRequests = mManageListRequestsAdapter.getListLeaveRequest();
                for (int i = 0; i < leaveRequests.size(); i++) {
                    LeaveRequest leaveRequest = leaveRequests.get(i);
                    if (requestCanSelected(leaveRequest.getStatus(),
                            leaveRequest.isCanApproveReject())) {
                        mManageListRequestsAdapter.updateCheckedItem(mRequestType, i,
                                !mIsSelectAll);
                        totalRequestSelected++;
                    }
                }
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> offRequests = mManageListRequestsAdapter.getListOffRequest();
                for (int i = 0; i < offRequests.size(); i++) {
                    OffRequest offRequest = offRequests.get(i);
                    if (requestCanSelected(offRequest.getStatus(),
                            offRequest.isCanApproveReject())) {
                        mManageListRequestsAdapter.updateCheckedItem(mRequestType, i,
                                !mIsSelectAll);
                        totalRequestSelected++;
                    }
                }
                break;
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> overTimeRequests =
                        mManageListRequestsAdapter.getListOverTimeRequest();
                for (int i = 0; i < overTimeRequests.size(); i++) {
                    RequestOverTime requestOverTime = overTimeRequests.get(i);
                    if (requestCanSelected(requestOverTime.getStatus(),
                            requestOverTime.isCanApproveReject())) {
                        mManageListRequestsAdapter.updateCheckedItem(mRequestType, i,
                                !mIsSelectAll);
                        totalRequestSelected++;
                    }
                }
                break;
            default:
                break;
        }
        setStatusSelectAll(totalRequestSelected);
    }

    private void checkAcceptAll(List<Integer> listRequestId) {
        if (listRequestId.size() > 0) {
            mActionRequest.setListRequestId(listRequestId);
            mPresenter.approveAllRequest(mActionRequest);
            return;
        }
        mNavigator.showToastCustom(TypeToast.WARNING,
                mContext.getString(R.string.there_is_no_request_to_choose));
    }

    public void onAcceptAll(View view) {
        mActionRequest.setApproveAll(true);
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                List<Integer> listLeaveRequestId = new ArrayList<>();
                for (LeaveRequest leaveRequest : mManageListRequestsAdapter.getListLeaveRequest()) {
                    if (leaveRequest.isChecked()) {
                        listLeaveRequestId.add(leaveRequest.getId());
                    }
                }
                checkAcceptAll(listLeaveRequestId);
                break;
            case RequestType.REQUEST_OFF:
                List<Integer> listOffRequestId = new ArrayList<>();
                for (OffRequest offRequest : mManageListRequestsAdapter.getListOffRequest()) {
                    if (offRequest.isChecked()) {
                        listOffRequestId.add(offRequest.getId());
                    }
                }
                checkAcceptAll(listOffRequestId);
                break;
            case RequestType.REQUEST_OVERTIME:
                List<Integer> listOvertimeRequestId = new ArrayList<>();
                for (RequestOverTime requestOverTime : mManageListRequestsAdapter
                        .getListOverTimeRequest()) {
                    if (requestOverTime.isChecked()) {
                        listOvertimeRequestId.add(requestOverTime.getId());
                    }
                }
                checkAcceptAll(listOvertimeRequestId);
                break;
            default:
                break;
        }
    }

    @StringDef({ TypeRequest.LEAVE, TypeRequest.OFF, TypeRequest.OT })
    @interface TypeRequest {
        String LEAVE = "leave";
        String OFF = "off";
        String OT = "ot";
    }

    @IntDef({ TypeAction.APPROVE, TypeAction.REJECT })
    @interface TypeAction {
        int APPROVE = 0;
        int REJECT = 1;
    }
}
