
package com.chatapp.mvp.register;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterPresenterImpl implements RegisterPresent {

    private WeakReference<RegisterView> registerView;
    private RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView view) {
        this.registerView = new WeakReference<>(view);
        this.registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void submitRegisterFrom(RegisterRequest request) {
        if (registerView.get() != null) {
            registerView.get().showProgress();
        }
        registerInteractor.register(request, new ApiCallback<ResponseModel<RegisterModel>>() {
            @Override
            public void onSuccess(ResponseModel<RegisterModel> response) {
                if (registerView.get() != null) {
                    registerView.get().hideProgress();
                    registerView.get().onRegisterSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<RegisterModel>> response) {
                if (registerView.get() != null) {
                    registerView.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        registerView.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        registerView.get().onRegisterError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<RegisterModel>> call, Throwable throwable) {
                if (registerView != null) {
                    registerView.get().showErrorDialog();
                    registerView.get().hideProgress();
                }
            }
        });
    }
}
