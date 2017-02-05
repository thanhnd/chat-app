package com.chatapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chatapp.service.models.response.ListParamsModel;

/**
 * Created by thanhnguyen on 12/11/16.
 */

public class CacheUtil {
    public static final String PREFS_NAME = "chat_app";
    private static ListParamsModel listParamsModel;

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public static void setListParamsModel(ListParamsModel listParamsModel) {
        CacheUtil.listParamsModel = listParamsModel;
    }

    public static ListParamsModel getListParamsModel() {
        return listParamsModel;
    }
}
