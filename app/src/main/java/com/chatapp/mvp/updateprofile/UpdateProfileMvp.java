package com.chatapp.mvp.updateprofile;

import android.net.Uri;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UpdateProfileMvp {
    interface Interactor {

        void uploadAvatar(String authorization, MultipartBody.Part filePart, ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback);
        void submit(String authorization, Map<String, Object> request, ApiCallback<ResponseModel<Object>> callback);
    }

    interface Presenter {
        void uploadAvatar(Uri url);

        void submit(Map<String, Object> request);
    }

    interface View extends BaseView {
        void onUploadAvatarSuccess(String path);
        void onUploadAvatarFail();

        void onUpdateProfileSuccess();

        void onUpdateProfileFail();
    }
}
