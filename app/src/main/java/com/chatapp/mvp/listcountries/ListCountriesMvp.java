package com.chatapp.mvp.listcountries;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.CountryModel;

import java.util.List;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface ListCountriesMvp {

    interface ListCountriesPresenter {
        void getListCountries();
    }

    interface ListCountriesView extends BaseView {
        void onGetListCountriesSuccess(List<CountryModel> listCountries);
        void onGetListCountriesFail();
    }
}
