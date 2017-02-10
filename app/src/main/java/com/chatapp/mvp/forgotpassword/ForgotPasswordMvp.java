package com.chatapp.mvp.forgotpassword;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;

import java.util.Map;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface ForgotPasswordMvp {
    interface Presenter {

        void submitVerifyCode(String code);

        void changePassword(Map<String, String> request);
    }

    interface View extends BaseView {
        void onSubmitCodeSuccess();

        void onSubmitPasswordSuccess();
    }

    interface Interactor {

        void submitVerifyCode(Map<String, String> request, ApiCallback<ResponseModel> callback);

        void submitPassword(Map<String, String> request, BaseApiCallback<ResponseModel> callback);
    }
}
