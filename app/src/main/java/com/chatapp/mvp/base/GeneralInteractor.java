package com.chatapp.mvp.base;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public interface GeneralInteractor {
    void getMyProfile(ApiCallback<ResponseModel<MyProfileModel>> callback) throws RequireLoginException;
}
