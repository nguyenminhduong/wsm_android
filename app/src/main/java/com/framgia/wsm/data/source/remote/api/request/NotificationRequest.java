package com.framgia.wsm.data.source.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minhd on 7/25/2017.
 */

public class NotificationRequest extends BaseRequest {
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("read")
    @Expose
    private Boolean isRead;
    @SerializedName("read_all")
    @Expose
    private Boolean isReadAll;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Boolean getReadAll() {
        return isReadAll;
    }

    public void setReadAll(Boolean readAll) {
        isReadAll = readAll;
    }
}
