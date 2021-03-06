package com.chatapp.mvp.verify;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;

import java.util.Map;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface VerifyMvp {
    interface VerifyInteractor {
        void verify(Map<String, String> request,
                    ApiCallback<ResponseModel<VerifyModel>> loginCallback) throws RequireLoginException;
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface VerifyPresent {
        void submitVerifyForm(Map<String, String> request) throws RequireLoginException;
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface VerifyView extends BaseView {
        void onVerifySuccess();
        void onVerifyError();
    }
}
