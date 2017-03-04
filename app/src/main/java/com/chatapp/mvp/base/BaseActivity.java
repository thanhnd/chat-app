package com.chatapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.chatapp.chat.services.CallService;
import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.login.LogInActivity;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

public class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        DialogUtils.showProgressDialog(this);
    }

    @Override
    public void hideProgress() {
        DialogUtils.hideProgressDialog(this);
    }

    @Override
    public void showErrorDialog() {
        DialogUtils.showGeneralErrorAlert(this);
    }

    @Override
    public void showErrorDialog(String message) {
        DialogUtils.showGeneralErrorAlert(this, message);
    }

    @Override
    public void showDialog(String title, String message) {
        DialogUtils.showGeneralAlert(this, title, message);
    }


    @Override
    public void onTokenExpired() {
        logOut();
    }

    @Override
    public void onRequiredLogin() {
        logOut();
    }

    public void logOut() {
        userLogout();
        AccountUtils.logOut();
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void userLogout() {
        ChatHelper.getInstance().logout(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
//                if (googlePlayServicesHelper.checkPlayServicesAvailable()) {
//                    googlePlayServicesHelper.unregisterFromGcm(Consts.GCM_SENDER_ID);
//                }
                SharedPreferencesUtil.removeQbUser();
            }

            @Override
            public void onError(QBResponseException e) {
//                reconnectToChatLogout(SharedPreferencesUtil.getQbUser());
            }
        });
    }

    protected void startLoginService(QBUser qbUser) {
        CallService.start(this, qbUser);
    }
}
