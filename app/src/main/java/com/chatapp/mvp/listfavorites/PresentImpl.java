package com.chatapp.mvp.listfavorites;

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

public class PresentImpl implements ListFavoritesMvp.Present {

    private WeakReference<ListFavoritesMvp.View> view;
    private ListFavoritesMvp.Interactor interactor;

    public PresentImpl(ListFavoritesMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListFavorites(boolean isFirstPage) {
        interactor.getListFavorites(new ApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListFavoritesSuccess(response.getResultSet());
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
