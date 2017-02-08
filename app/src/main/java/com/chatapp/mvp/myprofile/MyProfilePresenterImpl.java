
package com.chatapp.mvp.myprofile;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class MyProfilePresenterImpl implements MyProfileMvp.MyProfilePresenter {

    private WeakReference<MyProfileMvp.MyProfileView> view;
    private GeneralInteractor interactor;

    public MyProfilePresenterImpl(MyProfileMvp.MyProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void getMyProfile() {
        interactor.getMyProfile(new AuthorizeApiCallback<ResponseModel<MyProfileModel>>() {
            @Override
            public void onSuccess(ResponseModel<MyProfileModel> response) {
                AccountUtils.setMyProfileModel(response.getResultSet());
                if (view.get() != null) {
                    view.get().onGetMyProfileSuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<MyProfileModel>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<MyProfileModel>> call, Throwable throwable) {

            }

            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().onTokenExpired();
                }
            }
        });

    }
}
