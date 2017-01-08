package com.chatapp.service;

import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface AuthorizeApiCallback<T extends ResponseModel> extends ApiCallback<T> {
    void onTokenExpired();
}
