
package com.chatapp.mvp.album;

import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;

import java.lang.ref.WeakReference;

public class AlbumPresenterImpl implements AlbumMvp.Presenter {

    private WeakReference<AlbumMvp.View> view;
    private GeneralInteractor interactor;

    public AlbumPresenterImpl(AlbumMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new GeneralInteractorImmpl();
    }
}
