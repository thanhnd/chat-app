package com.chatapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thanhnguyen on 12/11/16.
 */

public class CacheUtil {
    public static final String PREFS_NAME = "chat_app";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
