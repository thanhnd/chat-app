package com.chatapp.mvp.updatebasicprofile;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.BasicProfileRequest;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 1/8/17.
 */

public interface UpdateBasicProfileMvp {
    interface UpdateBasicProfileInteractor {

        void submit(BasicProfileRequest request, AuthorizeApiCallback<ResponseModel<Object>> apiCallback);
    }

    /**
     * Created by thanhnguyen on 12/19/16.
     */
    interface UpdateBasicProfilePresenter {
        void submit(BasicProfileRequest request);
    }

    /**
     * Created by thanhnguyen on 12/19/16.
     */
    interface UpdateBasicProfileView extends BaseView {

        void onUpdateBasicProfileSuccess();
        void onUpdateBasicProfileFail();
    }
}
