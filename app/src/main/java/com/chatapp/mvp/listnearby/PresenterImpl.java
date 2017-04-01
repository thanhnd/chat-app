package com.chatapp.mvp.listnearby;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresenterImpl implements ListNearbyMvp.Presenter {

    private WeakReference<ListNearbyMvp.View> view;
    private ListNearbyMvp.Interactor interactor;
    private GeneralInteractor generalInteractor;

    public PresenterImpl(ListNearbyMvp.View view) {

        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
        this.generalInteractor = new GeneralInteractorImmpl();

    }

    @Override
    public void getListNearBy() throws RequireLoginException {

        if (AccountUtils.getLongitude() == null || AccountUtils.getLongitude() == null) {
            return;
        }

        ListNearbyRequest request = new ListNearbyRequest();
        request.setLongitude(AccountUtils.getLongitude());
        request.setLatitude(AccountUtils.getLatitude());

        interactor.getListNearBy(request, new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {

            @Override
            public void onSuccess(ResponseModel<List<UserModel>> response) {
                if (view.get() != null) {
                    view.get().onGetListNearbySuccess(response.getResultSet());
                }
            }
        });
    }

    @Override
    public void getMyProfile() throws RequireLoginException {
        generalInteractor.getMyProfile(new BaseApiCallback<ResponseModel<MyProfileModel>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<MyProfileModel> response) {
                if (view.get() != null) {
                    view.get().onGetMyProfileSuccess(response.getResultSet());
                }
            }
        });
    }
}
