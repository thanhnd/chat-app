package com.chatapp.mvp.updateprofile;

import android.net.Uri;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.MultipartBody;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UpdateProfileMvp {
    interface Interactor {

        void uploadAvatar(String authorization, MultipartBody.Part filePart, AuthorizeApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback);
    }

    interface Presenter {
        void uploadAvatar(Uri url);
    }

    interface View extends BaseView {
        void onUploadAvatarSuccess(String path);
        void onUploadAvatarFail();
    }
}
