
package com.chatapp.mvp.userprofile;

import java.lang.ref.WeakReference;

public class UserProfilePresenterImpl implements UserProfileMvp.UserProfilePresent {

    private WeakReference<UserProfileMvp.UserProfileView> view;
    private UserProfileMvp.UserProfileInteractor interactor;

    public UserProfilePresenterImpl(UserProfileMvp.UserProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new UserProfileInteractorImpl();
    }

}
