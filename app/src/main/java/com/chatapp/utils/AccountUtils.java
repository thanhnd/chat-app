package com.chatapp.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;


/**
 * Created by thanhnguyen on 11/6/16.
 */

public class AccountUtils {
    private static final String SIGN_IN_MODEL = "sign_in_model";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    static private volatile LogInModel logInModel;
    private static MyProfileModel myProfileModel;

    static private Double longitude;
    static private Double latitude;

    private static String phone;
    private static String email;

    public static LogInModel getLogInModel() {
        if (logInModel == null) {
            synchronized (AccountUtils.class) {
                if (logInModel == null) {
                    logInModel = getCacheSignInModel();
                }
            }
        }
        return logInModel;
    }

    public static void setLogInModel(LogInModel loginInfo) {
        AccountUtils.logInModel = loginInfo;
        cacheLogInModel(loginInfo);
    }

    public static void setAccountStatus(int status) {
        logInModel.setIsActive(status);
        cacheLogInModel(logInModel);
    }

    public static boolean isLoggedIn() {
        return (getLogInModel() != null);
    }

    public static void logOut() {
        logInModel = null;
        CacheUtil.remove(SIGN_IN_MODEL);
    }

    private static void cacheLogInModel(@NonNull LogInModel loginInfo) {
        CacheUtil.save(SIGN_IN_MODEL, ParserUtil.toJson(loginInfo, LogInModel.class));
    }

    private static LogInModel getCacheSignInModel() {

        String str = CacheUtil.get(SIGN_IN_MODEL);
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

    public static void setMyProfileModel(MyProfileModel myProfileModel) {
        AccountUtils.myProfileModel = myProfileModel;
    }

    public static MyProfileModel getMyProfileModel() {
        return myProfileModel;
    }

    public static Double getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        AccountUtils.longitude = longitude;
    }

    public static Double getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        AccountUtils.latitude = latitude;
    }

    public static String getAuthorization() throws RequireLoginException {
        LogInModel logInModel = getLogInModel();
        if (logInModel == null) {
            throw new RequireLoginException();
        }
        return logInModel.getToken();
    }

    public static String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            phone = CacheUtil.get(PHONE);
        }

        return phone;
    }

    public static void setPhone(String phone) {
        AccountUtils.phone = phone;
        CacheUtil.save(PHONE, phone);
    }

    public static String getEmail() {
        if (TextUtils.isEmpty(email)) {
            email = CacheUtil.get(EMAIL);
        }

        return email;
    }

    public static void setEmail(String email) {
        AccountUtils.email = email;
        CacheUtil.save(EMAIL, email);
    }
}
