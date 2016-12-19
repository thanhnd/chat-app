package com.chatapp.mvp.verify;

import com.chatapp.service.models.request.VerifyEmailRequest;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface VerifyPresent {
    void submitVerifyForm(VerifyEmailRequest request);
}
