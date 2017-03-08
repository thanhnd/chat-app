package com.chatapp;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.chatapp.chat.utils.Consts;
import com.crashlytics.android.Crashlytics;
import com.quickblox.sample.core.CoreApp;

import io.fabric.sdk.android.Fabric;

/**
 * Created by thanhnguyen on 11/27/16.
 */

public class MyApplication extends CoreApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        initCredentials(Consts.QB_APP_ID, Consts.QB_AUTH_KEY, Consts.QB_AUTH_SECRET, Consts.QB_ACCOUNT_KEY);
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
            MultiDex.install(this);
        } catch (RuntimeException ex) {
            //Catch nothing
        }
    }
}
