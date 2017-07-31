package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ths on 04/07/2017.
 */

public class Setting extends BaseModel implements Parcelable {

    @Expose
    @SerializedName("workspace_default")
    private int mWorkspaceDefault;
    @Expose
    @SerializedName("group_default")
    private int mGroupDefault;
    @Expose
    @SerializedName("notification_setting")
    private NotificationOrEmailSetting mNotificationSetting;
    @Expose
    @SerializedName("email_setting")
    private NotificationOrEmailSetting mEmailSetting;

    protected Setting(Parcel in) {
        mWorkspaceDefault = in.readInt();
        mGroupDefault = in.readInt();
        mNotificationSetting = in.readParcelable(NotificationOrEmailSetting.class.getClassLoader());
        mEmailSetting = in.readParcelable(NotificationOrEmailSetting.class.getClassLoader());
    }

    public Setting() {
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        @Override
        public Setting createFromParcel(Parcel in) {
            return new Setting(in);
        }

        @Override
        public Setting[] newArray(int size) {
            return new Setting[size];
        }
    };

    public int getWorkspaceDefault() {
        return mWorkspaceDefault;
    }

    public void setWorkspaceDefault(int workspaceDefault) {
        mWorkspaceDefault = workspaceDefault;
    }

    public int getGroupDefault() {
        return mGroupDefault;
    }

    public void setGroupDefault(int groupDefault) {
        mGroupDefault = groupDefault;
    }

    public NotificationOrEmailSetting getNotificationSetting() {
        return mNotificationSetting;
    }

    public void setNotificationSetting(NotificationOrEmailSetting notificationSetting) {
        mNotificationSetting = notificationSetting;
    }

    public NotificationOrEmailSetting getEmailSetting() {
        return mEmailSetting;
    }

    public void setEmailSetting(NotificationOrEmailSetting emailSetting) {
        mEmailSetting = emailSetting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWorkspaceDefault);
        dest.writeInt(mGroupDefault);
        dest.writeParcelable(mNotificationSetting, flags);
        dest.writeParcelable(mEmailSetting, flags);
    }
}
