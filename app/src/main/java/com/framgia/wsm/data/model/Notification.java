package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhd on 6/30/2017.
 */

public class Notification extends BaseModel {
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("trackable_type")
    @Expose
    private String mTrackableType;
    @SerializedName("read")
    @Expose
    private Boolean isRead;
    @SerializedName("message")
    @Expose
    private String mMessage;
    @SerializedName("permission")
    @Expose
    private int mPermission;
    @SerializedName("created_at")
    @Expose
    private String mCreatedAt;
    private String mIcon;
    @SerializedName("trackable_status")
    @Expose
    private String mTrachableStatus;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getTrackableType() {
        return mTrackableType;
    }

    public void setTrackableType(String trackableType) {
        mTrackableType = trackableType;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public int getPermission() {
        return mPermission;
    }

    public void setPermission(int permission) {
        mPermission = permission;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTrachableStatus() {
        return mTrachableStatus;
    }

    public void setTrachableStatus(String trachableStatus) {
        mTrachableStatus = trachableStatus;
    }
}
