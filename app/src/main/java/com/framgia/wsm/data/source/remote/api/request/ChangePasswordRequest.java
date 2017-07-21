package com.framgia.wsm.data.source.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ASUS on 05/07/2017.
 */

public class ChangePasswordRequest extends BaseRequest {
    @Expose
    @SerializedName("user")
    private ChangePassword mChangePassword;

    public ChangePasswordRequest(String currentPassword, String newPassword,
            String confirmPassword) {
        mChangePassword = new ChangePassword(currentPassword, newPassword, confirmPassword);
    }

    public ChangePassword getChangePassword() {
        return mChangePassword;
    }

    public void setChangePassword(ChangePassword changePassword) {
        mChangePassword = changePassword;
    }

    private static final class ChangePassword {
        @Expose
        @SerializedName("current_password")
        private String mCurrentPassword;
        @Expose
        @SerializedName("password")
        private String mNewPassword;
        @Expose
        @SerializedName("password_confirmation")
        private String mPasswordConfirm;

        private ChangePassword(String currentPassword, String newPassword, String confirmPassword) {
            mCurrentPassword = currentPassword;
            mNewPassword = newPassword;
            mPasswordConfirm = confirmPassword;
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

        public String getPasswordConfirm() {
            return mPasswordConfirm;
        }

        public void setPasswordConfirm(String passwordConfirm) {
            mPasswordConfirm = passwordConfirm;
        }
    }
}
