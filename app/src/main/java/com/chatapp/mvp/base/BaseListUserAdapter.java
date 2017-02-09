package com.chatapp.mvp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.chatapp.R;
import com.chatapp.mvp.listfriends.ListFriendsAdapter;
import com.chatapp.service.models.response.UserModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thanhnguyen on 2/5/17.
 */

public abstract class BaseListUserAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<UserModel> mDataset;
    protected Context context;
    protected int imgDiameter;

    protected OnUserProfileItemClick onUserProfileItemClick;

    public void setOnUserProfileItemClick(ListFriendsAdapter.OnUserProfileItemClick onUserProfileItemClick) {
        this.onUserProfileItemClick = onUserProfileItemClick;
    }

    public BaseListUserAdapter(Context context) {
        this.context = context;
        imgDiameter = context.getResources().getDimensionPixelSize(R.dimen.list_img_diameter);
        mDataset = new ArrayList<>();
    }

    public void clearData() {
        mDataset.clear();
        notifyDataSetChanged();
    }

    public interface OnUserProfileItemClick {
        void onItemClick(UserModel userModel);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(List<UserModel> userModels) {
        int position = mDataset.size();
        mDataset.addAll(userModels);
        notifyItemRangeInserted(position, userModels.size());
    }
}
