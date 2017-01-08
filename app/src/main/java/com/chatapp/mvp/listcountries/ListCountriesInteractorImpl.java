package com.chatapp.mvp.listcountries;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCountriesInteractorImpl implements ListCountriesMvp.ListCountriesInteractor {

    @Override
    public void listCountries(final ApiCallback<ResponseModel<List<CountryModel>>> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiService.retrofit.create(ApiService.class);
        Call<ResponseModel<List<CountryModel>>> call = service.listCountries();
        call.enqueue(new Callback<ResponseModel<List<CountryModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<CountryModel>>> call, Response<ResponseModel<List<CountryModel>>> response) {
                Log.d(response.raw().toString());
                ResponseModel<List<CountryModel>> responseModel = response.body();
                if (callback != null) {
                    if (response.isSuccessful() && responseModel != null
                            && responseModel.getResponseCd() != ResponseModel.RESPONSE_CD_ERROR) {
                        callback.onSuccess(responseModel);
                    } else {
                        callback.onFail(response);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<CountryModel>>> call, Throwable t) {
                if (callback != null) {
                    callback.onFail(call, t);
                }
                Log.e(t);
            }
        });
    }

}
