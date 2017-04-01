package com.chatapp.mvp.filterresult;

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

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilterResultAdapter extends BaseListUserAdapter<FilterResultAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_online_status) TextView tvOnlineStatus;
        @Bind(R.id.tv_location) TextView tvLocation;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;

        public ViewHolder(View v) {
            super(v);
            vItem = v;
            ButterKnife.bind(this, v);
        }
    }

    public FilterResultAdapter(Context context) {
        super(context);
    }

    @Override
    public FilterResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_user, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FilterResultAdapter.ViewHolder vh = new FilterResultAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FilterResultAdapter.ViewHolder holder, int position) {
        final UserModel userModel = mDataset.get(position);
        String name = userModel.getDisplayNameStr();
        holder.tvName.setText(name);
        holder.tvOnlineStatus.setText(userModel.getOnlineStatus());
        holder.tvOnlineStatus.setEnabled(userModel.isOnline());

        if (!TextUtils.isEmpty(userModel.getAvatar())) {
            Picasso.with(context)
                    .load(userModel.getAvatar())
                    .fit()
                    .centerCrop()
                    .error(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .transform(new CircleTransform())
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
    }
}
