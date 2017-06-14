package com.framgia.wsm.screen.listrequest;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Request;
import com.framgia.wsm.databinding.ItemListRequestLeaveBinding;
import com.framgia.wsm.databinding.ItemListRequestOffBinding;
import com.framgia.wsm.databinding.ItemListRequestOvertimeBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.RequestType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 12/06/2017.
 */

public class ListRequestAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private OnRecyclerViewItemClickListener<Request> mItemClickListener;
    private List<Request> mRequests;
    @RequestType
    private int mRequestType;

    ListRequestAdapter(@NonNull Context context, @RequestType int requestType) {
        super(context);
        mRequestType = requestType;
        mRequests = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case RequestType.REQUEST_OVERTIME:
                ItemListRequestOvertimeBinding itemListRequestOvertimeBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_list_request_overtime, parent, false);
                return new RequestOverTimeViewHolder(itemListRequestOvertimeBinding,
                        mItemClickListener);
            case RequestType.REQUEST_OFF:
                ItemListRequestOffBinding itemListRequestOffBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_list_request_off, parent, false);
                return new RequestOffViewHolder(itemListRequestOffBinding, mItemClickListener);
            case RequestType.REQUEST_LATE_EARLY:
                ItemListRequestLeaveBinding itemListRequestLeaveBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                R.layout.item_list_request_leave, parent, false);
                return new RequestLeaveViewHolder(itemListRequestLeaveBinding, mItemClickListener);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RequestType.REQUEST_OVERTIME:
                RequestOverTimeViewHolder requestOverTimeViewHolder =
                        (RequestOverTimeViewHolder) holder;
                requestOverTimeViewHolder.bind(mRequests.get(position));
                break;
            case RequestType.REQUEST_OFF:
                RequestOffViewHolder requestOffViewHolder = (RequestOffViewHolder) holder;
                requestOffViewHolder.bind(mRequests.get(position));
                break;
            case RequestType.REQUEST_LATE_EARLY:
                RequestLeaveViewHolder requestLeaveViewHolder = (RequestLeaveViewHolder) holder;
                requestLeaveViewHolder.bind(mRequests.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mRequestType;
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    void updateData(List<Request> requests) {
        if (requests == null) {
            return;
        }
        mRequests.clear();
        mRequests.addAll(requests);
        notifyDataSetChanged();
    }

    void setItemClickListener(OnRecyclerViewItemClickListener<Request> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    private static class RequestOverTimeViewHolder extends RecyclerView.ViewHolder {
        private ItemListRequestOvertimeBinding mBinding;
        private OnRecyclerViewItemClickListener<Request> mItemClickListener;

        RequestOverTimeViewHolder(ItemListRequestOvertimeBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Request> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(Request request) {
            mBinding.setViewModel(new ItemListRequestViewModel(request, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    private static class RequestOffViewHolder extends RecyclerView.ViewHolder {
        private ItemListRequestOffBinding mBinding;
        private OnRecyclerViewItemClickListener<Request> mItemClickListener;

        RequestOffViewHolder(ItemListRequestOffBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Request> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(Request request) {
            mBinding.setViewModel(new ItemListRequestViewModel(request, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }

    private static class RequestLeaveViewHolder extends RecyclerView.ViewHolder {
        private ItemListRequestLeaveBinding mBinding;
        private OnRecyclerViewItemClickListener<Request> mItemClickListener;

        RequestLeaveViewHolder(ItemListRequestLeaveBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Request> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(Request request) {
            mBinding.setViewModel(new ItemListRequestViewModel(request, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }
}
