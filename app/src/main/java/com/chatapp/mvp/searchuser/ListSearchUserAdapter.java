package com.chatapp.mvp.searchuser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.service.models.response.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ListSearchUserAdapter extends BaseAdapter {
    private List<UserModel> users;
    private Context context;

    public ListSearchUserAdapter(Context context) {
        this.context = context;
        users = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (users != null) {
            return users.size();
        }
        return 0;
    }

    public void addUsers(@NonNull List<UserModel> listUsers) {
        users.addAll(listUsers);
        notifyDataSetChanged();
    }

    public void setUsers(@NonNull List<UserModel> listUsers) {
        users = listUsers;
        notifyDataSetChanged();
    }

    @Override
    public UserModel getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_list_search_user, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserModel userModel = getItem(position);

        viewHolder.tvName.setText(!TextUtils.isEmpty(userModel.getDisplayName()) ?
                userModel.getDisplayName() : "No name");
        return convertView;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvCode;

        public ViewHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
        }
    }
}
