
package com.chatapp.mvp.register;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.BaseResponse;
import com.chatapp.service.models.response.RegisterModel;

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
        registerInteractor.register(request, new ApiCallback<RegisterModel>() {
            @Override
            public void onSuccess(RegisterModel response) {
                if (registerView.get() != null) {
                    registerView.get().hideProgress();
                    registerView.get().onRegisterSuccess();
                }
            }

            @Override
            public void onFail(Response<RegisterModel> response) {
                if (registerView.get() != null) {
                    registerView.get().hideProgress();

                    // Get data response from server
                    BaseResponse baseResponse = response.body();

                    // Show error message from server if there is
                    if (baseResponse != null && !TextUtils.isEmpty(baseResponse.getResponseMsg())) {
                        registerView.get().showErrorDialog(baseResponse.getResponseMsg());
                    } else {
                        registerView.get().onRegisterError();
                    }
                }
            }

            @Override
            public void onFail(Call<RegisterModel> call, Throwable throwable) {
                if (registerView != null) {
                    registerView.get().showErrorDialog();
                    registerView.get().hideProgress();
                }
            }
        });
    }
}
