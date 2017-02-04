package com.chatapp.mvp.listrecommendedfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.userprofile.UserProfileActivity;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListRecommendedFriendsActivity extends BaseActivity implements ListRecommendedFriendsMvp.View{

    @Bind(R.id.rv_list)
    RecyclerView recyclerView;

    private ListRecommendedFriendsAdapter adapter;

    private ListRecommendedFriendsMvp.Present present;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_recommended_friends);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListRecommendedFriendsAdapter(this);
        adapter.setOnUserProfileItemClick(new ListRecommendedFriendsAdapter.OnUserProfileItemClick() {
            @Override
            public void onItemClick(UserModel userModel) {
                navigateToUserProfile(userModel);
            }
        });

        adapter.setOnAddFriendClick(new ListRecommendedFriendsAdapter.OnAddFriendClick() {
            @Override
            public void onItemClick(UserModel userModel) {
                present.acceptFriendRequest(userModel.getUserId());
            }
        });
        recyclerView.setAdapter(adapter);
        present = new PresentImpl(this);
        present.getListRecommendedFriends();
    }

    @Override
    public void onGetListRecommendedFriendsSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

    @Override
    public void onAcceptFriendSuccess() {

    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }
}
