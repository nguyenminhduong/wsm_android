package com.framgia.wsm.data.source.remote.api.response;

import com.google.gson.annotations.Expose;

/**
 * Created by framgia on 11/05/2017.
 */

public class ErrorResponse {
    @Expose
    private int code;
    @Expose
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
