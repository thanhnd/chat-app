
package com.chatapp.mvp.myprofile;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

public class MyProfilePresenterImpl implements MyProfileMvp.MyProfilePresenter {

    private WeakReference<MyProfileMvp.MyProfileView> view;
    private GeneralInteractor interactor;

    public MyProfilePresenterImpl(MyProfileMvp.MyProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void getMyProfile() throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getMyProfile(new BaseApiCallback<ResponseModel<MyProfileModel>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<MyProfileModel> response) {
                AccountUtils.setMyProfileModel(response.getResultSet());
                if (view.get() != null) {
                    view.get().onGetMyProfileSuccess(response.getResultSet());
                }
            }
        });
    }

    @Override
    public CountryModel getCountry(int countryId) {
        return interactor.getCountryFromDatabase(countryId);
    }
}
