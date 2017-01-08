
package com.chatapp.mvp.listcountries;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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
        interactor.listCountries(new ApiCallback<ResponseModel<List<CountryModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<CountryModel>> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    if (responseModel.getResponseCd() == ResponseModel.RESPONSE_CD_SUCCESS) {
                        view.get().onGetListCountriesSuccess(responseModel.getResultSet());
                    }
                }
            }

            @Override
            public void onFail(Response<ResponseModel<List<CountryModel>>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel<List<CountryModel>> responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onGetListCountriesFail();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<List<CountryModel>>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });
    }
}
