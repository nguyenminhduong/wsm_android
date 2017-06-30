package com.framgia.wsm.screen.notification;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.framgia.wsm.R;
import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.databinding.ItemNotificationBinding;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter
        extends BaseRecyclerViewAdapter<NotificationAdapter.ItemViewHolder> {
    private final List<Notification> mNotifications;
    private OnRecyclerViewItemClickListener<Notification> mItemClickListener;

    public NotificationAdapter(@NonNull Context context) {
        super(context);
        mNotifications = new ArrayList<>();
    }

    @Override
    public NotificationAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNotificationBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_notification, parent, false);
        return new NotificationAdapter.ItemViewHolder(binding, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ItemViewHolder holder, int position) {
        holder.bind(mNotifications.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public void updateData(List<Notification> notifications) {
        if (notifications == null) {
            return;
        }
        mNotifications.clear();
        mNotifications.addAll(notifications);
        notifyDataSetChanged();
    }

    void setItemClickListener(OnRecyclerViewItemClickListener<Notification> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final OnRecyclerViewItemClickListener<Notification> mItemClickListener;
        private ItemNotificationBinding mBinding;

        ItemViewHolder(ItemNotificationBinding binding,
                BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification> listener) {
            super(binding.getRoot());
            mBinding = binding;
            mItemClickListener = listener;
        }

        void bind(Notification notification) {
            mBinding.setViewModel(new ItemNotificationViewModel(notification, mItemClickListener));
            mBinding.executePendingBindings();
        }
    }
}
