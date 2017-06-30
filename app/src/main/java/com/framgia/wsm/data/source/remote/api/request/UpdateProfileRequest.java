package com.framgia.wsm.data.source.remote.api.request;

import com.framgia.wsm.data.model.BaseModel;
import java.io.File;

/**
 * Created by tri on 29/06/2017.
 */

public class UpdateProfileRequest extends BaseModel {

    private String mName;
    private String mBirthday;
    private File mAvatar;

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

    public File getAvatar() {
        return mAvatar;
    }

    public void setAvatar(File avatar) {
        mAvatar = avatar;
    }
}
