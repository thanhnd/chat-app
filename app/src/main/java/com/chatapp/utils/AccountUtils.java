package com.chatapp.utils;

import android.support.annotation.NonNull;

import com.chatapp.MyApplication;
import com.chatapp.service.models.response.LogInModel;

import static com.chatapp.utils.CacheUtil.getSharedPreferences;


/**
 * Created by thanhnguyen on 11/6/16.
 */

public class AccountUtils {
    private static final String SIGN_IN_MODEL = "sign_in_model";

    static private LogInModel logInModel;


    public static LogInModel getLogInModel() {
        return logInModel;
    }

    public static void setLogInModel(LogInModel loginInfo) {
        cacheSignInModel(loginInfo);
        AccountUtils.logInModel = loginInfo;
    }

    public static boolean isLoggedIn() {
        if (logInModel == null) {
            logInModel = getCacheSignInModel();
        }
        return (logInModel != null);
    }

    public static void logOut() {
        logInModel = null;
        getSharedPreferences(MyApplication.getInstance()).edit()
                .putString(SIGN_IN_MODEL, "").apply();
    }

    private static void cacheSignInModel(@NonNull LogInModel loginInfo) {
        getSharedPreferences(MyApplication.getInstance()).edit()
                .putString(SIGN_IN_MODEL, ParserUtil.toJson(loginInfo, LogInModel.class)).apply();
    }


    private static LogInModel getCacheSignInModel() {
        String str = getSharedPreferences(MyApplication.getInstance())
                .getString(SIGN_IN_MODEL, "");
        return ParserUtil.fromJson(str, LogInModel.class);
    }

    public static String getHiddenPhone(String phone) {
        StringBuilder builder = new StringBuilder();
        if (phone.length() > 3) {
            builder.append(phone.substring(0, 3));

            int i = 3;
            for (; i < phone.length() && i < 6; i ++) {
                builder.append("*");
            }
            if (i < phone.length()) {
                builder.append(phone.substring(i, phone.length()));
            }
        }

        return builder.toString();

    }
}
