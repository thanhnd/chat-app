
package com.chatapp.mvp.listcountries;


import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;

import java.util.List;

public interface ListCountriesInteractor {
    void listCountries(ApiCallback<ResponseModel<List<CountryModel>>> callback);
}
