
package com.chatapp.mvp.listcountries;

import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ListCountriesPresenterImpl implements ListCountriesMvp.ListCountriesPresenter {

    private WeakReference<ListCountriesMvp.ListCountriesView> view;
    private ListCountriesMvp.ListCountriesInteractor interactor;

    public ListCountriesPresenterImpl(ListCountriesMvp.ListCountriesView listCountriesView) {
        this.view = new WeakReference<>(listCountriesView);
        this.interactor = new ListCountriesInteractorImpl();
    }

    @Override
    public void getListCountries() {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.listCountries(new BaseApiCallback<ResponseModel<List<CountryModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<CountryModel>> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        view.get().onGetListCountriesSuccess(responseModel.getResultSet());
                    }
                }
            }
        });
    }
}
