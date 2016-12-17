package com.chatapp.service;

import com.chatapp.service.models.response.ResponseModel;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public interface ApiCallback<T extends ResponseModel> {
    void onSuccess(T response);

    void onFail(Response<T> response);

    void onFail(Call<T> call, Throwable throwable);
}

