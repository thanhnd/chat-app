
package com.chatapp.mvp.userprofile;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;

import java.lang.ref.WeakReference;

public class UserProfilePresenterImpl implements UserProfileMvp.UserProfilePresent {

    private WeakReference<UserProfileMvp.UserProfileView> view;
    private UserProfileMvp.UserProfileInteractor interactor;

    public UserProfilePresenterImpl(UserProfileMvp.UserProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new UserProfileInteractorImpl();
    }

    @Override
    public void getUserProfile(String userId) throws RequireLoginException {
        interactor.getUserProfile(userId, new BaseApiCallback<ResponseModel<UserProfileModel>>() {
            @Override
            public void onSuccess(ResponseModel<UserProfileModel> response) {
                if (view.get() != null) {
                    view.get().onGetUserProfileSuccess(response.getResultSet());
                }
            }

        });
    }

    @Override
    public void addFavorite(String userId) throws RequireLoginException {
        interactor.addFavorite(userId, new BaseApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onAddFavoriteSuccess();
                }
            }
        });
    }


    @Override
    public void requestAddFriend(String userId, String noted) throws RequireLoginException {
        interactor.requestAddFriend(userId, noted, new BaseApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onRequestAddFriendSuccess();
                }
            }
        });
    }

    @Override
    public void removeFavorite(String userId) throws RequireLoginException {
        interactor.removeFavorite(userId, new BaseApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onRemoveFavoriteSuccess();
                }
            }
        });
    }
}
