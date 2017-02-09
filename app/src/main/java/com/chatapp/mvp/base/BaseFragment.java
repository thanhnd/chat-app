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
        BaseActivity activity = (BaseActivity) getActivity();
        if (isAdded() && activity!= null) {
            activity.showErrorDialog();
        }
    }

    @Override
    public void showErrorDialog(String message) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (isAdded() && activity!= null) {
            activity.showErrorDialog(message);
        }
    }

    @Override
    public void showDialog(String title, String message) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (isAdded() && activity!= null) {
            activity.showDialog(title, message);
        }
    }

    @Override
    public void onTokenExpired() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (getActivity() != null && isAdded()) {
            activity.onTokenExpired();
        }
    }

    @Override
    public void onRequiredLogin() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (getActivity() != null && isAdded()) {
            activity.onRequiredLogin();
        }
    }
}
