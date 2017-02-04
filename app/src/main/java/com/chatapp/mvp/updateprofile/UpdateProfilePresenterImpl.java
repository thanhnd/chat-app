
package com.chatapp.mvp.updateprofile;

import java.lang.ref.WeakReference;

public class UpdateProfilePresenterImpl implements UpdateProfileMvp.UpdateProfilePresent {

    private WeakReference<UpdateProfileMvp.UpdateProfileView> view;
    private UpdateProfileMvp.UpdateProfileInteractor interactor;

    public UpdateProfilePresenterImpl(UpdateProfileMvp.UpdateProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new UpdateProfileInteractorImpl();
    }

}
