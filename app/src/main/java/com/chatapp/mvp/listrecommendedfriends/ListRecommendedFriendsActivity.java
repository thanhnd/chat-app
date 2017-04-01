package com.chatapp.mvp.listrecommendedfriends;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.mvp.userprofile.UserProfileActivity;
import com.chatapp.service.models.response.UserModel;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListRecommendedFriendsActivity extends BaseActivity implements
        ListRecommendedFriendsMvp.View, ListRecommendedFriendsAdapter.OnSelectedChangedListener {

    @Bind(R.id.rv_list)
    RecyclerView recyclerView;

    @Bind(R.id.v_action)
    View vAction;

    @Bind((R.id.btn_select_all))
    Button btnSelectAll;

    @Bind((R.id.btn_delete))
    Button btnDelete;

    private ListRecommendedFriendsAdapter adapter;
    private ListRecommendedFriendsMvp.Presenter presenter;
    private MenuItem menuEdit;
    private boolean isEditMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_recommended_friends);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        btnDelete.setPaintFlags(btnDelete.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnSelectAll.setPaintFlags(btnSelectAll.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
                try {
                    presenter.acceptFriendRequest(userModel.getUserId());
                } catch (RequireLoginException e) {
                    onRequiredLogin();
                }
            }
        });

        adapter.setOnSelectedChanged(this);
        recyclerView.setAdapter(adapter);
        presenter = new PresenterImpl(this);
        try {
            presenter.getListRecommendedFriends();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }

    }

    @Override
    public void onGetListRecommendedFriendsSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

    @Override
    public void onAcceptFriendSuccess() {
        try {
            adapter.clearData();
            presenter.getListRecommendedFriends();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }

    @Override
    public void onDeleteRecommendsSuccess() {
        try {
            adapter.clearData();
            presenter.getListRecommendedFriends();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_recommend, menu);
        menuEdit = menu.findItem(R.id.action_edit);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                toggleEditMode();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_select_all)
    public void onClickSellectAll() {
        adapter.selectAll();
    }

    @OnClick(R.id.btn_delete)
    public void onClickDelete() {
        Set<UserModel> userModels = adapter.getmSelectedItems();
        if (userModels.size() > 0) {
            try {
                presenter.deleteRecommends(userModels);
            } catch (RequireLoginException e) {
                onRequiredLogin();
            }
        }
    }

    private void toggleEditMode() {

        if (!isEditMode) {

            menuEdit.setTitle(R.string.menu_cancel);
            adapter.setEditMode(true);
            vAction.setVisibility(View.VISIBLE);

        } else {

            menuEdit.setTitle(R.string.menu_edit);
            adapter.setEditMode(false);
            vAction.setVisibility(View.GONE);
        }

        isEditMode = !isEditMode;

    }

    @Override
    public void onSelectedChange(Set<UserModel> userModels) {
        if (userModels.size() > 0) {
            btnDelete.setText(String.format(Locale.getDefault(), "%s (%d)",
                    getString(R.string.delete), userModels.size()));
        } else {
            btnDelete.setText(getString(R.string.delete));
        }
    }
}
