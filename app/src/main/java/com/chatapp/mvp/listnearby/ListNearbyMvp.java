package com.chatapp.mvp.listnearby;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListNearbyMvp {

    interface Present {
        void getListNearBy();
        void getMyProfile();
    }

    interface Interactor {
        void getListNearBy(ListNearbyRequest request, AuthorizeApiCallback<ResponseModel<List<UserModel>>> callback);
    }

    interface View extends BaseView {
        void onGetListNearbySuccess(List<UserModel> resultSet);
        void onGetMyProfileSuccess(MyProfileModel myProfileModel);
        void navigateToMyProfile();
        void navigateToUserProfile(UserModel userModel);
    }
}
