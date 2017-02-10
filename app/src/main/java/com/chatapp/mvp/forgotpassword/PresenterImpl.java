
package com.chatapp.mvp.forgotpassword;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class PresenterImpl implements ForgotPasswordMvp.Presenter {

    private WeakReference<ForgotPasswordMvp.View> view;
    private ForgotPasswordMvp.Interactor interactor;
    private GeneralInteractor generalInteractor;

    public PresenterImpl(ForgotPasswordMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
        this.generalInteractor = new GeneralInteractorImmpl();
    }

    @Override
    public void submitVerifyCode(String code) {

        Map<String, String> request = new HashMap<>();
        request.put("code", code);

        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.submitVerifyCode(request, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onSubmitCodeSuccess();
                }
            }
        });

    }

    @Override
    public void changePassword(Map<String, String> request) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.submitPassword(request, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onSubmitPasswordSuccess();
                }
            }
        });
    }

    @Override
    public void sendVerifyCodeForgotPassword(String email) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        Map<String, String> request = new HashMap<>();
        request.put("email", email);

        generalInteractor.sendVerifyCodeForgotPassword(request, new BaseApiCallback<ResponseModel<Object>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().sendVerifyCodeForgotPasswordWithEmailSuccess();
                }
            }
        });
    }
}
