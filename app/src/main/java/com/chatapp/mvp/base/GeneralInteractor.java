package com.chatapp.mvp.base;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.google.gson.internal.LinkedTreeMap;

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
    CountryModel getCountryFromDatabase(int countryId);
    void getListCommonParams(ApiCallback<ResponseModel<ListParamsModel>> callback);

    void updateShowHideDistance(boolean isShowDistance, ApiCallback<ResponseModel> callback)
            throws RequireLoginException;

    void getShowHideDistance(ApiCallback<ResponseModel<LinkedTreeMap<String, Integer>>> callback)
            throws RequireLoginException;

    void updatePassword(String currentPassword, String newPassword, ApiCallback<ResponseModel> callback)
            throws RequireLoginException;

    void updateUnitSystem(int unitSystem, BaseApiCallback<ResponseModel> baseApiCallback)
            throws RequireLoginException;

    void applyFilter(boolean isFilterPhoto, boolean isFilterOnline,
                     int[] filterAge, int[] filterHeight, int[] filterWeight,
                     int[] filterEthnicities, int[] filterBodyType, int[] filterTribes, int[] filterRelationshipStatus,
                     int[] filterLocation, BaseApiCallback<ResponseModel<List<UserModel>>> baseApiCallback)
            throws RequireLoginException;

    void applyFilter(Map query, BaseApiCallback<ResponseModel<List<UserModel>>> baseApiCallback)
            throws RequireLoginException;
}
