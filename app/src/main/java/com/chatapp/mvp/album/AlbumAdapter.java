package com.chatapp.mvp.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chatapp.R;
import com.chatapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        @Bind(R.id.iv_album)
        ImageView ivAlbum;

        public ViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public AlbumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_album, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AlbumAdapter.ViewHolder vh = new AlbumAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AlbumAdapter.ViewHolder holder, final int position) {
        Picasso.with(context)
                .load("http://appname.wwwebapp.com/images/user/05/1494716289.jpg")
                .fit()
                .centerCrop()
                .error(R.drawable.img_user_avatar)
                .placeholder(R.drawable.img_user_avatar)
                .transform(new CircleTransform())
                .into(holder.ivAlbum);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        int size = 100;
        return size;
    }
}
