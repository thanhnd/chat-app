
package com.chatapp.mvp.login;

import android.os.Bundle;

import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.ChatHelper;
import com.chatapp.utils.Log;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import java.lang.ref.WeakReference;

public class LogInPresenterImpl implements LoginMvp.LogInPresenter {

    private WeakReference<LoginMvp.LogInView> view;
    private LoginMvp.LogInInteractor interactor;

    private ResponseModel<LogInModel> responseModel;
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
                LogInModel logInModel = responseModel.getResultSet();
                AccountUtils.setLogInModel(logInModel);
                final QBUser user = new QBUser(logInModel.getLogin(), logInModel.getPass());

                LogInPresenterImpl.this.responseModel = responseModel;
                loginQuickblox(user);
            }
        });
    }

    private void loginQuickblox(final QBUser user) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        ChatHelper.getInstance().login(user, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle bundle) {
                SharedPreferencesUtil.saveQbUser(user);

                if (view.get() != null) {
                    view.get().hideProgress();
                }

                if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_ACTIVE) {
                    view.get().onNotVerify();
                } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_NOT_CONFIRM) {
                    view.get().onNotConfirm();
                } else if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                    view.get().onLogInSuccess();
                }
            }

            @Override
            public void onError(QBResponseException e) {
                if (view.get() != null) {
                    view.get().hideProgress();
                }
                Log.e(e.toString());
            }
        });
    }
}
