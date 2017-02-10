package com.chatapp.mvp.verify;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by thanhnguyen on 12/17/16.
 */

public class VerifyPresentImpl implements VerifyMvp.VerifyPresent {
    private WeakReference<VerifyMvp.VerifyView> view;
    private VerifyMvp.VerifyInteractor interactor;

    public VerifyPresentImpl(VerifyMvp.VerifyView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new VerifyInteractorImpl();
    }
    @Override
    public void submitVerifyForm(Map<String, String> request) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.verify(request, new BaseApiCallback<ResponseModel<VerifyModel>>() {

            @Override
            public void onSuccess(ResponseModel<VerifyModel> response) {

                AccountUtils.setAccountStatus(LogInModel.VERIFIED);

                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onVerifySuccess();
                }
            }
        });
    }
}
