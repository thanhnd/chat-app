package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.util.List;
import java.util.Set;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListRecommendedFriendsMvp {

    interface Presenter {
        void getListRecommendedFriends();

        void acceptFriendRequest(String userId);

        void delete(Set<UserModel> userModels);
    }

    interface Interactor {

        void getListRecommendedFriends(ApiCallback<ResponseModel<List<UserModel>>> callback);

        void acceptFriendRequest(String userId, ApiCallback<ResponseModel<Object>> callback);
    }

    interface View extends BaseView {
        void onGetListRecommendedFriendsSuccess(List<UserModel> resultSet);
        void onAcceptFriendSuccess();
        void navigateToUserProfile(UserModel userModel);
    }

}
