
package com.chatapp.mvp.setting;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.ref.WeakReference;
import java.util.Map;

public class SettingPresenterImpl implements SettingMvp.Presenter {

    private WeakReference<SettingMvp.View> view;
    private GeneralInteractor interactor;

    public SettingPresenterImpl(SettingMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

    @Override
    public void updateShowHideDistance(boolean isOn) throws  RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.updateShowHideDistance(isOn, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                if (view.get() != null) {
                    view.get().onUpdateShowHideDistanceSuccess(response.getResultSet());
                }
            }
        });
    }

    @Override
    public void getShowHideDistance() throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.getShowHideDistance(new BaseApiCallback<ResponseModel<LinkedTreeMap<String, Integer>>>(view.get()) {
            @Override
            public void onSuccess(ResponseModel<LinkedTreeMap<String, Integer>> response) {
                if (view.get() != null) {
                    Map<String, Integer> result = response.getResultSet();
                    boolean isOn = result.get("is_show_distance") == 1;

                    view.get().onGetShowHideDistanceSuccess(isOn);
                }
            }
        });
    }

    @Override
    public void updateUnitSystem(int unitSystem) throws RequireLoginException {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.updateUnitSystem(unitSystem, new BaseApiCallback<ResponseModel>(view.get()) {
            @Override
            public void onSuccess(ResponseModel response) {
                if (view.get() != null) {
                    view.get().onUpdateUnitSystemSuccess(response.getResultSet());
                }
            }
        });
    }
}
