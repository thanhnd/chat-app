package com.chatapp.service;

import com.chatapp.Config;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ListNearByModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.service.models.response.UserProfileModel;
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
import retrofit2.http.Path;

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

    @Headers("Content-Type: application/json")
    @POST("/api/profile/listNearBy")
    Call<ResponseModel<ListNearByModel>> listNearby(@Header("Authorization") String authorization, @Body ListNearbyRequest request);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listFavourite")
    Call<ResponseModel<List<UserModel>>> listFavorites(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listFavourite")
    Call<ResponseModel<List<UserModel>>> search(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/myProfile")
    Call<ResponseModel<MyProfileModel>> getMyProfile(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/userProfile/{userId}")
    Call<ResponseModel<UserProfileModel>> getUserProfile(@Header("Authorization") String authorization, @Path("userId") String userId);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/addFavourite")
    Call<ResponseModel<Object>> addUserFavorite(@Header("Authorization") String authorization, @Body UserRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/addFriend")
    Call<ResponseModel<Object>> requestAddFriend(@Header("Authorization") String authorization, @Body UserRequest request);

}
