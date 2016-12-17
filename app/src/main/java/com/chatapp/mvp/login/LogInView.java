package com.chatapp.mvp.login;


import com.chatapp.mvp.base.BaseView;

public interface LogInView extends BaseView {
    void onLogInSuccess();
    void onNotVerify();
    void onLogInError();
}
