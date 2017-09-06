package com.framgia.wsm.screen.notification;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;
import com.framgia.wsm.R;
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

    public int getIcon() {
        switch (mNotification.getTrackableType()) {
            case NotificationViewModel.TrackableType.GROUP:
                return R.drawable.ic_group_notification;
            case NotificationViewModel.TrackableType.REQUEST_LEAVE:
                return R.drawable.ic_request_leave;
            case NotificationViewModel.TrackableType.REQUEST_OFF:
                return R.drawable.ic_request_off;
            case NotificationViewModel.TrackableType.REQUEST_OT:
                return R.drawable.ic_request_overtime;
            case NotificationViewModel.TrackableType.WORKSPACE:
                return R.drawable.ic_workspace_notification;
            case NotificationViewModel.TrackableType.USER:
                return R.drawable.ic_personal_issue;
            case NotificationViewModel.TrackableType.USER_SPECIAL_TYPE:
                return R.drawable.ic_user_type;
            case NotificationViewModel.TrackableType.LOCK_TIME_SHEET:
                return R.drawable.ic_lock_time_sheet;
            default:
                return R.drawable.ic_notifications;
        }
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
