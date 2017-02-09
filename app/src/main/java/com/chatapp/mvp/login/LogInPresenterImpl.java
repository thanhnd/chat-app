
package com.chatapp.mvp.login;

import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

public class LogInPresenterImpl implements LoginMvp.LogInPresenter {

    private WeakReference<LoginMvp.LogInView> loginView;
    private LoginMvp.LogInInteractor logInInteractor;

    public LogInPresenterImpl(LoginMvp.LogInView logInView) {
        this.loginView = new WeakReference<>(logInView);
        this.logInInteractor = new LogInInteractorImpl();
    }

    @Override
    public void login(LogInRequest request) {
        if (loginView.get() != null) {
            loginView.get().showProgress();
        }
        logInInteractor.login(request, new BaseApiCallback<ResponseModel<LogInModel>>() {
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
        });
    }
}
