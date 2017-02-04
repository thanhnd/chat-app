
package com.chatapp.mvp.userprofile;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserProfileModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class UserProfilePresenterImpl implements UserProfileMvp.UserProfilePresent {

    private WeakReference<UserProfileMvp.UserProfileView> view;
    private UserProfileMvp.UserProfileInteractor interactor;

    public UserProfilePresenterImpl(UserProfileMvp.UserProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new UserProfileInteractorImpl();
    }

    @Override
    public void getUserProfile(String userId) {
        interactor.getUserProfile(userId, new AuthorizeApiCallback<ResponseModel<UserProfileModel>>() {
            @Override
            public void onSuccess(ResponseModel<UserProfileModel> response) {
                if (view.get() != null) {
                    view.get().onGetUserProfileSuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<UserProfileModel>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<UserProfileModel>> call, Throwable throwable) {

            }

            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().onTokenExpired();
                }
            }
        });
    }

    @Override
    public void addUserFavorite(String userId) {
        interactor.addUserFavorite(userId, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onAddUserFavoriteSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {

            }

            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().onTokenExpired();
                }
            }
        });
    }

    @Override
    public void requestAddFriend(String userId, String noted) {
        interactor.requestAddFriend(userId, noted, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onRequestAddFriendSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {
                if (view.get() != null) {
                    view.get().onRequestAddFriendFail();
                }
            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {
                if (view.get() != null) {
                    view.get().onRequestAddFriendFail();
                }
            }

            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().onTokenExpired();
                }
            }
        });
    }
}
