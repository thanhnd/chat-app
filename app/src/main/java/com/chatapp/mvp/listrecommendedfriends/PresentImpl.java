package com.chatapp.mvp.listrecommendedfriends;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresentImpl implements ListRecommendedFriendsMvp.Present {

    private WeakReference<ListRecommendedFriendsMvp.View> view;
    private ListRecommendedFriendsMvp.Interactor interactor;

    public PresentImpl(ListRecommendedFriendsMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListRecommendedFriends() {
        interactor.getListRecommendedFriends(new ApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListRecommendedFriendsSuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<List<UserModel>>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<List<UserModel>>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void acceptFriendRequest(String userId) {
        interactor.acceptFriendRequest(userId, new ApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().onAcceptFriendSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {

            }
        });
    }
}
