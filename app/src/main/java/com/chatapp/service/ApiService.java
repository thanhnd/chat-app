package com.chatapp.service;

import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.request.UserRequest;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.service.models.response.UserProfileModel;
import com.chatapp.service.models.response.VerifyModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/api/user/login")
    Call<ResponseModel<LogInModel>> signIn(@Body LogInRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/register")
    Call<ResponseModel<RegisterModel>> register(@Body RegisterRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/verifyCode")
    Call<ResponseModel<VerifyModel>> verifyCode(@Header("Authorization") String authorization, @Body Map<String, String> request);

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
    Call<ResponseModel<List<UserModel>>> listNearby(@Header("Authorization") String authorization, @Body ListNearbyRequest request);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listFavourite")
    Call<ResponseModel<List<UserModel>>> listFavorites(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listFriend")
    Call<ResponseModel<List<UserModel>>> listFriends(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/search")
    Call<ResponseModel<List<UserModel>>> search(@Header("Authorization") String authorization, @Body Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/myProfile")
    Call<ResponseModel<MyProfileModel>> getMyProfile(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/userProfile/{userId}")
    Call<ResponseModel<UserProfileModel>> getUserProfile(@Header("Authorization") String authorization, @Path("userId") String userId);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/addFavourite")
    Call<ResponseModel<Object>> addFavorite(@Header("Authorization") String authorization, @Body UserRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/delFavourite")
    Call<ResponseModel<Object>> removeFavorite(@Header("Authorization") String authorization, @Body UserRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/addFriend")
    Call<ResponseModel<Object>> requestAddFriend(@Header("Authorization") String authorization, @Body UserRequest request);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listRecommendFriend")
    Call<ResponseModel<List<UserModel>>> listRecommendedFriends(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/acceptFriend")
    Call<ResponseModel<Object>> acceptFriendRequest(@Header("Authorization") String authorization, @Body UserRequest request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/updateLonLat")
    Call<ResponseModel<Object>> updateLonLat(@Header("Authorization") String authorization, @Body Map request);

    @Headers("Content-Type: application/json")
    @GET("/api/common/listCommon")
    Call<ResponseModel<ListParamsModel>> listCommon();

    @Multipart
    @POST("/api/user/uploadAvatar")
    Call<ResponseModel<LinkedTreeMap<String, String>>> uploadAvatar(@Header("Authorization") String authorization,
                                                                    @Part MultipartBody.Part file);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/update")
    Call<ResponseModel<Object>> updateProfile(@Header("Authorization") String authorization,
                                              @Body Map request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/delRequest")
    Call<ResponseModel<Object>> deleteRecommend(@Header("Authorization") String authorization, @Body List<Map<String,String>> list);

    @Headers("Content-Type: application/json")
    @POST("/api/user/forgotCodePass")
    Call<ResponseModel<Object>> sendVerifyCodeForgotPassword(@Body Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/confirmCodeForgotPass")
    Call<ResponseModel> confirmCodeForgotPassword(@Body Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/changePassword")
    Call<ResponseModel> changePassword(@Body Map<String, String> request);

    // region Settings

    @Headers("Content-Type: application/json")
    @POST("/api/user/showHideDistance")
    Call<ResponseModel> updateShowHideDistance(@Header("Authorization") String authorization,
                                         @Body Map<String, Integer> request);

    @Headers("Content-Type: application/json")
    @GET("/api/user/getShowHideDistance")
    Call<ResponseModel<LinkedTreeMap<String, Integer>>> getShowHideDistance(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @POST("/api/user/changeUnitSystem")
    Call<ResponseModel> updateUnitSystem(@Header("Authorization") String authorization,
                                               @Body Map<String, Integer> request);

    @Headers("Content-Type: application/json")
    @POST("/api/user/changePasswordUser")
    Call<ResponseModel> changePassword(@Header("Authorization") String authorization,
                                               @Body Map<String, String> request);
    // endregion Settings

    // region Block users

    @Headers("Content-Type: application/json")
    @POST("/api/profile/addBlock")
    Call<ResponseModel> addBlock(@Header("Authorization") String authorization,
                                       @Body Map<String, String> request);

    @Headers("Content-Type: application/json")
    @POST("/api/profile/unBlock")
    Call<ResponseModel> unBlock(@Header("Authorization") String authorization,
                                 @Body Map<String, String> request);

    @Headers("Content-Type: application/json")
    @GET("/api/profile/listBlockUser")
    Call<ResponseModel> listBlockUsers(@Header("Authorization") String authorization);

    // endregion block

    @Headers("Content-Type: application/json")
    @POST("/api/profile/filter")
    Call<ResponseModel<List<UserModel>>> applyFilter(@Header("Authorization") String authorization, @Body Map request);

    @Headers("Content-Type: application/json")
    @GET("/api/common/listCountryFilter")
    Call<ResponseModel<List<CountryModel>>> loadFilterCountries();
}
