package com.chatapp.mvp.updatebasicprofile;

import android.net.Uri;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;
import com.google.gson.internal.LinkedTreeMap;

import okhttp3.MultipartBody;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UpdateBasicProfileMvp {
    interface Interactor {

        void submit(BasicProfileRequest request, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;

        void uploadAvatar(MultipartBody.Part filePart,
                          ApiCallback<ResponseModel<LinkedTreeMap<String, String>>> callback) throws RequireLoginException;

    }

    /**
     * Created by thanhnguyen on 12/19/16.
     */
    interface ProfilePresenter {
        void submit(BasicProfileRequest request) throws RequireLoginException;

        void uploadAvatar(Uri url) throws RequireLoginException;

        void getMyProfile() throws RequireLoginException;

    }

    /**
     * Created by thanhnguyen on 12/19/16.
     */
    interface View extends BaseView {

        void onUpdateBasicProfileSuccess();
        void onUpdateBasicProfileFail();

        void onUploadAvatarSuccess(String path);

        void onUploadAvatarFail();

        void onGetMyProfileSuccess(MyProfileModel resultSet);
    }
}
