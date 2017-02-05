
package com.chatapp.mvp.splash;

import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.CacheUtil;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

public class PresenterImpl implements SplashMvp.Presenter {

    private WeakReference<SplashMvp.SplashView> view;
    private SplashMvp.Interactor interactor;

    public PresenterImpl(SplashMvp.SplashView splashView) {
        this.view = new WeakReference<>(splashView);
        this.interactor = new InteractorImpl();
    }

    @Override
    public void getListCommonParams() {
        this.interactor.getListCommonParams(new ApiCallback<ResponseModel<ListParamsModel>>() {
            @Override
            public void onSuccess(ResponseModel<ListParamsModel> response) {
                ListParamsModel listParamsModel = response.getResultSet();
                CacheUtil.setListParamsModel(listParamsModel);
                if (view.get() != null) {
                    view.get().onGetListCommonParamsSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<ListParamsModel>> response) {

            }

            @Override
            public void onFail(Call<ResponseModel<ListParamsModel>> call, Throwable throwable) {

            }
        });
    }

}
