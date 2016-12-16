
package com.chatapp.mvp.signin;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.SignInRequest;
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
        signInInteractor.login(new SignInRequest(), new ApiCallback<SignInModel>() {
            @Override
            public void onSuccess(SignInModel response) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();
                    loginView.get().onSignInSuccess();
                }
            }

            @Override
            public void onFail(Response<SignInModel> response) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();

                    // Get data response from server
                    SignInModel loginInfo = response.body();

                    // Show error message from server if there is
                    if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getResponseMsg())) {
                        loginView.get().showErrorDialog(loginInfo.getResponseMsg());
                    } else {
                        loginView.get().onSignInError();
                    }
                }
            }

            @Override
            public void onFail(Call<SignInModel> call, Throwable throwable) {
                if (loginView != null) {
                    loginView.get().showErrorDialog();
                    loginView.get().hideProgress();
                }
            }
        });
    }
}
