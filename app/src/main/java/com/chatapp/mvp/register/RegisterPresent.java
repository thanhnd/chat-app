package com.chatapp.mvp.register;

import com.chatapp.service.models.request.RegisterRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface RegisterPresent {
    void submitRegisterFrom(RegisterRequest request);
}
