package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 21/06/2017.
 */

public class UserProfileResponse {
    @Expose
    @SerializedName("user")
    private User mUser;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
