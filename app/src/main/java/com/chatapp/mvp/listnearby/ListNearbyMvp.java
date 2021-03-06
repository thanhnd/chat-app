package com.chatapp.mvp.listnearby;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListNearbyMvp {

    interface Presenter {
        void getListNearBy() throws RequireLoginException;
        void getMyProfile() throws RequireLoginException;
    }

    interface Interactor {
        void getListNearBy(ListNearbyRequest request, ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onGetListNearbySuccess(List<UserModel> resultSet);
        void onGetMyProfileSuccess(MyProfileModel myProfileModel);
        void navigateToMyProfile();
        void navigateToUserProfile(UserModel userModel);
    }
}
