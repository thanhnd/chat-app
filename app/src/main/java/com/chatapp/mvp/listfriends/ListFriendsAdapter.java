package com.chatapp.mvp.listfriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseListUserAdapter;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class ListFriendsAdapter extends BaseListUserAdapter<ListFriendsAdapter.ViewHolder> {

    private ArrayList<UserModel> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_online_status) TextView tvOnlineStatus;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;

        public ViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public ListFriendsAdapter(Context context) {
        super(context);
        mDataset = new ArrayList<>();
    }

    @Override
    public ListFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_friends, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserModel userModel = mDataset.get(position);
        String name = TextUtils.isEmpty(userModel.getDisplayName()) ? "No name" : userModel.getDisplayName();
        holder.tvName.setText(name);
        holder.tvOnlineStatus.setText(userModel.getOnlineStatus());
        holder.tvOnlineStatus.setEnabled(userModel.isOnline());
        Picasso.with(context)
                .load(userModel.getAvatar())
                .resize(imgDiameter, imgDiameter)
                .error(R.drawable.img_user_avatar)
                .placeholder(R.drawable.img_user_avatar)
                .transform(new CircleTransform())
                .into(holder.ivAvatar);
        holder.vItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUserProfileItemClick != null) {
                    onUserProfileItemClick.onItemClick(userModel);
                }
            }
        });
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
