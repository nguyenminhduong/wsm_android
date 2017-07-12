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
import com.framgia.wsm.databinding.ItemManageListRequestBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tri on 10/07/2017.
 */

public class ManageListRequestsAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private OnRecyclerViewItemClickListener<Object> mItemClickListener;
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
                requestOverTimeViewHolder.bind(mRequestOverTimes.get(position));
                break;
            case RequestType.REQUEST_OFF:
                ManageListRequestsAdapter.RequestOffViewHolder requestOffViewHolder =
                        (ManageListRequestsAdapter.RequestOffViewHolder) holder;
                requestOffViewHolder.bind(mRequestsOffs.get(position));
                break;
            case RequestType.REQUEST_LATE_EARLY:
                ManageListRequestsAdapter.RequestLeaveViewHolder requestLeaveViewHolder =
                        (ManageListRequestsAdapter.RequestLeaveViewHolder) holder;
                requestLeaveViewHolder.bind(mRequestsLeaves.get(position));
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

    void updateDataRequest(List<LeaveRequest> requests) {
        if (requests == null) {
            return;
        }
        mRequestsLeaves.clear();
        mRequestsLeaves.addAll(requests);
        notifyDataSetChanged();
    }

    void updateDataRequestOff(List<OffRequest> requestOffs) {
        if (requestOffs == null) {
            return;
        }
        mRequestsOffs.clear();
        mRequestsOffs.addAll(requestOffs);
        notifyDataSetChanged();
    }

    void updateDataRequestOverTime(List<RequestOverTime> requestOverTimes) {
        if (requestOverTimes == null) {
            return;
        }
        mRequestOverTimes.clear();
        mRequestOverTimes.addAll(requestOverTimes);
        notifyDataSetChanged();
    }

    void setItemClickListener(OnRecyclerViewItemClickListener<Object> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    void setActionRequestListener(ActionRequestListener actionRequestListener) {
        mActionRequestListener = actionRequestListener;
    }

    private class RequestOverTimeViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private OnRecyclerViewItemClickListener<Object> mItemClickListener;

        RequestOverTimeViewHolder(ItemManageListRequestBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(RequestOverTime requestOverTime) {
            mBinding.setViewModel(
                    new ItemManageListRequestViewModel(requestOverTime, mItemClickListener,
                            mActionRequestListener));
            mBinding.executePendingBindings();
        }
    }

    private class RequestOffViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private OnRecyclerViewItemClickListener<Object> mItemClickListener;

        RequestOffViewHolder(ItemManageListRequestBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(OffRequest offRequest) {
            mBinding.setViewModel(new ItemManageListRequestViewModel(offRequest, mItemClickListener,
                    mActionRequestListener));
            mBinding.executePendingBindings();
        }
    }

    private class RequestLeaveViewHolder extends RecyclerView.ViewHolder {
        private ItemManageListRequestBinding mBinding;
        private OnRecyclerViewItemClickListener<Object> mItemClickListener;

        RequestLeaveViewHolder(ItemManageListRequestBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Object> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(LeaveRequest leaveRequest) {
            mBinding.setViewModel(
                    new ItemManageListRequestViewModel(leaveRequest, mItemClickListener,
                            mActionRequestListener));
            mBinding.executePendingBindings();
        }
    }
}
