package com.chatapp.mvp.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

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

    public static final int SELECT_PICTURE = 1;
    public static final int RC_GET_COUNTRY_CODE = 2;
    public static final int RC_CHANGE_PASSWORD = 3;

    public static final int PERMISSION_READ_EXTERNAL_STORAGE = 101;

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

    protected void processUpLoad() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            openGallery();
        }

    }

    protected void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(this, "Need access your phone to get pictures!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
