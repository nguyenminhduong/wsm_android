package com.framgia.wsm.screen.managelistrequests;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.LeaveRequest;
import com.framgia.wsm.data.model.OffRequest;
import com.framgia.wsm.data.model.RequestOverTime;
import com.framgia.wsm.data.source.remote.api.response.ActionRequestResponse;
import com.framgia.wsm.databinding.ItemManageListRequestBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestType;
import com.framgia.wsm.utils.StatusCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tri on 10/07/2017.
 */

public class ManageListRequestsAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private ItemRecyclerViewClickListener mItemClickListener;
    private List<LeaveRequest> mRequestsLeaves;
    private List<OffRequest> mRequestsOffs;
    private List<RequestOverTime> mRequestOverTimes;
    private ActionRequestListener mActionRequestListener;
    @RequestType
    private int mRequestType;

    ManageListRequestsAdapter(@NonNull Context context, @RequestType int requestType) {
        super(context);
        mRequestType = requestType;
        mRequestsLeaves = new ArrayList<>();
        mRequestsOffs = new ArrayList<>();
        mRequestOverTimes = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemManageListRequestBinding itemManageListRequestBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_manage_list_request, parent, false);
        switch (viewType) {
            case RequestType.REQUEST_OVERTIME:
                return new ManageListRequestsAdapter.RequestOverTimeViewHolder(
                        itemManageListRequestBinding, mItemClickListener);
            case RequestType.REQUEST_OFF:
                return new ManageListRequestsAdapter.RequestOffViewHolder(
                        itemManageListRequestBinding, mItemClickListener);
            case RequestType.REQUEST_LATE_EARLY:
                return new ManageListRequestsAdapter.RequestLeaveViewHolder(
                        itemManageListRequestBinding, mItemClickListener);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RequestType.REQUEST_OVERTIME:
                ManageListRequestsAdapter.RequestOverTimeViewHolder requestOverTimeViewHolder =
                        (ManageListRequestsAdapter.RequestOverTimeViewHolder) holder;
                requestOverTimeViewHolder.bind(mRequestOverTimes.get(position), position);
                break;
            case RequestType.REQUEST_OFF:
                ManageListRequestsAdapter.RequestOffViewHolder requestOffViewHolder =
                        (ManageListRequestsAdapter.RequestOffViewHolder) holder;
                requestOffViewHolder.bind(mRequestsOffs.get(position), position);
                break;
            case RequestType.REQUEST_LATE_EARLY:
                ManageListRequestsAdapter.RequestLeaveViewHolder requestLeaveViewHolder =
                        (ManageListRequestsAdapter.RequestLeaveViewHolder) holder;
                requestLeaveViewHolder.bind(mRequestsLeaves.get(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mRequestType;
    }

    @Override
    public int getItemCount() {
        if (mRequestType == RequestType.REQUEST_OVERTIME) {
            return mRequestOverTimes.size();
        } else if (mRequestType == RequestType.REQUEST_OFF) {
            return mRequestsOffs.size();
        } else {
            return mRequestsLeaves.size();
        }
    }

    List<LeaveRequest> getListLeaveRequest() {
        return mRequestsLeaves;
    }

    List<OffRequest> getListOffRequest() {
        return mRequestsOffs;
    }

    List<RequestOverTime> getListOverTimeRequest() {
        return mRequestOverTimes;
    }

    private boolean isCanUpdateItem(String currentStatus, String statusRequuest,
            boolean isCanApprove) {
        return StatusCode.FORWARD_CODE.equals(statusRequuest) && isCanApprove
                || currentStatus == null;
    }

    void updateItem(int requestType, int position, ActionRequestResponse actionRequestResponse,
            String currentStatus) {
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                if (isCanUpdateItem(currentStatus, actionRequestResponse.getStatus(),
                        actionRequestResponse.isCanApproveReject())) {
                    mRequestsLeaves.get(position).setStatus(actionRequestResponse.getStatus());
                    mRequestsLeaves.get(position)
                            .setCanApproveReject(actionRequestResponse.isCanApproveReject());
                    mRequestsLeaves.get(position).setChecked(false);
                    notifyItemChanged(position, mRequestsLeaves);
                    return;
                }
                mRequestsLeaves.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                break;
            case RequestType.REQUEST_OFF:
                if (isCanUpdateItem(currentStatus, actionRequestResponse.getStatus(),
                        actionRequestResponse.isCanApproveReject())) {
                    mRequestsOffs.get(position).setStatus(actionRequestResponse.getStatus());
                    mRequestsOffs.get(position)
                            .setCanApproveReject(actionRequestResponse.isCanApproveReject());
                    mRequestsOffs.get(position).setChecked(false);
                    notifyItemChanged(position, mRequestsOffs);
                    return;
                }
                mRequestsOffs.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                break;
            case RequestType.REQUEST_OVERTIME:
                if (isCanUpdateItem(currentStatus, actionRequestResponse.getStatus(),
                        actionRequestResponse.isCanApproveReject())) {
                    mRequestOverTimes.get(position).setStatus(actionRequestResponse.getStatus());
                    mRequestOverTimes.get(position)
                            .setCanApproveReject(actionRequestResponse.isCanApproveReject());
                    mRequestOverTimes.get(position).setChecked(false);
                    notifyItemChanged(position, mRequestOverTimes);
                    return;
                }
                mRequestOverTimes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                break;
            default:
                break;
        }
    }

    void updateCheckedItem(int requestType, int position, boolean isChecked) {
        switch (requestType) {
            case RequestType.REQUEST_LATE_EARLY:
                mRequestsLeaves.get(position).setChecked(isChecked);
                notifyItemChanged(position, mRequestsLeaves);
                break;
            case RequestType.REQUEST_OFF:
                mRequestsOffs.get(position).setChecked(isChecked);
                notifyItemChanged(position, mRequestsOffs);
                break;
            case RequestType.REQUEST_OVERTIME:
                mRequestOverTimes.get(position).setChecked(isChecked);
                notifyItemChanged(position, mRequestOverTimes);
                break;
            default:
                break;
        }
    }

    void updateDataRequest(List<LeaveRequest> requests, boolean isLoadMore) {
        if (requests == null) {
            return;
        }
        if (!isLoadMore) {
            mRequestsLeaves.clear();
        }
        mRequestsLeaves.addAll(requests);
        notifyDataSetChanged();
    }

    void updateDataRequestOff(List<OffRequest> requestOffs, boolean isLoadMore) {
        if (requestOffs == null) {
            return;
        }
        if (!isLoadMore) {
            mRequestsOffs.clear();
        }
        mRequestsOffs.addAll(requestOffs);
        notifyDataSetChanged();
    }

    void updateDataRequestOverTime(List<RequestOverTime> requestOverTimes, boolean isLoadMore) {
        if (requestOverTimes == null) {
            return;
        }
        if (!isLoadMore) {
            mRequestOverTimes.clear();
        }
        mRequestOverTimes.addAll(requestOverTimes);
        notifyDataSetChanged();
    }

    void setItemClickListener(ItemRecyclerViewClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    void setActionRequestListener(ActionRequestListener actionRequestListener) {
        mActionRequestListener = actionRequestListener;
    }

    private class RequestOverTimeViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private ItemRecyclerViewClickListener mItemClickListener;

        RequestOverTimeViewHolder(ItemManageListRequestBinding binding,
                ItemRecyclerViewClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(RequestOverTime requestOverTime, int itemPosition) {
            mBinding.setViewModel(
                    new ItemManageListRequestViewModel(requestOverTime, mItemClickListener,
                            mActionRequestListener, itemPosition));
            mBinding.executePendingBindings();
        }
    }

    private class RequestOffViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private ItemRecyclerViewClickListener mItemClickListener;

        RequestOffViewHolder(ItemManageListRequestBinding binding,
                ItemRecyclerViewClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(OffRequest offRequest, int itemPosition) {
            mBinding.setViewModel(new ItemManageListRequestViewModel(offRequest, mItemClickListener,
                    mActionRequestListener, itemPosition));
            mBinding.executePendingBindings();
        }
    }

    private class RequestLeaveViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private ItemRecyclerViewClickListener mItemClickListener;

        RequestLeaveViewHolder(ItemManageListRequestBinding binding,
                ItemRecyclerViewClickListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(LeaveRequest leaveRequest, int itemPosition) {
            mBinding.setViewModel(
                    new ItemManageListRequestViewModel(leaveRequest, mItemClickListener,
                            mActionRequestListener, itemPosition));
            mBinding.executePendingBindings();
        }
    }
}
