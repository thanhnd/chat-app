package com.chatapp.mvp.base;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public interface GeneralInteractor {
    void getMyProfile(AuthorizeApiCallback<ResponseModel<MyProfileModel>> callback);
}
