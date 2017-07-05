package com.framgia.wsm.data.source.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 05/07/2017.
 */

public class ChangePasswordRequest extends BaseRequest {
    @Expose
    @SerializedName("change_password")
    private ChangePassword mChangePassword;

    public ChangePasswordRequest(String currentPassword, String newPassword) {
        mChangePassword = new ChangePassword(currentPassword, newPassword);
    }

    private static final class ChangePassword {
        @Expose
        @SerializedName("current_password")
        private String mCurrentPassword;
        @Expose
        @SerializedName("new_password")
        private String mNewPassword;

        private ChangePassword(String currentPassword, String newPassword) {
            mCurrentPassword = currentPassword;
            mNewPassword = newPassword;
        }

        public String getCurrentPassword() {
            return mCurrentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            mCurrentPassword = currentPassword;
        }

        public String getNewPassword() {
            return mNewPassword;
        }

        public void setNewPassword(String newPassword) {
            mNewPassword = newPassword;
        }
    }
}
