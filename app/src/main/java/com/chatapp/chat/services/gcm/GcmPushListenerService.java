package com.chatapp.chat.services.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.chatapp.R;
import com.chatapp.chat.services.CallService;
import com.chatapp.mvp.splash.SplashActivity;
import com.chatapp.utils.Log;
import com.quickblox.sample.core.gcm.CoreGcmPushListenerService;
import com.quickblox.sample.core.utils.NotificationUtils;
import com.quickblox.sample.core.utils.ResourceUtils;
import com.quickblox.sample.core.utils.SharedPrefsHelper;
import com.quickblox.sample.core.utils.constant.GcmConsts;
import com.quickblox.users.model.QBUser;

public class GcmPushListenerService extends CoreGcmPushListenerService {
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void showNotification(String message) {
        NotificationUtils.showNotification(this, SplashActivity.class,
                ResourceUtils.getString(R.string.notification_title), message,
                R.mipmap.ic_launcher, NOTIFICATION_ID);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        if (sharedPrefsHelper.hasQbUser()) {
            Log.d("App have logined user");
            QBUser qbUser = sharedPrefsHelper.getQbUser();
            startLoginService(qbUser);
        }
    }

    private void startLoginService(QBUser qbUser){
        CallService.start(this, qbUser);
    }

    @Override
    protected void sendPushMessageBroadcast(String message) {
        Intent gcmBroadcastIntent = new Intent(GcmConsts.ACTION_NEW_GCM_EVENT);
        gcmBroadcastIntent.putExtra(GcmConsts.EXTRA_GCM_MESSAGE, message);

        LocalBroadcastManager.getInstance(this).sendBroadcast(gcmBroadcastIntent);
    }
}