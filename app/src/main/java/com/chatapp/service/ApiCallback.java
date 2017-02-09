package com.chatapp.service;

import com.chatapp.service.models.response.ResponseModel;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public interface ApiCallback<T extends ResponseModel> extends Callback <T>{
    void onSuccess(T response);

    void onFailure(Response<T> response);

    void onTokenExpired();
}

