package com.chatapp.mvp.listnearby;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class ListNearbyAdapter extends RecyclerView.Adapter<ListNearbyAdapter.ViewHolder> {
    private ArrayList<UserModel> mDataset;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_distance) TextView tvDistance;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ListNearbyAdapter(Context context) {
        mDataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ListNearbyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_nearby, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserModel userModel = mDataset.get(position);
        String name = TextUtils.isEmpty(userModel.getDisplayName()) ? "No name" : userModel.getDisplayName();
        holder.tvName.setText(name);
        holder.tvDistance.setText(String.format(Locale.getDefault(), "%d feet away", userModel.getFeetAway()));
        Picasso.with(context)
                .load(userModel.getAvatar())
                .error(R.drawable.img_user_avatar)
                .placeholder(R.drawable.img_user_avatar)
                .transform(new CircleTransform())
                .into(holder.ivAvatar);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(List<UserModel> userModels) {
        int position = mDataset.size();
        mDataset.addAll(userModels);
        notifyItemInserted(position);
    }
}