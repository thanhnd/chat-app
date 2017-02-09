package com.chatapp.mvp.listnearby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseFragment;
import com.chatapp.mvp.home.HomeActivity;
import com.chatapp.mvp.myprofile.MyProfileActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.mvp.userprofile.UserProfileActivity;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.ItemOffsetDecoration;
import com.chatapp.utils.LocationChangeObservable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListNearbyFragment extends BaseFragment implements ListNearbyMvp.View, LocationChangeObservable {

    @Bind(R.id.rv_list_users)
    RecyclerView recyclerView;

    private ListNearbyAdapter adapter;
    private ListNearbyMvp.Present present;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_nearby, container, false);

        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListNearbyAdapter(getContext());
        adapter.setOnMyProfileItemClick(new ListNearbyAdapter.OnMyProfileItemClick() {
            @Override
            public void onItemClick() {
                navigateToMyProfile();
            }
        });
        adapter.setOnUserProfileItemClick(new ListNearbyAdapter.OnUserProfileItemClick() {
            @Override
            public void onItemClick(UserModel userModel) {
                navigateToUserProfile(userModel);
            }
        });
        recyclerView.setAdapter(adapter);

        present = new PresentImpl(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            present.getMyProfile();
            present.getListNearBy();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }

    }

    @Override
    public void onGetListNearbySuccess(List<UserModel> userModels) {
        adapter.add(userModels, true);
    }

    @Override
    public void onGetMyProfileSuccess(MyProfileModel myProfileModel) {
        AccountUtils.setMyProfileModel(myProfileModel);
        adapter.setMyProfileModel(myProfileModel);
    }

    @Override
    public void navigateToMyProfile() {
        Intent intent = new Intent(getContext(), MyProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }

    @Override
    public void updateLocation(double lattitude, double longitude) {

        try {
            present.getListNearBy();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    @Override
    public void onAttach(Context context) {

        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.addToLocationChangeObservableList(this);

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.removeFromLocationChangeObservableList(this);

        super.onDetach();
    }
}
