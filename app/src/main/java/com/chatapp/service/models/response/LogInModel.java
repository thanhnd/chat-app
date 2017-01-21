package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class LogInModel implements Serializable {
    private static final int NOT_VERIFY = 0;
    private static final int VERIFIED = 1;
    private static final int CONFIRM = 2;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("is_active")
    @Expose
    private int isActive;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public boolean isConfirm() {
        return isActive == CONFIRM;
    }

    public boolean isVerified() {
        return isActive == VERIFIED;
    }

    public boolean isNotVerify() {
        return isActive == NOT_VERIFY;
    }

}
