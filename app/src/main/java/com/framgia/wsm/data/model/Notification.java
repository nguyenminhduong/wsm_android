package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhd on 6/30/2017.
 */

public class Notification extends BaseModel {
    @Expose
    @SerializedName("id")
    private int mId;
    @Expose
    @SerializedName("date")
    private String mDate;
    @Expose
    @SerializedName("content")
    private String mContent;
    @Expose
    @SerializedName("status")
    private int mStatus;
    @Expose
    @SerializedName("icon")
    private String mIcon;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
