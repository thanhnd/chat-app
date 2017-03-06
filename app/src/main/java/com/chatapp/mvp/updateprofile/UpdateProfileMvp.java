package com.chatapp.mvp.updateprofile;

import android.net.Uri;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UpdateProfileMvp {
    interface Interactor {

        void uploadAvatar(MultipartBody.Part filePart, ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback) throws RequireLoginException;
        void submit(Map<String, Object> request, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;
    }

    interface Presenter {
        void uploadAvatar(Uri url) throws RequireLoginException;

        void submit(Map<String, Object> request) throws RequireLoginException;

        CountryModel getCountry(int countryId);
    }

    interface View extends BaseView {
        void onUploadAvatarSuccess(String path);
        void onUploadAvatarFail();

        void onUpdateProfileSuccess();

        void onUpdateProfileFail();
    }
}
