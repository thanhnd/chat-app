
package com.chatapp.mvp.register;

import android.os.Bundle;

import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.ChatHelper;
import com.chatapp.utils.Log;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import java.lang.ref.WeakReference;

public class RegisterPresenterImpl implements RegisterMvp.RegisterPresent {

    private WeakReference<RegisterMvp.RegisterView> view;
    private RegisterMvp.RegisterInteractor interactor;

    private ResponseModel<LogInModel> responseModel;

    public RegisterPresenterImpl(RegisterMvp.RegisterView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new RegisterInteractorImpl();
    }

    @Override
    public void submitRegisterForm(RegisterRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.register(request, new BaseApiCallback<ResponseModel<RegisterModel>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<RegisterModel> response) {
                view.get().onRegisterSuccess();
            }
        });
    }

    @Override
    public void requestLogin(LogInRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.login(request, new BaseApiCallback<ResponseModel<LogInModel>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<LogInModel> responseModel) {
                RegisterPresenterImpl.this.responseModel = responseModel;
                LogInModel logInModel = responseModel.getResultSet();
                AccountUtils.setLogInModel(logInModel);
                final QBUser user = new QBUser(logInModel.getLogin(), logInModel.getPass());

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

                view.get().onLoginSuccess(responseModel.getResultSet());
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

    @Override
    public void getVerifyCode() throws RequireLoginException {

        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getVerifyCode(new BaseApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onGetVerifyCodeSuccess();
                }
            }
        });
    }
}
