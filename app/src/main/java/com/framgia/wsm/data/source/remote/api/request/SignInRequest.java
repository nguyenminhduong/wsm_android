package com.framgia.wsm.data.source.remote.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Duong on 6/6/2017.
 */

public class SignInRequest extends BaseRequest {
    @Expose
    @SerializedName("sign_in")
    private SignIn mSignIn;

    public SignInRequest(String username, String password) {
        mSignIn = new SignIn(username, password);
    }

    private static class SignIn {
        @Expose
        @SerializedName("email")
        private String mEmail;
        @Expose
        @SerializedName("password")
        private String mPassword;

        private SignIn(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public String getPassword() {
            return mPassword;
        }

        public void setPassword(String password) {
            mPassword = password;
        }
    }
}
