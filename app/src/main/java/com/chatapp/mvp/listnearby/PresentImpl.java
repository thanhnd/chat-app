package com.chatapp.mvp.listnearby;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresentImpl implements ListNearbyMvp.Present {

    private WeakReference<ListNearbyMvp.View> view;
    private ListNearbyMvp.Interactor interactor;

    public PresentImpl(ListNearbyMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();

    }
    @Override
    public void getListNearBy() {
        if (AccountUtils.getLongitude() == null || AccountUtils.getLongitude() == null) {
            return;
        }

        ListNearbyRequest request = new ListNearbyRequest();
        request.setLongitude(AccountUtils.getLongitude());
        request.setLatitude(AccountUtils.getLatitude());

        interactor.getListNearBy(request, new AuthorizeApiCallback<ResponseModel<List<UserModel>>>() {
            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListNearbySuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<List<UserModel>>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<List<UserModel>>> call, Throwable throwable) {

            }

            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().onTokenExpired();
                }
            }
        });
    }

    @Override
    public void getMyProfile() {
        interactor.getMyProfile(new AuthorizeApiCallback<ResponseModel<MyProfileModel>>() {
            @Override
            public void onSuccess(ResponseModel<MyProfileModel> response) {
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
