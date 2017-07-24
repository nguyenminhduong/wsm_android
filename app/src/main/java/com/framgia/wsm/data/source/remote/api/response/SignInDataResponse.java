package com.framgia.wsm.data.source.remote.api.response;

import com.framgia.wsm.data.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duong on 6/6/2017.
 */

public class SignInDataResponse {
    @Expose
    @SerializedName("user_info")
    private User mUser;
    @Expose
    @SerializedName("authen_token")
    private String mAuthenToken;
    @Expose
    @SerializedName("is_manager")
    private boolean mIsManager;

    public String getAuthenToken() {
        return mAuthenToken;
    }

    public void setAuthenToken(String authenToken) {
        mAuthenToken = authenToken;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public boolean isManager() {
        return mIsManager;
    }

    public void setManager(boolean manager) {
        mIsManager = manager;
    }
}
