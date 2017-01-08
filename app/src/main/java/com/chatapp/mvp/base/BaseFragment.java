package com.chatapp.mvp.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.chatapp.mvp.login.LogInActivity;

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

    @Override
    public void onTokenExpired() {
        if (getActivity() != null && isAdded()) {
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
