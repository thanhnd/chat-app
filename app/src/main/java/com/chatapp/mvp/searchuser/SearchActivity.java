package com.chatapp.mvp.searchuser;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.service.models.response.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchUserMvp.View {

    private SearchUserMvp.Presenter presenter;

    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.rv_list_users)
    RecyclerView rvListUsers;
    @Bind(R.id.edt_keyword)
    EditText edtKeyword;

    ListSearchUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_users);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvListUsers.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null));
        rvListUsers.addItemDecoration(dividerItemDecoration);

        adapter = new ListSearchUserAdapter(this);
        rvListUsers.setAdapter(adapter);

        presenter = new SearchUserPresenterImpl(this);

        edtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEARCH
                        || i == EditorInfo.IME_ACTION_DONE) {
                    onNextClick();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void onNextClick() {
        String keyword = edtKeyword.getText().toString();
        if (!TextUtils.isEmpty(keyword)) {
            try {
               presenter.searchUser(keyword);
            } catch (RequireLoginException e) {
                onRequiredLogin();
            }
        }
    }

    @Override
    public void onSearchSuccess(List<UserModel> resultSet) {
        if (resultSet != null && !resultSet.isEmpty()) {
            adapter.setUsers(resultSet);
        }

        rvListUsers.setVisibility(adapter.getItemCount() != 0 ? View.VISIBLE : View.GONE);
        tvEmpty.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
