package com.chatapp.mvp.listchat;

import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresenterImpl implements ListFavorites.Presenter {

    private WeakReference<ListFavorites.View> view;
    private ListFavorites.Interactor interactor;

    public PresenterImpl(ListFavorites.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListFavorites() {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getListFavorites(new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListFavoritesSuccess(response.getResultSet());
                }
            }
        });
    }
}
