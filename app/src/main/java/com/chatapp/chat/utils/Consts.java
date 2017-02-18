package com.chatapp.chat.utils;

import com.chatapp.R;
import com.quickblox.sample.core.utils.ResourceUtils;

public interface Consts {
    // In GCM, the Sender ID is a project ID that you acquire from the API console
    String GCM_SENDER_ID = "611078040695";

    String QB_APP_ID = "52654";
    String QB_AUTH_KEY = "XsLRhxvAVjzJGLB";
    String QB_AUTH_SECRET = "J972pjTtSBChmZv";
    String QB_ACCOUNT_KEY = "ZntquHfRXxByqdsRp1CN";

    String QB_USERS_TAG = "test";
    String QB_USERS_PASSWORD = "12341234";

    int PREFERRED_IMAGE_SIZE_PREVIEW = ResourceUtils.getDimen(R.dimen.chat_attachment_preview_size);
    int PREFERRED_IMAGE_SIZE_FULL = ResourceUtils.dpToPx(320);
}