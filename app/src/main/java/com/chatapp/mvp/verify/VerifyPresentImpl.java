package com.chatapp.mvp.verify;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

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
    public void submitVerifyForm(VerifyEmailRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.verify(request, new ApiCallback<ResponseModel<VerifyModel>>() {

            @Override
            public void onSuccess(ResponseModel<VerifyModel> response) {

                AccountUtils.setAccountStatus(LogInModel.VERIFIED);

                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onVerifySuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<VerifyModel>> response) {

                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onVerifyError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<VerifyModel>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });
    }
}
