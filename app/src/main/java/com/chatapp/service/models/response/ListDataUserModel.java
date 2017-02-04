package com.chatapp.service.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class ListDataUserModel {
    @SerializedName("total_page")
    @Expose
    private int totalPage;

    @SerializedName("data")
    @Expose
    private List<UserModel> userModels;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<UserModel> getUserModels() {
        return userModels;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }
}
