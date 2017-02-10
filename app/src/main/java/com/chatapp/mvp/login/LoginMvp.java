package com.chatapp.mvp.login;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface LoginMvp {
    interface LogInInteractor {

        void login(LogInRequest logInRequest, BaseApiCallback<ResponseModel<LogInModel>> callback);
    }

    interface LogInView extends BaseView {
        void onLogInSuccess();
        void onNotVerify();
        void onLogInError();
        void onNotConfirm();

        void sendVerifyCodeForgotPasswordWithPhoneSuccess();
    }

    interface LogInPresenter {
        void login(LogInRequest request);

        void sendVerifyCodeForgotPasswordWithPhone(String phone);
    }
}
