package com.framgia.wsm.screen.notification;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;
import com.framgia.wsm.data.model.Notification;
import com.framgia.wsm.screen.BaseRecyclerViewAdapter;
import com.framgia.wsm.utils.common.DateTimeUtils;
import com.framgia.wsm.utils.common.TimeAgo;

public class ItemNotificationViewModel extends BaseObservable {
    private final BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification>
            mItemClickListener;
    private Notification mNotification;
    private Context mContext;

    public ItemNotificationViewModel(Context context, Notification notification,
            BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener<Notification> listener) {
        mContext = context;
        mNotification = notification;
        mItemClickListener = listener;
    }

    public String getContent() {
        return mNotification.getMessage();
    }

    public String getDate() {
        return TimeAgo.toDuration(DateTimeUtils.getTimeAgo(mNotification.getCreatedAt()), mContext);
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
