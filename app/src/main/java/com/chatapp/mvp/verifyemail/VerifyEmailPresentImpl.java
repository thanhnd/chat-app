package com.chatapp.mvp.verifyemail;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class VerifyEmailPresentImpl implements VerifyEmailPresent {
    private WeakReference<VerifyEmailView> view;
    private VerifyEmailInteractor interactor;

    public VerifyEmailPresentImpl(VerifyEmailView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new VerifyEmailInteractorImpl();
    }
    @Override
    public void submitVerifyForm(String token, VerifyEmailRequest request) {
        interactor.verify(token, request, new ApiCallback<ResponseModel<VerifyModel>>() {
            @Override
            public void onSuccess(ResponseModel<VerifyModel> response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onVerifySuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<VerifyModel>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onLoginError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<VerifyModel>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });
    }

    public void requestLogin(LogInRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.login(request, new ApiCallback<ResponseModel<LogInModel>>() {
            @Override
            public void onSuccess(ResponseModel<LogInModel> response) {
                if (view.get() != null) {
                    view.get().onLoginSuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<LogInModel>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().showErrorDialog();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<LogInModel>> call, Throwable throwable) {
                if (view != null) {
                    view.get().hideProgress();
                    view.get().showErrorDialog();
                }
            }
        });
    }
}
