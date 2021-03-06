package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListRecommendedFriendsMvp {

    interface Presenter {
        void getListRecommendedFriends() throws RequireLoginException;

        void acceptFriendRequest(String userId) throws RequireLoginException;

        void deleteRecommends(Set<UserModel> userModels) throws RequireLoginException;
    }

    interface Interactor {

        void getListRecommendedFriends(ApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException;

        void acceptFriendRequest(String userId, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;

        void deleteRecomments(List<Map<String, String>> list, BaseApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;
    }

    interface View extends BaseView {
        void onGetListRecommendedFriendsSuccess(List<UserModel> resultSet);
        void onAcceptFriendSuccess();
        void navigateToUserProfile(UserModel userModel);

        void onDeleteRecommendsSuccess();
    }

}
