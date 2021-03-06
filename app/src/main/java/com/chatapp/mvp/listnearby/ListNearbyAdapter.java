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
import com.chatapp.mvp.base.BaseListUserAdapter;
import com.chatapp.service.models.response.MyProfileModel;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.CircleTransform;
import com.chatapp.utils.Log;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 1/6/17.
 */

public class ListNearbyAdapter extends BaseListUserAdapter {
    public static final int ITEM_VIEW_TYPE_MY_PROFILE = 0;
    public static final int ITEM_VIEW_TYPE_OTHER_PROFILE = 1;
    private OnMyProfileItemClick onMyProfileItemClick;
    private MyProfileModel myProfile;

    public void setOnMyProfileItemClick(OnMyProfileItemClick onMyProfileItemClick) {
        this.onMyProfileItemClick = onMyProfileItemClick;
    }

    public class OtherUserViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_distance) TextView tvDistance;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;
        @Bind(R.id.iv_favorite) ImageView ivFavorite;
        @Bind(R.id.iv_friend) ImageView ivFriend;
        @Bind(R.id.iv_block) ImageView ivBlock;
        View vItem;

        public OtherUserViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public static class MyProfileViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;
        View vItem;
        public MyProfileViewHolder(View v) {
            super(v);
            this.vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public ListNearbyAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        if (viewType == ITEM_VIEW_TYPE_MY_PROFILE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.item_list_nearby_my_profile, parent, false);
            return new MyProfileViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate( R.layout.item_list_nearby, parent, false);
            return new OtherUserViewHolder(v);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == ITEM_VIEW_TYPE_MY_PROFILE) {

            MyProfileViewHolder myProfileViewHolder = (MyProfileViewHolder)holder;
            String name = myProfile.getDisplayNameStr();
            myProfileViewHolder.tvName.setText(name);

            if (!TextUtils.isEmpty(myProfile.getAvatar())) {
                Picasso.with(context)
                        .load(myProfile.getAvatar())
                        .fit()
                        .centerCrop()
                        .error(R.drawable.img_user_avatar)
                        .placeholder(R.drawable.img_user_avatar)
                        .transform(new CircleTransform())
                        .into(myProfileViewHolder.ivAvatar);
            } else {
                Picasso.with(context)
                        .load(R.drawable.img_user_avatar)
                        .placeholder(R.drawable.img_user_avatar)
                        .into(myProfileViewHolder.ivAvatar);
            }

            myProfileViewHolder.vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMyProfileItemClick != null) {
                        onMyProfileItemClick.onItemClick();
                    }
                }
            });

        } else {

            int index = myProfile != null ? position - 1 : position;
            OtherUserViewHolder otherUserViewHolder = (OtherUserViewHolder)holder;
            try {
                final UserModel userModel = (UserModel) mDataset.get(index);

                String name = userModel.getDisplayNameStr();
                otherUserViewHolder.tvName.setText(name);
                otherUserViewHolder.tvDistance.setText(String.format(Locale.getDefault(), "%d feet away", userModel.getFeetAway()));

                if (!TextUtils.isEmpty(userModel.getAvatar())) {
                    Picasso.with(context)
                            .load(userModel.getAvatar())
                            .error(R.drawable.img_user_avatar)
                            .placeholder(R.drawable.img_user_avatar)
                            .transform(new CircleTransform())
                            .fit()
                            .centerCrop()
                            .into(otherUserViewHolder.ivAvatar);
                } else {
                    Picasso.with(context)
                            .load(R.drawable.img_user_avatar)
                            .placeholder(R.drawable.img_user_avatar)
                            .into(otherUserViewHolder.ivAvatar);
                }

                otherUserViewHolder.vItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onUserProfileItemClick != null) {
                            onUserProfileItemClick.onItemClick(userModel);
                        }
                    }
                });

                otherUserViewHolder.ivFavorite.setVisibility(userModel.isFavourite() ? View.VISIBLE : View.INVISIBLE);
                otherUserViewHolder.ivFriend.setVisibility(userModel.isFriend() ? View.VISIBLE : View.INVISIBLE);
                otherUserViewHolder.ivBlock.setVisibility(userModel.isBlock() ? View.VISIBLE : View.INVISIBLE);

            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(e);
            }


        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        int size = mDataset.size();
        if (myProfile != null) {
            size ++;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && myProfile != null) ? ITEM_VIEW_TYPE_MY_PROFILE : ITEM_VIEW_TYPE_OTHER_PROFILE;
    }

    public void add(List<UserModel> userModels, boolean needClearData) {

        if (userModels != null && userModels.size() > 0) {
            if (needClearData) {
                mDataset.clear();
            }

            mDataset.addAll(userModels);
            notifyDataSetChanged();
        }
    }

    public void setMyProfileModel(MyProfileModel myProfileModel) {

        this.myProfile = myProfileModel;

        notifyDataSetChanged();
    }

    public interface OnMyProfileItemClick {
        void onItemClick();
    }
}
