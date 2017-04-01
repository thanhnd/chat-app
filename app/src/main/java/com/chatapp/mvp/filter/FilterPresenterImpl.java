
package com.chatapp.mvp.filter;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;
import java.util.List;

public class FilterPresenterImpl implements FilterMvp.Presenter {

    private WeakReference<FilterMvp.View> view;
    private GeneralInteractor interactor;

    public FilterPresenterImpl(FilterMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void loadFilterCountry() {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.loadFilterCountries(new BaseApiCallback<ResponseModel<List<CountryModel>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<List<CountryModel>> response) {
                if (view.get() != null) {
                    view.get().onLoadFilterCountriesSuccess(response.getResultSet());
                }
            }
        });
    }
}
