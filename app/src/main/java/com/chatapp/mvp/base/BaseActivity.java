package com.chatapp.mvp.base;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.chatapp.chat.utils.SharedPreferencesUtil;
import com.chatapp.chat.utils.chat.ChatHelper;
import com.chatapp.mvp.login.LogInActivity;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import java.util.Calendar;

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

    protected void pickDate(DatePickerDialog.OnDateSetListener onDateSetListener) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                onDateSetListener, year, month, day);
        datePickerDialog.show();
    }

    protected void pickTime(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener, hour, minute, false);
        timePickerDialog.show();
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
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}
