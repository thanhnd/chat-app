package com.chatapp.mvp.listchat;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresenterImpl implements ListChatMvp.Presenter {

    private WeakReference<ListChatMvp.View> view;
    private ListChatMvp.Interactor interactor;

    public PresenterImpl(ListChatMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListChat() throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getListChat(new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListChatSuccess(response.getResultSet());
                }
            }
        });
    }
}
