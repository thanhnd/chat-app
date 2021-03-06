package com.chatapp.service.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class UserRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("noted")
    @Expose
    private String noted;

    public UserRequest(String userId) {
        this.userId = userId;
    }
    public UserRequest(String userId, String noted) {
        this.userId = userId;
        this.noted = noted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
