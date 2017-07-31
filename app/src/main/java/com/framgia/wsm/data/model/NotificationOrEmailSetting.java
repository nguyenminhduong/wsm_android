package com.framgia.wsm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ThS on 7/28/2017.
 */

public class NotificationOrEmailSetting extends BaseModel implements Parcelable {

    @Expose
    @SerializedName("Workspace")
    private boolean mWorkspace;
    @Expose
    @SerializedName("Group")
    private boolean mGroup;
    @Expose
    @SerializedName("UserSpecialType")
    private boolean mUserSpecialType;

    protected NotificationOrEmailSetting(Parcel in) {
        mWorkspace = in.readByte() != 0;
        mGroup = in.readByte() != 0;
        mUserSpecialType = in.readByte() != 0;
    }

    public static final Creator<NotificationOrEmailSetting> CREATOR =
            new Creator<NotificationOrEmailSetting>() {
                @Override
                public NotificationOrEmailSetting createFromParcel(Parcel in) {
                    return new NotificationOrEmailSetting(in);
                }

                @Override
                public NotificationOrEmailSetting[] newArray(int size) {
                    return new NotificationOrEmailSetting[size];
                }
            };

    public boolean isWorkspace() {
        return mWorkspace;
    }

    public void setWorkspace(boolean workspace) {
        mWorkspace = workspace;
    }

    public boolean isGroup() {
        return mGroup;
    }

    public void setGroup(boolean group) {
        mGroup = group;
    }

    public boolean isUserSpecialType() {
        return mUserSpecialType;
    }

    public void setUserSpecialType(boolean userSpecialType) {
        mUserSpecialType = userSpecialType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mWorkspace ? 1 : 0));
        dest.writeByte((byte) (mGroup ? 1 : 0));
        dest.writeByte((byte) (mUserSpecialType ? 1 : 0));
    }
}
