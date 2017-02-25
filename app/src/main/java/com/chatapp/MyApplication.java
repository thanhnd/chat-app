package com.chatapp;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.quickblox.sample.core.CoreApp;

/**
 * Created by thanhnguyen on 11/27/16.
 */

public class MyApplication extends CoreApp {

    @Override
    public void onCreate() {
        super.onCreate();

        initCredentials(Config.QB_APP_ID, Config.QB_AUTH_KEY, Config.QB_AUTH_SECRET, Config.QB_ACCOUNT_KEY);
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
