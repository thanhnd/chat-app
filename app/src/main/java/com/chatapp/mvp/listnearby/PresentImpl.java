package com.chatapp.mvp.listnearby;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.ListNearByModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

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
        ListNearbyRequest request = new ListNearbyRequest();
        request.setLongitude(0);
        request.setLatitude(20);
        request.setPage(0);
        interactor.getListNearBy(request, new AuthorizeApiCallback<ResponseModel<ListNearByModel>>() {
            @Override
            public void onSuccess(ResponseModel<ListNearByModel> response) {
                if (view.get() != null) {
                    view.get().onGetListNearbySuccess(response.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<ListNearByModel>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<ListNearByModel>> call, Throwable throwable) {

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
