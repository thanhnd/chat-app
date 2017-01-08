package com.chatapp.mvp.listfriends;

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

public class PresentImpl implements ListFriendsMvp.Present {

    private WeakReference<ListFriendsMvp.View> view;
    private ListFriendsMvp.Interactor interactor;

    public PresentImpl(ListFriendsMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListFriends() {
        interactor.getListFriends(new ApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListFriendsSuccess(response.getResultSet());
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
}
