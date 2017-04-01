package com.chatapp.mvp.filter;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.models.response.CountryModel;

import java.util.List;

/**
 * Created by thanhnguyen on 2/10/17.
 */

public class FilterMvp {
    interface View extends BaseView {
        void onLoadFilterCountriesSuccess(List<CountryModel> resultSet);

    }

    interface Presenter {
        void loadFilterCountry();
    }
}
