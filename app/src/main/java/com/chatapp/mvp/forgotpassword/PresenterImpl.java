
package com.chatapp.mvp.forgotpassword;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.Log;

import java.lang.ref.WeakReference;
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
    public void submitVerifyCode(Map<String, String> request) {

        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.submitVerifyCode(request, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {

                try {
                    Map result = (Map) response.getResultSet();
                    String chatId = (String) result.get("chat_id");
                    String oldPassword = (String) result.get("password_old");


                    if (view.get() != null) {
                        view.get().hideProgress();
                        view.get().onSubmitCodeSuccess(chatId, oldPassword);
                    }
                } catch (Exception e) {
                    Log.e(e);
                    if (view.get() != null) {
                        view.get().hideProgress();
                        view.get().onSubmitCodeError();
                    }
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
    public void sendVerifyCodeForgotPassword(Map<String, String> request) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        generalInteractor.sendVerifyCodeForgotPassword(request, new BaseApiCallback<ResponseModel<Object>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().sendVerifyCodeForgotPasswordSuccess();
                }
            }
        });
    }
}
