package com.chatapp.mvp.listfriends;

import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;


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
        interactor.getListFriends(new BaseApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListFriendsSuccess(response.getResultSet());
                }
            }
        });
    }
}
