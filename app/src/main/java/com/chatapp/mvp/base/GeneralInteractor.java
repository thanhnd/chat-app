package com.chatapp.mvp.base;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public interface GeneralInteractor {
    void getMyProfile(ApiCallback<ResponseModel<MyProfileModel>> callback) throws RequireLoginException;
    void sendVerifyCodeForgotPassword(Map<String, String> request, BaseApiCallback<ResponseModel<Object>> baseApiCallback);
    void listCountries(ApiCallback<ResponseModel<List<CountryModel>>> callback);
    void saveCountries(List<CountryModel> listCountries);
    List<CountryModel> getCountriesFromDatabase();
}
