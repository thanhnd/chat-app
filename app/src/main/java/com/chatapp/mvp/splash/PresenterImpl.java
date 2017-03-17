
package com.chatapp.mvp.splash;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.CacheUtil;

import java.lang.ref.WeakReference;

public class PresenterImpl implements SplashMvp.Presenter {

    private WeakReference<SplashMvp.SplashView> view;
    private GeneralInteractor interactor;

    public PresenterImpl(SplashMvp.SplashView splashView) {
        this.view = new WeakReference<>(splashView);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void getListCommonParams() {

        this.interactor.getListCommonParams(new BaseApiCallback<ResponseModel<ListParamsModel>>(view.get()) {

            @Override
            public void onSuccess(ResponseModel<ListParamsModel> response) {
                ListParamsModel listParamsModel = response.getResultSet();
                CacheUtil.setListParamsModel(listParamsModel);
                if (view.get() != null) {
                    view.get().onGetListCommonParamsSuccess();
                }
            }
        });
    }
}
