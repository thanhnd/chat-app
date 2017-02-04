package com.chatapp.mvp.home;

import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface HomeMvp {

    interface Present {
        void updateLocation(double latitude, double longitude);
    }

    interface Interactor {
        void updatLocation(double latitude, double longitude, AuthorizeApiCallback<ResponseModel<Object>>  callback);
    }

    interface View {
    }
}
