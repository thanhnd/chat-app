package com.chatapp.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chatapp.R;

/**
 * Created by thanhnguyen on 5/16/17.
 */

public class CustomActionBarActivity extends BaseActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        iniActionbar();
        super.onCreate(savedInstanceState);

    }

    private void iniActionbar() {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            View v = getLayoutInflater().inflate(R.layout.layout_material_actionbar, null);
            actionBar.setCustomView(v, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER
            ));

            actionBar.setElevation(0);

            Toolbar parent = (Toolbar) v.getParent();
            parent.setContentInsetsAbsolute(0, 0);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (actionBar != null && !TextUtils.isEmpty(title)) {
            TextView tvTitle = (TextView) actionBar.getCustomView()
                    .findViewById(R.id.center_text);
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
        }
    }
}
