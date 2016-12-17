package com.chatapp.mvp.verifyemail;

import com.chatapp.service.models.request.VerifyRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyEmailPresent {
    void submitVerifyForm(VerifyRequest request);

}
