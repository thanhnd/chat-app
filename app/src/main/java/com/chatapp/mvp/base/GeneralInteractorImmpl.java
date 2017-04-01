package com.chatapp.mvp.base;

import com.chatapp.MyApplication;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;
import com.google.gson.internal.LinkedTreeMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

/**
 * Created by thanhnguyen on 2/7/17.
 */

public class GeneralInteractorImmpl implements GeneralInteractor {

    @Override
    public void getMyProfile(final ApiCallback<ResponseModel<MyProfileModel>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<MyProfileModel>> call = service.getMyProfile(AccountUtils.getAuthorization());
        call.enqueue(callback);
    }

    @Override
    public void sendVerifyCodeForgotPassword(Map<String, String> request, BaseApiCallback<ResponseModel<Object>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<Object>> call = service.sendVerifyCodeForgotPassword(request);
        call.enqueue(callback);
    }

    @Override
    public void listCountries(final ApiCallback<ResponseModel<List<CountryModel>>> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<CountryModel>>> call = service.listCountries();
        call.enqueue(callback);
    }

    @Override
    public void saveCountries(List<CountryModel> listCountries) {
        Realm.init(MyApplication.getInstance());
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        for (CountryModel countryModel : listCountries) {
            realm.copyToRealm(countryModel);
        }

        realm.commitTransaction();
        realm.close();

    }

    @Override
    public List<CountryModel> getCountriesFromDatabase() {
        Realm.init(MyApplication.getInstance());
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        RealmResults<CountryModel> countryModels = realm.where(CountryModel.class).findAll();

        realm.commitTransaction();

        List<CountryModel> results = realm.copyFromRealm(countryModels);

        realm.close();

        return  results;
    }

    @Override
    public CountryModel getCountryFromDatabase(int countryId) {
        try {
            Realm.init(MyApplication.getInstance());
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            CountryModel countryModel = realm.where(CountryModel.class).equalTo("countryId", countryId).findFirst();

            realm.commitTransaction();

            CountryModel result = null;
            if (countryModel != null) {
                result = realm.copyFromRealm(countryModel);
            }

            realm.close();

            return  result;
        } catch (Exception e) {
            Log.e(e);
        }

        return null;
    }

    @Override
    public void getListCommonParams(final ApiCallback<ResponseModel<ListParamsModel>> callback) {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<ListParamsModel>> call = service.listCommon();
        call.enqueue(callback);
    }

    @Override
    public void updateShowHideDistance(boolean isShowDistance,
                                       ApiCallback<ResponseModel> callback)
            throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, Integer> request = new HashMap<>();
        request.put("is_show_distance", isShowDistance ? 1 : 0);

        Call<ResponseModel> call = service.updateShowHideDistance(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

    @Override
    public void getShowHideDistance(ApiCallback<ResponseModel<LinkedTreeMap<String, Integer>>> callback)
            throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();

        Call<ResponseModel<LinkedTreeMap<String, Integer>>> call = service.getShowHideDistance(AccountUtils.getAuthorization());
        call.enqueue(callback);
    }

    @Override
    public void updatePassword(String currentPassword, String newPassword, ApiCallback<ResponseModel> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, String> request = new HashMap<>();
        request.put("currentPassword", currentPassword);
        request.put("password", newPassword);

        Call<ResponseModel> call = service.changePassword(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

    @Override
    public void updateUnitSystem(int unitSystem, BaseApiCallback<ResponseModel> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, Integer> request = new HashMap<>();
        request.put("unit_system", unitSystem);

        Call<ResponseModel> call = service.updateUnitSystem(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

    @Override
    public void applyFilter(boolean isFilterPhoto, boolean isFilterOnline,
                            int[] filterAge, int[] filterHeight, int[] filterWeight,
                            int[] filterEthnicities, int[] filterBodyType, int[] filterTribes, int[] filterRelationshipStatus,
                            int[] filterLocation,
                            BaseApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Map<String, Integer> request = new HashMap<>();

        request.put("age_from", filterAge != null && filterAge.length > 0 ? filterAge[0] : 0);
        request.put("age_to", filterAge != null && filterAge.length > 0 ? filterAge[1] : 0);

        request.put("height_from", filterHeight != null && filterHeight.length > 0 ? filterHeight[0] : 0);
        request.put("height_to", filterHeight != null && filterHeight.length > 0 ? filterHeight[1] : 0);

        request.put("weight_from", filterWeight != null && filterWeight.length > 0 ? filterWeight[0] : 0);
        request.put("weight_to", filterWeight != null && filterWeight.length > 0 ? filterWeight[1] : 0);

        Call<ResponseModel<List<UserModel>>> call = service.applyFilter(AccountUtils.getAuthorization(), request);
        call.enqueue(callback);
    }

    @Override
    public void applyFilter(Map query, BaseApiCallback<ResponseModel<List<UserModel>>> callback) throws RequireLoginException {
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<UserModel>>> call = service.applyFilter(AccountUtils.getAuthorization(), query);
        call.enqueue(callback);
    }

}
