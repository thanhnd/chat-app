package com.chatapp.mvp.listfriends;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListFriendsMvp {

    interface Presenter {
        void getListFriends() throws RequireLoginException;
    }

    interface Interactor {
        void getListFriends(ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onGetListFriendsSuccess(List<UserModel> resultSet);

        void navigateToUserProfile(UserModel userModel);
    }
}
