
package com.chatapp.mvp.myprofile;

import java.lang.ref.WeakReference;

public class MyProfilePresenterImpl implements MyProfileMvp.MyProfilePresent {

    private WeakReference<MyProfileMvp.MyProfileView> view;
    private MyProfileMvp.MyProfileInteractor interactor;

    public MyProfilePresenterImpl(MyProfileMvp.MyProfileView view) {
        this.view = new WeakReference<>(view);
        this.interactor = new MyProfileInteractorImpl();
    }

}
