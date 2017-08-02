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
import android.view.View;
import android.widget.DatePicker;
import com.framgia.wsm.BR;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.data.source.remote.api.request.ActionRequest;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.screen.managelistrequests.memberrequestdetail
        .MemberRequestDetailDialogFragment;
import com.framgia.wsm.screen.managelistrequests.memberrequestdetail.MemberRequestDetailViewModel;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.StatusCode;
import com.framgia.wsm.utils.TypeToast;
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
    private boolean mIsVisibleLayoutSearch;
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
    private boolean mIsVisiableLayoutFooter;

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
        setLoading(false);
        mPage = PAGE_ONE;
        mCurrentStatus = mContext.getString(R.string.pending);
        mCurrentPositionStatus = CURRRENT_STATUS;
        mActionRequest = new ActionRequest();
        mFromTime = DateTimeUtils.dayFirstMonthWorking();
        mToTime = DateTimeUtils.dayLasttMonthWorking();
        mQueryRequest = new QueryRequest();
        mQueryRequest.setFromTime(mFromTime);
        mQueryRequest.setToTime(mToTime);
        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
        mQueryRequest.setPage(String.valueOf(mPage));
        setVisiableLayoutFooter(true);
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
        setShowProgress(false);
        setVisiableLayoutDataNotFound(true);
    }

    @Override
    public void onGetListRequestManageSuccess(Object object, boolean isLoadMore) {
        setLoading(false);
        setShowProgress(false);
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
    }

    @Override
    public void onReloadData() {
        setPage(PAGE_ONE);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
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
        updateItemRequest(mRequestType, mItemPosition, actionRequestResponse);
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
    public void showLayoutFooter() {
        setVisiableLayoutFooter(true);
    }

    @Override
    public void hideLayoutFooter() {
        setVisiableLayoutFooter(false);
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
    public void onItemRecyclerViewClick(Object object, int position) {
        MemberRequestDetailDialogFragment dialogFragment =
                MemberRequestDetailDialogFragment.newInstance(object, position);
        dialogFragment.setApproveOrRejectListener(this);
        mNavigator.showDialogFragment(R.id.layout_container, dialogFragment, false,
                NavigateAnim.NONE, MemberRequestDetailDialogFragment.TAG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dateTime = DateTimeUtils.convertDateToString(year, month, dayOfMonth);
        if (mIsFromTimeSelected) {
            validateFromTime(dateTime);
        } else {
            validateToTime(dateTime);
        }
    }

    public ManageListRequestsAdapter getManageListRequestsAdapter() {
        return mManageListRequestsAdapter;
    }

    private void updateItemRequest(@RequestType int requestType, int itemPosition,
            ActionRequestResponse actionRequestResponse) {
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        actionRequestResponse, mCurrentStatus);
                break;
            case RequestType.REQUEST_OFF:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        actionRequestResponse, mCurrentStatus);
                break;
            case RequestType.REQUEST_OVERTIME:
                mManageListRequestsAdapter.updateItem(requestType, itemPosition,
                        actionRequestResponse, mCurrentStatus);
                break;
            default:
                break;
        }
    }

    private void approveOrRejectRequest(int itemPosition, int requestId, String statusCode) {
        mItemPosition = itemPosition;
        mActionRequest.setStatus(statusCode);
        mActionRequest.setRequestId(requestId);
        mPresenter.approveOrRejectRequest(mActionRequest);
    }

    private void checkSizeListRequest(int size, boolean isLoadMore) {
        if (size == 0 && !isLoadMore) {
            setVisiableLayoutDataNotFound(true);
            return;
        }
        mPage++;
        mQueryRequest.setPage(String.valueOf(mPage));
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

    public void setPage(int page) {
        mPage = page;
        mQueryRequest.setPage(String.valueOf(mPage));
        setVisiableLayoutDataNotFound(false);
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
    public boolean isVisiableLayoutFooter() {
        return mIsVisiableLayoutFooter;
    }

    public void setVisiableLayoutFooter(boolean visiableLayoutFooter) {
        mIsVisiableLayoutFooter = visiableLayoutFooter;
        notifyPropertyChanged(BR.visiableLayoutFooter);
    }

    @Bindable
    public boolean isVisiableLayoutDataNotFound() {
        return mIsVisiableLayoutDataNotFound;
    }

    private void setVisiableLayoutDataNotFound(boolean visiableLayoutDataNotFound) {
        mIsVisiableLayoutDataNotFound = visiableLayoutDataNotFound;
        notifyPropertyChanged(BR.visiableLayoutDataNotFound);
    }

    private void setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
        notifyPropertyChanged(BR.refreshEnable);
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

    private void validateToTime(String toTime) {
        if (DateTimeUtils.convertStringToDate(toTime)
                .before((DateTimeUtils.convertStringToDate(mFromTime)))) {
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
        setPage(PAGE_ONE);
        setUserName(null);
        setCurrentStatus(null);
        mFromTime = DateTimeUtils.dayFirstMonthWorking();
        mToTime = DateTimeUtils.dayLasttMonthWorking();
        setFromTime(mFromTime);
        setToTime(mToTime);
        mQueryRequest.setStatus(null);
        mQueryRequest.setFromTime(mFromTime);
        mQueryRequest.setToTime(mToTime);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    public void onClickSearch(View view) {
        setPage(PAGE_ONE);
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest, false);
    }

    @Override
    public void onApproveOrRejectListener(@RequestType int requestType, int itemPosition,
            ActionRequestResponse actionRequestResponse) {
        updateItemRequest(requestType, itemPosition, actionRequestResponse);
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
