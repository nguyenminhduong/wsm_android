package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.BaseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 29/06/2017.
 */

public class UpdateProfileRequest extends BaseModel {
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("birthday")
    private String mBirthday;
    @Expose
    @SerializedName("avatar")
    private String Avatar;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
