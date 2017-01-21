package com.chatapp.mvp.userprofile;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UserProfileMvp {
    interface UserProfileInteractor {
        void getUserProfile(String userId, AuthorizeApiCallback<ResponseModel<UserProfileModel>> callback);
    }

    interface UserProfilePresent {
        void getUserProfile(String userId);
    }

    interface UserProfileView extends BaseView {
        void onGetUserProfileSuccess(UserProfileModel userProfileModel);
    }
}
