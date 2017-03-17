package com.chatapp.mvp.splash;

import com.chatapp.mvp.base.BaseView;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface SplashMvp {

    interface SplashView extends BaseView {
        void onGetListCommonParamsSuccess();
        void onGetListCommonFail();
    }

    interface Presenter {
        void getListCommonParams();
    }
}
