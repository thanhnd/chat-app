package com.chatapp.mvp.base;

import com.chatapp.MyApplication;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.Log;

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

            CountryModel result = realm.copyFromRealm(countryModel);

            realm.close();

            return  result;
        } catch (Exception e) {
            Log.e(e);
        }

        return null;

    }
}
