package com.chatapp.mvp.listnearby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseFragment;
import com.chatapp.service.models.response.ListNearByModel;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.utils.ItemOffsetDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/3/17.
 */

public class ListNearbyFragment extends BaseFragment implements ListNearbyMvp.View{

    @Bind(R.id.rv_list_nearby)
    RecyclerView rvNearby;

    private ListNearbyAdapter adapter;

    private ListNearbyMvp.Present present;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_nearby, container, false);

        ButterKnife.bind(this, view);
        rvNearby.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvNearby.setLayoutManager(layoutManager);
        rvNearby.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.list_item_space));
        adapter = new ListNearbyAdapter(getContext());
        rvNearby.setAdapter(adapter);

        present = new PresentImpl(this);
        present.getListNearBy();
        present.getMyProfile();

        return view;
    }

    @Override
    public void onGetListNearbySuccess(ListNearByModel resultSet) {
        adapter.add(resultSet.getUserModels());
    }

    @Override
    public void onGetMyProfileSuccess(MyProfileModel myProfileModel) {
        adapter.setMyProfileModel(myProfileModel);
    }
}
