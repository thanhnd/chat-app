package com.chatapp.service;

import android.text.TextUtils;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.Log;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 2/9/17.
 */
public abstract class BaseApiCallback<T extends ResponseModel> implements ApiCallback<T> {

    private WeakReference<BaseView> view;

    public BaseApiCallback(BaseView view) {
        this.view = new WeakReference<>(view);
    }

    public BaseApiCallback() {
        this(null);
    }

    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        if (view.get() != null) {
            view.get().hideProgress();
        }
        T responseModel = response.body();
        if (response.isSuccessful() && responseModel != null) {
            if (responseModel.isTokenExpired()) {

                onTokenExpired();
                return;

            } else if (responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_SUCCESS
                    || responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_NOT_CONFIRM
                    || responseModel.getResponseCd() == RegisterModel.RESPONSE_CD_NOT_ACTIVE) {

                onSuccess(responseModel);
                return;
            }
        }
        onFailure(response);
    }

    @Override
    public final void onFailure(Response<T> response) {
        if (view.get() != null) {
            view.get().hideProgress();

            // Get data response from server
            T responseModel = response.body();

            // Show error message from server if there is
            if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                view.get().showErrorDialog(responseModel.getResponseMsg());
            } else {
                view.get().showErrorDialog();
            }
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        Log.e(t);
        if (view.get() != null) {
            view.get().hideProgress();
            view.get().showErrorDialog();
        }
    }

    @Override
    public final void onTokenExpired() {
        if (view.get() != null) {
            view.get().hideProgress();
            view.get().onTokenExpired();
        }
    }

    @Override
    public void onRequiredAuthorization() {
        if (view.get() != null) {
            view.get().hideProgress();
            view.get().onRequiredLogin();
        }
    }
}
