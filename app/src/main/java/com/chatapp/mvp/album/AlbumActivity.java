package com.chatapp.mvp.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class AlbumActivity extends BaseActivity implements AlbumMvp.View {


    @Bind(R.id.rv_list)
    RecyclerView rvList;

    private AlbumAdapter adapter;

    private AlbumMvp.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        adapter = new AlbumAdapter(this);
        rvList.setAdapter(adapter);

        presenter = new AlbumPresenterImpl(this);

    }
}
