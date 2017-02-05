
package com.chatapp.mvp.updateprofile;

import android.net.Uri;

import com.chatapp.MyApplication;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.ResponseModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.FileUtils;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class PresenterImpl implements UpdateProfileMvp.Presenter {

    private WeakReference<UpdateProfileMvp.View> view;
    private UpdateProfileMvp.Interactor interactor;

    public PresenterImpl(UpdateProfileMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
    }

    @Override
    public void uploadAvatar(Uri fileUri) {

        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

        File file = FileUtils.getFile(MyApplication.getInstance(), fileUri);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(MyApplication.getInstance().getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        interactor.uploadAvatar(authorization, filePart, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onTokenExpired() {

            }

            @Override
            public void onSuccess(ResponseModel<Object> response) {
                if (view.get() != null) {
                    try {
                        Map<String, String> responseResult = (LinkedTreeMap<String, String>) response.getResultSet();
                        String path = responseResult.get("url") + responseResult.get("avatar");
                        view.get().onUploadAvatarSuccess(path);
                    } catch (ClassCastException ex) {
                        view.get().showErrorDialog();
                    }
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {
                if (view.get() != null) {
                    view.get().onUploadAvatarFail();
                }
            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {
                if (view.get() != null) {
                    view.get().onUploadAvatarFail();
                }
            }
        });
    }
}
