package com.chatapp.mvp.base;

import android.support.v4.app.Fragment;

/**
 * Created by thanhnguyen on 1/6/17.
 */
public class BaseFragment extends Fragment implements BaseView {
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorDialog() {

    }

    @Override
    public void showErrorDialog(String message) {

    }

    @Override
    public void showDialog(String title, String message) {

    }
}
