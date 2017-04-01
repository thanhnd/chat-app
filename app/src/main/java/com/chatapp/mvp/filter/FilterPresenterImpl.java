
package com.chatapp.mvp.filter;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;

import java.lang.ref.WeakReference;

public class FilterPresenterImpl implements FilterMvp.Presenter {

    private WeakReference<FilterMvp.View> view;
    private GeneralInteractor interactor;

    public FilterPresenterImpl(FilterMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }

//    @Override
//    public void applyFilter(boolean isFilterPhoto, boolean isFilterOnline,
//                            int[] filterAge, int[] filterHeight, int[] filterWeight,
//                            int[] filterEthnicities, int[] filterBodyType, int[] filterTribes, int[] filterRelationshipStatus,
//                            int[] filterLocation) throws RequireLoginException {
//        interactor.applyFilter(isFilterPhoto, isFilterOnline, filterAge, filterHeight, filterWeight,
//                filterEthnicities, filterBodyType, filterTribes, filterRelationshipStatus,
//                filterLocation,
//                new BaseApiCallback<ResponseModel<List<UserModel>>>(view.get()) {
//                    @Override
//                    public void onSuccess(ResponseModel<List<UserModel>> response) {
//                        if (view.get() != null) {
//                            view.get().onApplyFilterSuccess(response.getResultSet());
//                        }
//                    }
//                });
//    }
}
