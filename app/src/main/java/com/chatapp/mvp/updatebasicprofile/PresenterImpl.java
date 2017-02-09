package com.chatapp.mvp.updatebasicprofile;

import android.net.Uri;

import com.chatapp.MyApplication;
import com.chatapp.mvp.base.GeneralInteractor;
import com.chatapp.mvp.base.GeneralInteractorImmpl;
import com.chatapp.service.BaseApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.LogInModel;
import com.chatapp.service.models.response.MyProfileModel;
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

/**
 * Created by thanhnguyen on 12/19/16.
 */
public class PresenterImpl implements UpdateBasicProfileMvp.ProfilePresenter {

    private WeakReference<UpdateBasicProfileMvp.View> view;
    private UpdateBasicProfileMvp.Interactor interactor;
    private GeneralInteractor generalInteractor;

    public PresenterImpl(UpdateBasicProfileMvp.View view) {
        this.view = new WeakReference<>(view);
        this.interactor = new InteractorImpl();
        this.generalInteractor = new GeneralInteractorImmpl();
    }

    @Override
    public void submit(BasicProfileRequest request) {
        if (view.get() != null) {
            view.get().showProgress();
        }
        interactor.submit(request, new BaseApiCallback<ResponseModel<Object>>() {

            @Override
            public void onSuccess(ResponseModel<Object> response) {

                AccountUtils.setAccountStatus(LogInModel.VERIFIED);

                if (view.get() != null) {
                    view.get().hideProgress();
                    view.get().onUpdateBasicProfileSuccess();
                }
            }
        });
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
    public void getMyProfile() {
        generalInteractor.getMyProfile(new BaseApiCallback<ResponseModel<MyProfileModel>>() {
            @Override
            public void onSuccess(ResponseModel<MyProfileModel> response) {
                if (view.get() != null) {
                    AccountUtils.setMyProfileModel(response.getResultSet());
                    view.get().onGetMyProfileSuccess(response.getResultSet());
                }
            }
        });
    }
}
