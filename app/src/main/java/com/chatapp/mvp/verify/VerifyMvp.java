package com.chatapp.mvp.verify;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.VerifyEmailRequest;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.service.models.response.VerifyModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface VerifyMvp {
    interface VerifyInteractor {
        void verify(VerifyEmailRequest request,
                    ApiCallback<ResponseModel<VerifyModel>> loginCallback);
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface VerifyPresent {
        void submitVerifyForm(VerifyEmailRequest request);
    }

    /**
     * Created by thanhnguyen on 12/17/16.
     */
    interface VerifyView extends BaseView {
        void onVerifySuccess();
        void onVerifyError();
    }
}
