package com.chatapp.mvp.searchuser;

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

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListSearchUserAdapter extends RecyclerView.Adapter<ListSearchUserAdapter.ViewHolder> {
    private List<UserModel> users;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name) TextView tvName;
        @Bind(R.id.tv_online_status) TextView tvOnlineStatus;
        @Bind(R.id.tv_location) TextView tvLocation;
        @Bind(R.id.iv_avatar) ImageView ivAvatar;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ListSearchUserAdapter(Context context) {
        users = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ListSearchUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_user, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ListSearchUserAdapter.ViewHolder vh = new ListSearchUserAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListSearchUserAdapter.ViewHolder holder, int position) {
        UserModel userModel = users.get(position);
        String name = TextUtils.isEmpty(userModel.getDisplayName()) ? "No name" : userModel.getDisplayName();
        holder.tvName.setText(name);
        holder.tvOnlineStatus.setText(userModel.isOnline() ? "Online" : "Offline");
        holder.tvOnlineStatus.setEnabled(userModel.isOnline());

        if (!TextUtils.isEmpty(userModel.getAvatar())) {
            Picasso.with(context)
                    .load(userModel.getAvatar())
                    .error(R.drawable.img_user_avatar)
                    .placeholder(R.drawable.img_user_avatar)
                    .transform(new CircleTransform())
                    .into(holder.ivAvatar);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserModel> userModels) {
        users = userModels;
        notifyDataSetChanged();
    }
}
