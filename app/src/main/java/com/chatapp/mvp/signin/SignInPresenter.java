package com.chatapp.mvp.signin;


import com.chatapp.service.models.request.SignInRequest;

public interface SignInPresenter {
    void login(SignInRequest request);
}