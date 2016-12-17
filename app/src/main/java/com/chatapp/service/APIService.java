package com.chatapp.service;

import com.chatapp.Config;
import com.chatapp.service.models.request.SignInRequest;
import com.chatapp.service.models.response.SignInModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public interface ApiService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Headers("Content-Type: application/json")
    @POST("/api/sign_in")
    Call<SignInModel> signIn(@Body SignInRequest signInRequest);
}
