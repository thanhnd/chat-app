package com.chatapp.mvp.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.chatapp.R;
import com.chatapp.mvp.listfriends.ListFriendsAdapter;
import com.chatapp.service.models.response.UserModel;


/**
 * Created by thanhnguyen on 2/5/17.
 */

public abstract class BaseListUserAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;
    protected int imgDiameter;

    protected OnUserProfileItemClick onUserProfileItemClick;

    public void setOnUserProfileItemClick(ListFriendsAdapter.OnUserProfileItemClick onUserProfileItemClick) {
        this.onUserProfileItemClick = onUserProfileItemClick;
    }

    public BaseListUserAdapter(Context context) {
        this.context = context;
        imgDiameter = context.getResources().getDimensionPixelSize(R.dimen.list_img_diameter);
    }

    public interface OnUserProfileItemClick {
        void onItemClick(UserModel userModel);
    }
}
