package com.chatapp.mvp.listrecommendedfriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class ListRecommendedFriendsAdapter extends RecyclerView.Adapter<ListRecommendedFriendsAdapter.ViewHolder> {
    private ArrayList<UserModel> mDataset;
    private Context context;
    private OnUserProfileItemClick onUserProfileItemClick;
    private OnAddFriendClick onAddFriendClick;

    public void setOnUserProfileItemClick(OnUserProfileItemClick onUserProfileItemClick) {
        this.onUserProfileItemClick = onUserProfileItemClick;
    }

    public void setOnAddFriendClick(OnAddFriendClick onAddFriendClick) {
        this.onAddFriendClick = onAddFriendClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_online_status) TextView tvOnlineStatus;
        @Bind(R.id.tv_noted) TextView tvNoted;
        @Bind(R.id.ib_add_recommended_friend)
        ImageButton ibAddFriend;


        public ViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public ListRecommendedFriendsAdapter(Context context) {
        mDataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ListRecommendedFriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_recommended_friends, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserModel userModel = mDataset.get(position);
        String name = TextUtils.isEmpty(userModel.getDisplayName()) ? "No name" : userModel.getDisplayName();
        holder.tvName.setText(name);
        holder.tvOnlineStatus.setText(userModel.isOnline() ? "Online" : "Offline");
        holder.tvOnlineStatus.setEnabled(userModel.isOnline());
        holder.tvNoted.setText(String.format(Locale.getDefault(), "\"%s\"", userModel.getNoted()));
        Picasso.with(context)
                .load(userModel.getAvatar())
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

        holder.ibAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAddFriendClick != null) {
                    onAddFriendClick.onItemClick(userModel);
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

    public interface OnUserProfileItemClick {
        void onItemClick(UserModel userModel);
    }

    public interface OnAddFriendClick {
        void onItemClick(UserModel userModel);
    }

}