package com.chatapp.service;

import com.chatapp.Config;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    @POST("/api/user/login")
    Call<ResponseModel<LogInModel>> signIn(@Body LogInRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/register")
    Call<ResponseModel<RegisterModel>> register(@Body RegisterRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/verifyCode")
    Call<ResponseModel<VerifyModel>> verifyCode(@Header("Authorization") String authorization, @Body VerifyEmailRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/updateBasic")
    Call<ResponseModel<Object>> updateBasicProfile(@Header("Authorization") String authorization, @Body BasicProfileRequest request);

    @Headers("Content-Type: application/json")
    @GET("/api/user/getVerifyCode")
    Call<ResponseModel<Object>> getVerifyCode(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/api/common/listCoutry")
    Call<ResponseModel<List<CountryModel>>> listCountries();

}
