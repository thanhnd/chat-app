package com.chatapp.mvp.searchuser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.UserModel;
import com.chatapp.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchUserMvp.View {

    private SearchUserMvp.Presenter presenter;

    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(android.R.id.list)
    ListView lvUsers;
    @Bind(R.id.edt_keyword)
    EditText edtKeyword;

    ListSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_users);
        ButterKnife.bind(this);

        adapter = new ListSearchAdapter(this);
        lvUsers.setAdapter(adapter);

        presenter = new SearchPresenterImpl(this);

        lvUsers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Utils.hideSoftKeyboard(SearchActivity.this);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

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
            presenter.searchUser(keyword);
        }
    }

    @Override
    public void onSearchSuccess(List<UserModel> resultSet) {

    }
}
