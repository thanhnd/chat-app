package com.chatapp.mvp.signin;


import com.chatapp.mvp.base.BaseView;

public interface SignInView extends BaseView {
    void onSignInSuccess();

    void onSignInError();
}
