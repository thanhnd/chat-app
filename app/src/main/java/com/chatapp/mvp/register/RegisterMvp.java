package com.chatapp.mvp.register;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface RegisterMvp {
    interface RegisterInteractor {
        void login(LogInRequest request,
                   ApiCallback<ResponseModel<LogInModel>> loginCallback);
        void register(RegisterRequest request,
                      ApiCallback<ResponseModel<RegisterModel>> callback);
        void getVerifyCode(ApiCallback<ResponseModel<Object>> callback);
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface RegisterPresent {
        void requestLogin(LogInRequest request);
        void submitRegisterForm(RegisterRequest request);
        void getVerifyCode();
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface RegisterView extends BaseView {
        void onRegisterSuccess();
        void onRegisterError();
        void onLoginSuccess(LogInModel logInModel);
        void onLoginError();
        void onGetVerifyCodeSuccess();
        void onGetVerifyCodeFail();
    }
}
