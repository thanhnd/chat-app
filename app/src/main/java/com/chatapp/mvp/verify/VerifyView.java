package com.chatapp.mvp.verify;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.LogInModel;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyView extends BaseView {
    void onVerifySuccess();
    void onVerifyError();
    void onLoginSuccess(LogInModel logInModel);
    void onLoginError();
}
