package com.chatapp.mvp.listnearby;

import com.chatapp.mvp.base.BaseView;
import com.chatapp.service.AuthorizeApiCallback;
import com.chatapp.service.models.request.ListNearbyRequest;
import com.chatapp.service.models.response.ListNearByModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.ResponseModel;

/**
 * Created by thanhnguyen on 12/17/16.
 */
public interface ListNearbyMvp {

    interface Present {
        void getListNearBy();

        void getMyProfile();
    }

    interface Interactor {
        void getListNearBy(ListNearbyRequest request, AuthorizeApiCallback<ResponseModel<ListNearByModel>> callback);
        void getMyProfile(AuthorizeApiCallback<ResponseModel<MyProfileModel>> callback);
    }

    interface View extends BaseView {
        void onGetListNearbySuccess(ListNearByModel resultSet);
        void onGetMyProfileSuccess(MyProfileModel myProfileModel);
    }
}
