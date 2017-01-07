package com.chatapp;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by thanhnguyen on 11/27/16.
 */

public class MyApplication extends MultiDexApplication {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
