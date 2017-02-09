package com.chatapp.mvp.listfavorites;

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

public class ListFavoritesFragment extends BaseFragment implements ListFavoritesMvp.View{

    @Bind(R.id.rv_list_users)
    RecyclerView recyclerView;

    private ListFavoritesAdapter adapter;

    private ListFavoritesMvp.Present present;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_favorites, container, false);

        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListFavoritesAdapter(getContext());
        adapter.setOnUserProfileItemClick(new ListFavoritesAdapter.OnUserProfileItemClick() {
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

        adapter.clearData();
        try {
            present.getListFavorites(true);
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    @Override
    public void onGetListFavoritesSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }

}
