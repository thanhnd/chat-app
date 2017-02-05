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
import com.chatapp.service.models.response.MyProfileModel;
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

public class ListNearbyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_VIEW_TYPE_MY_PROFILE = 0;
    public static final int ITEM_VIEW_TYPE_OTHER_PROFILE = 1;
    private MyProfileModel myProfileModel;
    private ArrayList<UserModel> mDataset;
    private Context context;
    private OnMyProfileItemClick onMyProfileItemClick;
    private OnUserProfileItemClick onUserProfileItemClick;

    public void setOnMyProfileItemClick(OnMyProfileItemClick onMyProfileItemClick) {
        this.onMyProfileItemClick = onMyProfileItemClick;
    }

    public void setOnUserProfileItemClick(OnUserProfileItemClick onUserProfileItemClick) {
        this.onUserProfileItemClick = onUserProfileItemClick;
    }

    public class OtherUserViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_distance) TextView tvDistance;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;
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
        mDataset = new ArrayList<>();
        this.context = context;
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

        final int itemType = getItemViewType(position);
        if (itemType == ITEM_VIEW_TYPE_MY_PROFILE) {
            MyProfileViewHolder myProfileViewHolder = (MyProfileViewHolder)holder;
            String name = TextUtils.isEmpty(myProfileModel.getDisplayName()) ?
                    "No name" : myProfileModel.getDisplayName();
            myProfileViewHolder.tvName.setText(name);
            Picasso.with(context)
                    .load(myProfileModel.getAvatar())
                    .error(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .transform(new CircleTransform())
                    .into(myProfileViewHolder.ivAvatar);
            myProfileViewHolder.vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMyProfileItemClick != null) {
                        onMyProfileItemClick.onItemClick();
                    }
                }
            });
        } else {
            OtherUserViewHolder otherUserViewHolder = (OtherUserViewHolder)holder;
            final UserModel userModel = mDataset.get(myProfileModel == null ?  position : position - 1);
            String name = TextUtils.isEmpty(userModel.getDisplayName()) ? "No name" : userModel.getDisplayName();
            otherUserViewHolder.tvName.setText(name);
            otherUserViewHolder.tvDistance.setText(String.format(Locale.getDefault(), "%d feet away", userModel.getFeetAway()));
            Picasso.with(context)
                    .load(userModel.getAvatar())
                    .error(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .transform(new CircleTransform())
                    .into(otherUserViewHolder.ivAvatar);
            otherUserViewHolder.vItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onUserProfileItemClick != null) {
                        onUserProfileItemClick.onItemClick(userModel);
                    }
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size() + (myProfileModel != null ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return (myProfileModel != null && position == 0) ? ITEM_VIEW_TYPE_MY_PROFILE : ITEM_VIEW_TYPE_OTHER_PROFILE;
    }

    public void add(List<UserModel> userModels, boolean needClearData) {
        if (needClearData) {
            mDataset.clear();
        }
        mDataset.addAll(userModels);
        notifyDataSetChanged();
    }

    public void setMyProfileModel(MyProfileModel myProfileModel) {
        this.myProfileModel = myProfileModel;
        notifyItemInserted(0);
    }

    public interface OnMyProfileItemClick {
        void onItemClick();
    }

    public interface OnUserProfileItemClick {
        void onItemClick(UserModel userModel);
    }
}
