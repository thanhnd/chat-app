
package com.chatapp.mvp.login;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class LogInPresenterImpl implements LogInPresenter {

    private WeakReference<LogInView> loginView;
    private LogInInteractor logInInteractor;

    public LogInPresenterImpl(LogInView logInView) {
        this.loginView = new WeakReference<>(logInView);
        this.logInInteractor = new LogInInteractorImpl();
    }

    @Override
    public void login(LogInRequest request) {
        if (loginView.get() != null) {
            loginView.get().showProgress();
        }
        logInInteractor.login(request, new ApiCallback<ResponseModel<LogInModel>>() {
            @Override
            public void onSuccess(ResponseModel<LogInModel> responseModel) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();
                    AccountUtils.setLogInModel(responseModel.getResultSet());
                    if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_ACTIVE) {
                        loginView.get().onNotVerify();
                    } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_CONFIRM) {
                        loginView.get().onNotConfirm();
                    } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        loginView.get().onLogInSuccess();
                    }

                }
            }

            @Override
            public void onFail(Response<ResponseModel<LogInModel>> response) {
                if (loginView.get() != null) {
                    loginView.get().hideProgress();

                    // Get data response from server
                    ResponseModel<LogInModel> responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        loginView.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        loginView.get().onLogInError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<LogInModel>> call, Throwable throwable) {
                if (loginView != null) {
                    loginView.get().showErrorDialog();
                    loginView.get().hideProgress();
                }
            }
        });
    }
}
