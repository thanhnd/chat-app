package com.chatapp.mvp.verify;

import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyPresent {
    void submitVerifyForm(String token, VerifyEmailRequest request);
    void requestLogin(LogInRequest request);

}
