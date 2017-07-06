package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.ErrorMessage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Locale;

/**
 * Created by framgia on 11/05/2017.
 */

public class ErrorResponse {
    @Expose
    private int code;
    @Expose
    private String messages;

    @Expose
    @SerializedName("data")
    private ErrorMessage mErrorMessage;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        if (mErrorMessage != null) {
            return messages.toUpperCase(Locale.getDefault())
                    + "\n"
                    + mErrorMessage.getMessageList();
        }
        return messages;
    }
}
