
package com.chatapp.mvp.updateprofile;

import android.net.Uri;
import android.text.TextUtils;

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
    public void uploadAvatar(Uri url) {

        LogInModel logInModel = AccountUtils.getLogInModel();
        if (logInModel == null) {
            return;
        }
        String authorization = logInModel.getToken();

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

        interactor.uploadAvatar(authorization, filePart, new AuthorizeApiCallback<ResponseModel<LinkedTreeMap<String, String>>>() {
            @Override
            public void onTokenExpired() {

            }

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

            @Override
            public void onFail(Response<ResponseModel<LinkedTreeMap<String, String>>> response) {
                if (view.get() != null) {
                    view.get().onUploadAvatarFail();
                }
            }

            @Override
            public void onFail(Call<ResponseModel<LinkedTreeMap<String, String>>> call, Throwable throwable) {
                if (view.get() != null) {
                    view.get().onUploadAvatarFail();
                }
            }
        });
    }

    @Override
    public void submit(Map<String, Object> request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.submit(request, new AuthorizeApiCallback<ResponseModel<Object>>() {
            @Override
            public void onTokenExpired() {
                if (view.get() != null) {
                    view.get().hideProgress();
                }
            }

            @Override
            public void onSuccess(ResponseModel<Object> response) {

                AccountUtils.setAccountStatus(LogInModel.VERIFIED);

                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onUpdateProfileSuccess();
                }
            }

            @Override
            public void onFail(Response<ResponseModel<Object>> response) {
                if (view.get() != null) {
                    view.get().hideProgress();

                    // Get data response from server
                    ResponseModel responseModel = response.body();

                    // Show error message from server if there is
                    if (responseModel != null && !TextUtils.isEmpty(responseModel.getResponseMsg())) {
                        view.get().showErrorDialog(responseModel.getResponseMsg());
                    } else {
                        view.get().onUpdateProfileFail();
                    }
                }
            }

            @Override
            public void onFail(Call<ResponseModel<Object>> call, Throwable throwable) {
                if (view != null) {
                    view.get().showErrorDialog();
                    view.get().hideProgress();
                }
            }
        });
    }
}
