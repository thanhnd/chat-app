
package com.chatapp.mvp.signin;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.SignInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.SignInModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class SignInPresenterImpl implements SignInPresenter {

    private WeakReference<SignInView> loginView;
    private SignInInteractor signInInteractor;

    public SignInPresenterImpl(SignInView signInView) {
        this.loginView = new WeakReference<>(signInView);
        this.signInInteractor = new SignInInteractorImpl();
    }

    @Override
    public void login(SignInRequest request) {
        if (loginView.get() != null) {
            loginView.get().showProgress();
        }
        signInInteractor.login(request, new ApiCallback<ResponseModel<SignInModel>>() {
            @Override
            public void onSuccess(ResponseModel<SignInModel> response) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();
                    loginView.get().onSignInSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<SignInModel>> response) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();

                    // Get data response from server
                    ResponseModel<SignInModel> responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        loginView.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        loginView.get().onSignInError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<SignInModel>> call, Throwable throwable) {
                if (loginView != null) {
                    loginView.get().showErrorDialog();
                    loginView.get().hideProgress();
                }
            }
        });
    }
}
