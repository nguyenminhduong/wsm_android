package com.framgia.wsm.screen.listrequest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;
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
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.screen.confirmrequestleave.ConfirmRequestLeaveActivity;
import com.framgia.wsm.screen.confirmrequestoff.ConfirmRequestOffActivity;
import com.framgia.wsm.screen.requestleave.RequestLeaveActivity;
import com.framgia.wsm.screen.requestoff.RequestOffActivity;
import com.framgia.wsm.screen.requestovertime.RequestOvertimeActivity;
import com.framgia.wsm.screen.requestovertime.confirmovertime.ConfirmOvertimeActivity;
import com.framgia.wsm.utils.ActionType;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.TypeToast;
import com.framgia.wsm.utils.common.DateTimeUtils;
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
    private static final int PAGE_ONE = 1;
    private static final int CURRRENT_STATUS = 0;

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
    private QueryRequest mQueryRequest;
    private boolean mIsLoading;
    private boolean mIsShowProgress;
    private boolean mIsVisiableLayoutDataNotFound;
    private boolean mIsLoadDataFirstTime;
    private int mPage;
    private int mCutOffDate;
    private String mNotificationLoadData;

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
        initData();
    }

    private void initData() {
        mPresenter.getUser();
        mPage = PAGE_ONE;
        setLoading(false);
        mMonthYear = DateTimeUtils.getMonthWorking(mCutOffDate);
        mQueryRequest = new QueryRequest();
        mQueryRequest.setMonthWorking(mMonthYear);
        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
        mQueryRequest.setPage(String.valueOf(mPage));
        mCurrentStatus = mContext.getString(R.string.pending);
        mCurrentPositionStatus = CURRRENT_STATUS;
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
        mPresenter.getListAllRequestNoProgressDialog(mRequestType, mQueryRequest, true);
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
        setNotificationLoadData(mContext.getString(R.string.can_not_load_data));
        setShowProgress(false);
        setVisiableLayoutDataNotFound(true);
        mNavigator.showToastCustom(TypeToast.ERROR, e.getMessage());
    }

    @Override
    public void onGetListRequestSuccess(int requestType, Object object, boolean isLoadMore) {
        setNotificationLoadData(mContext.getString(R.string.can_not_find_request));
        setLoading(false);
        setShowProgress(false);
        switch (requestType) {
            case RequestType.REQUEST_OVERTIME:
                List<RequestOverTime> listOverTime = (List<RequestOverTime>) object;
                checkSizeListRequest(listOverTime.size(), isLoadMore);
                mListRequestAdapter.updateDataRequestOverTime(listOverTime, isLoadMore);
                break;
            case RequestType.REQUEST_OFF:
                List<OffRequest> listOff = (List<OffRequest>) object;
                checkSizeListRequest(listOff.size(), isLoadMore);
                mListRequestAdapter.updateDataRequestOff(listOff, isLoadMore);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                List<LeaveRequest> listLeave = (List<LeaveRequest>) object;
                checkSizeListRequest(listLeave.size(), isLoadMore);
                mListRequestAdapter.updateDataRequest(listLeave, isLoadMore);
                break;
            default:
                break;
        }
        setLoadDataFirstTime(false);
    }

    @Override
    public void onReloadData(int requestType) {
        setPage(PAGE_ONE);
        mMonthYear = DateTimeUtils.getMonthWorking(mCutOffDate);
        mQueryRequest = new QueryRequest();
        mQueryRequest.setMonthWorking(mMonthYear);
        mQueryRequest.setStatus(String.valueOf(mCurrentPositionStatus));
        mQueryRequest.setPage(String.valueOf(mPage));
        mCurrentStatus = mContext.getString(R.string.pending);
        mCurrentPositionStatus = CURRRENT_STATUS;
        setMonthYear(mMonthYear);
        setCurrentStatus(mCurrentStatus);
        if (mIsLoadDataFirstTime) {
            mPresenter.getListAllRequest(requestType, mQueryRequest, false);
            return;
        }
        mPresenter.getListAllRequestNoProgressDialog(requestType, mQueryRequest, false);
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
        mQueryRequest.setMonthWorking(monthYear);
        notifyPropertyChanged(BR.monthYear);
        setPage(PAGE_ONE);
        mPresenter.getListAllRequest(mRequestType, mQueryRequest, false);
    }

    @Bindable
    public boolean isLoading() {
        return mIsLoading;
    }

    public void setLoading(boolean loading) {
        mIsLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    @Bindable
    public boolean isShowProgress() {
        return mIsShowProgress;
    }

    private void setShowProgress(boolean showProgress) {
        mIsShowProgress = showProgress;
        notifyPropertyChanged(BR.showProgress);
    }

    public void setPage(int page) {
        mPage = page;
        mQueryRequest.setPage(String.valueOf(mPage));
        setVisiableLayoutDataNotFound(false);
    }

    @Bindable
    public boolean isVisiableLayoutDataNotFound() {
        return mIsVisiableLayoutDataNotFound;
    }

    private void setVisiableLayoutDataNotFound(boolean visiableLayoutDataNotFound) {
        mIsVisiableLayoutDataNotFound = visiableLayoutDataNotFound;
        notifyPropertyChanged(BR.visiableLayoutDataNotFound);
    }

    private void setLoadDataFirstTime(boolean loadDataFirstTime) {
        mIsLoadDataFirstTime = loadDataFirstTime;
    }

    private void setMonthYearNotGetData(String monthYear) {
        mMonthYear = monthYear;
        mQueryRequest.setMonthWorking(monthYear);
        notifyPropertyChanged(BR.monthYear);
    }

    @Bindable
    public String getNotificationLoadData() {
        return mNotificationLoadData;
    }

    public void setNotificationLoadData(String notificationLoadData) {
        mNotificationLoadData = notificationLoadData;
        notifyPropertyChanged(BR.notificationLoadData);
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setLoading(true);
                setPage(PAGE_ONE);
                mPresenter.getListAllRequestNoProgressDialog(mRequestType, mQueryRequest, false);
            }
        };
    }

    public ListRequestAdapter getListRequestAdapter() {
        return mListRequestAdapter;
    }

    private void checkSizeListRequest(int size, boolean isLoadMore) {
        mPage++;
        mQueryRequest.setPage(String.valueOf(mPage));
        if (size == 0 && !isLoadMore) {
            setVisiableLayoutDataNotFound(true);
        }
    }

    public void setRequestType(int requestType) {
        setPage(PAGE_ONE);
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
                        setPage(PAGE_ONE);
                        setCurrentPositionStatus(positionType);
                        mQueryRequest.setStatus(String.valueOf(positionType));
                        setCurrentStatus(String.valueOf(charSequence));
                        mPresenter.getListAllRequest(mRequestType, mQueryRequest, false);
                        return true;
                    }
                });
    }

    public void onSearchRequest(View view) {
        setPage(PAGE_ONE);
        mPresenter.getListAllRequest(mRequestType, mQueryRequest, false);
    }

    public void onCreateRequest(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.EXTRA_ACTION_TYPE, ActionType.ACTION_CREATE);
        switch (mRequestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mNavigator.startActivityForResultFromFragment(RequestLeaveActivity.class, bundle,
                        Constant.RequestCode.REQUEST_LEAVE);
                break;
            case RequestType.REQUEST_OVERTIME:
                mNavigator.startActivityForResultFromFragment(RequestOvertimeActivity.class, bundle,
                        Constant.RequestCode.REQUEST_OVERTIME);
                break;
            case RequestType.REQUEST_OFF:
                mNavigator.startActivityForResultFromFragment(RequestOffActivity.class, bundle,
                        Constant.RequestCode.REQUEST_OFF);
                break;
            default:
                break;
        }
    }

    public void onClearData(View view) {
        setPage(PAGE_ONE);
        setCurrentStatus(null);
        setMonthYearNotGetData(null);
        mQueryRequest.setStatus(null);
        mQueryRequest.setMonthWorking(null);
        mPresenter.getListAllRequest(mRequestType, mQueryRequest, false);
    }
}
