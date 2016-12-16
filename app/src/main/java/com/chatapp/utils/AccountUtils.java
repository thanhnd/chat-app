package com.chatapp.utils;

import android.support.annotation.NonNull;

import com.chatapp.MyApplication;
import com.chatapp.service.models.response.SignInModel;

import static com.chatapp.utils.CacheUtil.getSharedPreferences;


/**
 * Created by thanhnguyen on 11/6/16.
 */

public class AccountUtils {
    private static final String SIGN_IN_MODEL = "sign_in_model";

    static private SignInModel signInModel;


    public static SignInModel getSignInModel() {
        return signInModel;
    }

    public static void setSignInModel(SignInModel loginInfo) {
        cacheSignInModel(loginInfo);
        AccountUtils.signInModel = loginInfo;
    }

    public static boolean isLoggedIn() {
        if (signInModel == null) {
            signInModel = getCacheSignInModel();
        }
        return (signInModel != null);
    }

    public static void logOut() {
        signInModel = null;
        getSharedPreferences(MyApplication.getInstance()).edit()
                .putString(SIGN_IN_MODEL, "").apply();
    }

    private static void cacheSignInModel(@NonNull SignInModel loginInfo) {
        getSharedPreferences(MyApplication.getInstance()).edit()
                .putString(SIGN_IN_MODEL, ParserUtil.toJson(loginInfo, SignInModel.class)).apply();
    }


    private static SignInModel getCacheSignInModel() {
        String str = getSharedPreferences(MyApplication.getInstance())
                .getString(SIGN_IN_MODEL, "");
        return ParserUtil.fromJson(str, SignInModel.class);
    }
}
