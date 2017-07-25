package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.Notification;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by minhd on 7/25/2017.
 */

public class NotificationResponse {
    @SerializedName("notifications")
    @Expose
    private List<Notification> mNotifications;
    @SerializedName("count_unread_notifications")
    @Expose
    private int mCountUnreadNotifications;

    public List<Notification> getNotifications() {
        return mNotifications;
    }

    public void setNotifications(List<Notification> notifications) {
        mNotifications = notifications;
    }

    public int getCountUnreadNotifications() {
        return mCountUnreadNotifications;
    }

    public void setCountUnreadNotifications(int countUnreadNotifications) {
        mCountUnreadNotifications = countUnreadNotifications;
    }
}
