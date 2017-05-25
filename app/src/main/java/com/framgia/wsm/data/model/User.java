package com.framgia.wsm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tri on 24/05/2017.
 */

public class User {
    @Expose
    @SerializedName("id")
    private Integer mId;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("email")
    private String mEmail;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
