package com.chatapp.mvp.filterresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.base.BaseListUserAdapter;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.mvp.userprofile.UserProfileActivity;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilterResultActivity extends BaseActivity implements FilterResultMvp.View {

    public static final String ARG_QUERY = "arg_query";
    private FilterResultMvp.Presenter presenter;

    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.rv_list_users)
    RecyclerView rvListUsers;

    FilterResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListUsers.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null));
        rvListUsers.addItemDecoration(dividerItemDecoration);

        adapter = new FilterResultAdapter(this);
        adapter.setOnUserProfileItemClick(new BaseListUserAdapter.OnUserProfileItemClick() {
            @Override
            public void onItemClick(UserModel userModel) {
                navigateToUserProfile(userModel);
            }
        });
        rvListUsers.setAdapter(adapter);
        Intent intent = getIntent();
        Map query = (HashMap)intent.getSerializableExtra(ARG_QUERY);


        presenter = new FilterResultPresenterImpl(this);
        try {
            presenter.searchUser(query);
        } catch (RequireLoginException e) {
            Log.e(e);
            onTokenExpired();
        }

    }

    @Override
    public void onSearchSuccess(List<UserModel> resultSet) {
        if (resultSet != null) {
            adapter.add(resultSet);
        }

        rvListUsers.setVisibility(adapter.getItemCount() != 0 ? View.VISIBLE : View.GONE);
        tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void navigateToUserProfile(UserModel userModel) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(UserProfileActivity.EXTRA_USER_MODEL, userModel);
        startActivity(intent);
    }
}
