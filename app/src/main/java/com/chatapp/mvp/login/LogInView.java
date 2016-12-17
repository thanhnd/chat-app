package com.chatapp.mvp.login;


import com.chatapp.mvp.base.BaseView;

public interface LogInView extends BaseView {
    void onSignInSuccess();

    void onSignInError();
}
