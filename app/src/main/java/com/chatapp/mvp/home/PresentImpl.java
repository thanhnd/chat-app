package com.chatapp.mvp.home;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class PresentImpl implements HomeMvp.Present {

    private WeakReference<HomeMvp.View> view;
    private HomeMvp.Interactor interactor;

    public PresentImpl(HomeMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
    }

    @Override
    public void updateLocation(double latitude, double longitude) throws RequireLoginException {
        interactor.updatLocation(latitude, longitude, new BaseApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
            }
        });
    }
}
