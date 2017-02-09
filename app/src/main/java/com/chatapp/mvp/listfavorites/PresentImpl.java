package com.chatapp.mvp.listfavorites;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresentImpl implements ListFavoritesMvp.Present {

    private WeakReference<ListFavoritesMvp.View> view;
    private ListFavoritesMvp.Interactor interactor;

    public PresentImpl(ListFavoritesMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListFavorites(boolean isFirstPage) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getListFavorites(new BaseApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListFavoritesSuccess(response.getResultSet());
                }
            }
        });
    }
}
