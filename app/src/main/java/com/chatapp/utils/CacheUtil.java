package com.chatapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chatapp.MyApplication;
import com.chatapp.service.models.response.ListParamsModel;
import com.chatapp.service.models.response.ParamModel;

import java.util.List;

/**
 * Created by thanhnguyen on 12/11/16.
 */

public class CacheUtil {
    public static final String PREFS_NAME = "chat_app";
    private static ListParamsModel listParamsModel;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public static void setListParamsModel(ListParamsModel listParamsModel) {
        CacheUtil.listParamsModel = listParamsModel;
    }

    public static ParamModel getParam(int key, List<ParamModel> paramModels) {

        if (paramModels == null) {
            return  null;
        }

        for (ParamModel paramModel: paramModels) {
            if (paramModel.getId() == key) {
                return paramModel;
            }
        }

        return null;
    }

    public static ListParamsModel getListParamsModel() {
        return listParamsModel;
    }

    public static void save(String key, String value) {
        getSharedPreferences(MyApplication.getInstance())
                .edit().putString(key, value).apply();
    }

    public static String get(String key) {
        return getSharedPreferences(MyApplication.getInstance()).getString(key, "");
    }

    public static void remove(String key) {
        getSharedPreferences(MyApplication.getInstance()).edit().remove(key).apply();
    }
}
