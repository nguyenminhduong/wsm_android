package com.framgia.wsm.screen.managelistrequests;

import android.content.Context;
import android.databinding.BaseObservable;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.QueryRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.error.BaseException;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.navigator.Navigator;
import com.framgia.wsm.widget.dialog.DialogManager;
import java.util.List;

/**
 * Exposes the data to be used in the Managelistrequests screen.
 */

public class ManageListRequestsViewModel extends BaseObservable
        implements ManageListRequestsContract.ViewModel,
        BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object>, ActionRequestListener {

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

    public ManageListRequestsViewModel(Context context,
            ManageListRequestsContract.Presenter presenter, DialogManager dialogManager,
            ManageListRequestsAdapter manageListRequestsAdapter, Navigator navigator) {
        mContext = context;
        mNavigator = navigator;
        mPresenter = presenter;
        mPresenter.setViewModel(this);
        mDialogManager = dialogManager;
        mManageListRequestsAdapter = manageListRequestsAdapter;
        mManageListRequestsAdapter.setItemClickListener(this);
        mManageListRequestsAdapter.setActionRequestListener(this);
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
                mManageListRequestsAdapter.updateDataRequestOverTime(
                        (List<RequestOverTime>) object);
                break;
            case RequestType.REQUEST_OFF:
                mManageListRequestsAdapter.updateDataRequestOff((List<OffRequest>) object);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                mManageListRequestsAdapter.updateDataRequest((List<LeaveRequest>) object);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReloadData(@RequestType int requestType) {
        mPresenter.getListAllRequestManage(mRequestType, mQueryRequest);
    }

    public void setRequestType(@RequestType int requestType) {
        mRequestType = requestType;
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
        mPresenter.approveRequest(mRequestType, itemPosition, requestId);
    }

    @Override
    public void onRejectRequest(int itemPosition, int requestId) {
        mPresenter.rejectRequest(mRequestType, itemPosition, requestId);
    }

    @Override
    public void onItemRecyclerViewClick(Object item) {
        //TODO on click item
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
}
