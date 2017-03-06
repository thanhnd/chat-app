package com.chatapp.mvp.userprofile;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UserProfileMvp {
    interface UserProfileInteractor {

        void getUserProfile(String userId, ApiCallback<ResponseModel<UserProfileModel>> callback) throws RequireLoginException;

        void addFavorite(String userId, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;

        void requestAddFriend(String userId, String noted, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;

        void removeFavorite(String userId, ApiCallback<ResponseModel<Object>> ApiCallback) throws RequireLoginException;
    }

    interface UserProfilePresenter {

        void getUserProfile(String userId) throws RequireLoginException;

        void addFavorite(String userId) throws RequireLoginException;

        void requestAddFriend(String userId, String noted) throws RequireLoginException;

        void removeFavorite(String userId) throws RequireLoginException;

        CountryModel getCountry(int countryId);
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
