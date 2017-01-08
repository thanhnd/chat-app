package com.chatapp.mvp.listfriends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseFragment;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.ItemOffsetDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListFriendsFragment extends BaseFragment implements ListFriendsMvp.View {

    @Bind(R.id.rv_list_nearby)
    RecyclerView rvNearby;

    private ListFriendsAdapter adapter;

    private ListFriendsMvp.Present present;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_nearby, container, false);

        ButterKnife.bind(this, view);
        rvNearby.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvNearby.setLayoutManager(layoutManager);
        rvNearby.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListFriendsAdapter(getContext());
        rvNearby.setAdapter(adapter);
        present = new PresentImpl(this);


        present.getListFriends();

        return view;
    }

    @Override
    public void onGetListFriendsSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

}