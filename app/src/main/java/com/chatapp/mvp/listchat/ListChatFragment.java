package com.chatapp.mvp.listchat;

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
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.ItemOffsetDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListChatFragment extends BaseFragment implements ListChatMvp.View{

    @Bind(R.id.rv_list_users)
    RecyclerView rvNearby;

    private ListChatAdapter adapter;

    private ListChatMvp.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_nearby, container, false);

        ButterKnife.bind(this, view);
        rvNearby.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvNearby.setLayoutManager(layoutManager);
        rvNearby.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListChatAdapter(getContext());
        rvNearby.setAdapter(adapter);
        presenter = new PresenterImpl(this);

        try {
            presenter.getListChat();
        } catch (RequireLoginException e) {
            onRequiredLogin();
        }

        return view;
    }

    @Override
    public void onGetListChatSuccess(List<UserModel> resultSet) {
        adapter.add(resultSet);
    }

}
