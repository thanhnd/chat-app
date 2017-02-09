
package com.chatapp.mvp.updateprofile;

import android.net.Uri;

import com.chatapp.MyApplication;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.FileUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PresenterImpl implements UpdateProfileMvp.Presenter {

    private WeakReference<UpdateProfileMvp.View> view;
    private UpdateProfileMvp.Interactor interactor;

    public PresenterImpl(UpdateProfileMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
    }

    @Override
    public void uploadAvatar(Uri url) {
        File file = FileUtils.getFile(MyApplication.getInstance(), url);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(MyApplication.getInstance().getContentResolver().getType(url)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        interactor.uploadAvatar(filePart, new BaseApiCallback<ResponseModel<LinkedTreeMap<String, String>>>() {

            @Override
            public void onSuccess(ResponseModel<LinkedTreeMap<String, String>> response) {
                if (view.get() != null) {
                    try {
                        Map<String, String> responseResult = response.getResultSet();
                        String path = responseResult.get("url");
                        view.get().onUploadAvatarSuccess(path);
                    } catch (ClassCastException ex) {
                        view.get().showErrorDialog();
                    }
                }
            }
        });
    }

    @Override
    public void submit(Map<String, Object> request) {
        if (view.get() != null) {
            view.get().showProgress();
        }

        interactor.submit(request, new BaseApiCallback<ResponseModel<Object>>() {

            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onUpdateProfileSuccess();
                }
            }
        });
    }
}
