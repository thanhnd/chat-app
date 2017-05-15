package com.chatapp.chat.utils.qb;

import android.text.TextUtils;

import com.quickblox.auth.QBAuth;
import com.quickblox.core.exception.BaseServiceException;

import java.util.Date;

public class QbAuthUtils {

    public static boolean isSessionActive() {
        try {
            String token = QBAuth.getBaseService().getToken();
            Date expirationDate = QBAuth.getBaseService().getTokenExpirationDate();

            if (TextUtils.isEmpty(token)) {
                return false;
            }

            return System.currentTimeMillis() < expirationDate.getTime();

        } catch (BaseServiceException ignored) {
        }

        return false;
    }

}
