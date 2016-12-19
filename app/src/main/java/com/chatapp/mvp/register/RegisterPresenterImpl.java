
package com.chatapp.mvp.register;

import android.text.TextUtils;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.LogInRequest;
import com.chatapp.service.models.request.RegisterRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.RegisterModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterPresenterImpl implements RegisterPresent {

    private WeakReference<RegisterView> view;
    private RegisterInteractor interactor;

    public RegisterPresenterImpl(RegisterView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new RegisterInteractorImpl();
    }

    @Override
    public void submitRegisterForm(RegisterRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.register(request, new ApiCallback<ResponseModel<RegisterModel>>() {
            @Override
            public void onSuccess(ResponseModel<RegisterModel> response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onRegisterSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<RegisterModel>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onRegisterError();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<RegisterModel>> call, Throwable throwable) {
                if (view != null) {
                    view.get().hideProgress();
                    view.get().showErrorDialog();
                }
            }
        });
    }

    @Override
    public void requestLogin(LogInRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.login(request, new ApiCallback<ResponseModel<LogInModel>>() {
            @Override
            public void onSuccess(ResponseModel<LogInModel> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    AccountUtils.setLogInModel(responseModel.getResultSet());
                    view.get().onLoginSuccess(responseModel.getResultSet());
                }
            }

            @Override
            public void onFail(Response<ResponseModel<LogInModel>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().showErrorDialog();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<LogInModel>> call, Throwable throwable) {
                if (view != null) {
                    view.get().hideProgress();
                    view.get().showErrorDialog();
                }
            }
        });
    }

    @Override
    public void getVerifyCode() {

        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getVerifyCode(new ApiCallback<ResponseModel<Object>>() {
            @Override
            public void onSuccess(ResponseModel<Object> responseModel) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onGetVerifyCodeSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().showErrorDialog();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {
                if (view != null) {
                    view.get().hideProgress();
                    view.get().showErrorDialog();
                }
            }
        });

    }
}
