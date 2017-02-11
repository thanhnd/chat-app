
package com.chatapp.mvp.login;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LogInPresenterImpl implements LoginMvp.LogInPresenter {

    private WeakReference<LoginMvp.LogInView> view;
    private LoginMvp.LogInInteractor interactor;

    public LogInPresenterImpl(LoginMvp.LogInView logInView) {
        this.view = new WeakReference<>(logInView);
        this.interactor = new LogInInteractorImpl();
    }

    @Override
    public void login(LogInRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.login(request, new BaseApiCallback<ResponseModel<LogInModel>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<LogInModel> responseModel) {
                AccountUtils.setLogInModel(responseModel.getResultSet());
                if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_ACTIVE) {
                    view.get().onNotVerify();
                } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_CONFIRM) {
                    view.get().onNotConfirm();
                } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                    view.get().onLogInSuccess();
                }
            }
        });
    }
}
