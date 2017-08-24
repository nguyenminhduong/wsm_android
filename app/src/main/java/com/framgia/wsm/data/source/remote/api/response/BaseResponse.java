package com.framgia.wsm.data.source.remote.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by le.quang.dao on 10/03/2017.
 */

public class BaseResponse<T> {
    @Expose
    @SerializedName("code")
    private int mCode;
    @Expose
    @SerializedName("messages")
    private String mMessages;
    @Expose
    @SerializedName("data")
    T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse() {
    }

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessages;
    }

    public T getData() {
        return data;
    }
}
