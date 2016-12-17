package com.chatapp.mvp.verifyemail;

import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyEmailPresent {
    void submitVerifyForm(String token, VerifyEmailRequest request);
    void requestLogin(LogInRequest request);

}
