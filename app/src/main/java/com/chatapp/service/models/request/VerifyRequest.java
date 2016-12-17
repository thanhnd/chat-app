package com.chatapp.service.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class VerifyRequest {
    @SerializedName("code")
    @Expose
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
