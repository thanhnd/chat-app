package com.chatapp.mvp.updatebasicprofile;

import com.chatapp.service.models.request.BasicProfileRequest;

/**
 * Created by thanhnguyen on 12/19/16.
 */
public interface UpdateBasicProfilePresenter {
    void submit(BasicProfileRequest request);
}
