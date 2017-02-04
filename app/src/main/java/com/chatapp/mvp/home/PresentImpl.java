package com.chatapp.mvp.home;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresentImpl implements HomeMvp.Present {

    private WeakReference<HomeMvp.View> view;
    private HomeMvp.Interactor interactor;

    public PresentImpl(HomeMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
    }

    @Override
    public void updateLocation(double latitude, double longitude) {
        interactor.updatLocation(latitude, longitude, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {

            }

            @Override
            public void onTokenExpired() {
            }
        });
    }
}
