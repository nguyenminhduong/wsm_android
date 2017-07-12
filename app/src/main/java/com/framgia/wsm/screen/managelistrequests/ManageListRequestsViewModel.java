package com.framgia.wsm.screen.managelistrequests;

import android.content.Context;
import android.databinding.BaseObservable;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestType;
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
    public void setRequestType(@RequestType int requestType) {
        mRequestType = requestType;
    }

    @Override
    public void onItemRecyclerViewClick(Object item) {
        //TODO on click item
    }

    @Override
    public void onApproveRequest(int requestID) {
        //TODO on click approve request
    }

    @Override
    public void onRejectRequest(int requestID) {
        //TODO on click reject request
    }

    public ManageListRequestsAdapter getManageListRequestsAdapter() {
        return mManageListRequestsAdapter;
    }

    public void onGetListRequestSuccess(int requestType, Object object) {
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
}
