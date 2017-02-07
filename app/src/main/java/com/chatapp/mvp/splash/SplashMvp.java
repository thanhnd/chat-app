package com.chatapp.mvp.splash;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface SplashMvp {
    interface Interactor {
        void getListCommonParams(ApiCallback<ResponseModel<ListParamsModel>> callback);
    }

    interface SplashView {
        void onGetListCommonParamsSuccess();
        void onGetListCommonFail();
    }

    interface Presenter {
        void getListCommonParams();
    }
}
