package com.framgia.wsm.screen.notification;

import android.databinding.BaseObservable;
import android.view.View;
import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;

public class ItemNotificationViewModel extends BaseObservable {
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification>
            mItemClickListener;
    private Notification mNotification;

    public ItemNotificationViewModel(Notification notification,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification> listener) {
        mNotification = notification;
        mItemClickListener = listener;
    }

    public String getContent() {
        return mNotification.getMessage();
    }

    public String getDate() {
        return mNotification.getCreatedAt();
    }

    public String getIcon() {
        return mNotification.getIcon() == null ? "" : mNotification.getIcon();
    }

    public boolean isRead() {
        return mNotification.getRead();
    }

    public void onItemClicked(View view) {
        if (mItemClickListener == null) {
            return;
        }
        mItemClickListener.onItemRecyclerViewClick(mNotification);
    }
}
