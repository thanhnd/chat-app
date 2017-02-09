package com.chatapp.mvp.home;

import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.ApiCallback;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface HomeMvp {

    interface Present {
        void updateLocation(double latitude, double longitude) throws RequireLoginException;
    }

    interface Interactor {
        void updatLocation(double latitude, double longitude, ApiCallback<ResponseModel<Object>> callback) throws RequireLoginException;
    }

    interface View {
    }
}
