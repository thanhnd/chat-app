package com.chatapp.mvp.register;

import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface RegisterPresent {
    void requestLogin(LogInRequest request);
    void submitRegisterForm(RegisterRequest request);
    void getVerifyCode();
}
