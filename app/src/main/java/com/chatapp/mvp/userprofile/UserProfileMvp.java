package com.chatapp.mvp.userprofile;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UserProfileMvp {
    interface UserProfileInteractor {

        void getUserProfile(String userId, ApiCallback<ResponseModel<UserProfileModel>> callback);

        void addFavorite(String userId, ApiCallback<ResponseModel<Object>> callback);

        void requestAddFriend(String userId, String noted, ApiCallback<ResponseModel<Object>> callback);

        void removeFavorite(String userId, ApiCallback<ResponseModel<Object>> ApiCallback);
    }

    interface UserProfilePresent {

        void getUserProfile(String userId);

        void addFavorite(String userId);

        void requestAddFriend(String userId, String noted);

        void removeFavorite(String userId);
    }

    interface UserProfileView extends BaseView {

        void onGetUserProfileSuccess(UserProfileModel userProfileModel);

        void onAddFavoriteSuccess();

        void onRequestAddFriendSuccess();

        void onRequestAddFriendFail();

        void onRemoveFavoriteSuccess();

        void onAddFavoriteFail();

        void onRemoveFavoriteFail();
    }
}
