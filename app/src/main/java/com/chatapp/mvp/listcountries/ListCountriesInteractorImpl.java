package com.chatapp.mvp.listcountries;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.ApiService;
import com.chatapp.service.ApiServiceHelper;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;

import java.util.List;

import retrofit2.Call;

public class ListCountriesInteractorImpl implements ListCountriesMvp.ListCountriesInteractor {

    @Override
    public void listCountries(final ApiCallback<ResponseModel<List<CountryModel>>> callback) {
        // Mock registerCargo. I'm creating a handler to delay the answer a couple of seconds
        ApiService service = ApiServiceHelper.getInstance();
        Call<ResponseModel<List<CountryModel>>> call = service.listCountries();
        call.enqueue(callback);
    }
}
