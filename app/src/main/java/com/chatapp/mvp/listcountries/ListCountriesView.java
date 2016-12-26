package com.chatapp.mvp.listcountries;


import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.CountryModel;

import java.util.List;

public interface ListCountriesView extends BaseView {
    void onGetListCountriesSuccess(List<CountryModel> listCountries);
    void onGetListCountriesFail();
}
