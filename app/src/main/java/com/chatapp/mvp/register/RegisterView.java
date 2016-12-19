package com.chatapp.mvp.register;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.LogInModel;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface RegisterView extends BaseView {
    void onRegisterSuccess();
    void onRegisterError();
    void onLoginSuccess(LogInModel logInModel);
    void onLoginError();
    void onGetVerifyCodeSuccess();
    void onGetVerifyCodeFail();
}
