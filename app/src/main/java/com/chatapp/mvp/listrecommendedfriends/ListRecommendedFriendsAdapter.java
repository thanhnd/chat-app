package com.chatapp.mvp.listrecommendedfriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseListUserAdapter;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class ListRecommendedFriendsAdapter extends BaseListUserAdapter<ListRecommendedFriendsAdapter.ViewHolder> {


    private Set<UserModel> mSelecteds;
    private OnUserProfileItemClick onUserProfileItemClick;
    private OnAddFriendClick onAddFriendClick;
    private boolean isEditMode;

    private OnSelectedChangedListener onSelectedChanged;

    public void setOnUserProfileItemClick(OnUserProfileItemClick onUserProfileItemClick) {
        this.onUserProfileItemClick = onUserProfileItemClick;
    }

    public void setOnAddFriendClick(OnAddFriendClick onAddFriendClick) {
        this.onAddFriendClick = onAddFriendClick;
    }

    public void selectAll() {
        for (UserModel userModel: mDataset) {
            mSelecteds.add(userModel);
        }

        notifyDataSetChanged();
        onSelectedChanged.onSelectedChange(mSelecteds);
    }

    public void setOnSelectedChanged(OnSelectedChangedListener onSelectedChanged) {
        this.onSelectedChanged = onSelectedChanged;
    }

    public interface OnSelectedChangedListener {
        void onSelectedChange(Set<UserModel> userModels);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_online_status) TextView tvOnlineStatus;
        @Bind(R.id.tv_noted) TextView tvNoted;
        @Bind(R.id.ib_add_recommended_friend) ImageButton ibAddFriend;
        @Bind(R.id.cb_selected) CheckBox cbSelectedItem;

        public ViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public ListRecommendedFriendsAdapter(Context context) {
        super(context);
        mSelecteds = new HashSet<>();
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
        String name = userModel.getDisplayNameStr();
        holder.tvName.setText(name);
        holder.tvOnlineStatus.setText(userModel.getOnlineStatus());
        holder.tvOnlineStatus.setEnabled(userModel.isOnline());
        holder.tvNoted.setText(String.format(Locale.getDefault(), "\"%s\"", userModel.getNoted()));

        if (!TextUtils.isEmpty(userModel.getAvatar())) {
            Picasso.with(context)
                    .load(userModel.getAvatar())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .transform(new CircleTransform())
                    .into(holder.ivAvatar);
        } else {
            Picasso.with(context)
                    .load(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .into(holder.ivAvatar);
        }

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

        holder.cbSelectedItem.setChecked(mSelecteds.contains(userModel));
        holder.cbSelectedItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                if (checkBox.isChecked()) {
                    mSelecteds.add(userModel);
                } else {
                    mSelecteds.remove(userModel);
                }

                if (onSelectedChanged != null) {
                    onSelectedChanged.onSelectedChange(mSelecteds);
                }
            }
        });

        holder.cbSelectedItem.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        holder.ibAddFriend.setVisibility(!isEditMode ? View.VISIBLE : View.GONE);
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

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        notifyDataSetChanged();
    }

    public Set<UserModel> getmSelectedItems() {
        return mSelecteds;
    }
}
