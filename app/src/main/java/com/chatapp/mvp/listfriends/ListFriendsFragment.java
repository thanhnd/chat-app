package com.chatapp.mvp.listfriends;

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
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.mvp.userprofile.UserProfileActivity;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.ItemOffsetDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListFriendsFragment extends BaseFragment implements ListFriendsMvp.View {

    @Bind(R.id.rv_list_users)
    RecyclerView recyclerView;

    private ListFriendsAdapter adapter;

    private ListFriendsMvp.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_friends, container, false);

        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListFriendsAdapter(getContext());
        adapter.setOnUserProfileItemClick(new ListFriendsAdapter.OnUserProfileItemClick() {
            @Override
            public void onItemClick(UserModel userModel) {
                navigateToUserProfile(userModel);
            }
        });
        recyclerView.setAdapter(adapter);

        presenter = new PresenterImpl(this);

        try {
            presenter.getListFriends();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }

        return view;
    }

    @Override
    public void onGetListFriendsSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }
}
