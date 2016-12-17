package com.chatapp.mvp.login;


import com.chatapp.service.models.request.LogInRequest;

public interface LogInPresenter {
    void login(LogInRequest request);
}