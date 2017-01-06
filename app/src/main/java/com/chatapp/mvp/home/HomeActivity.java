package com.chatapp.mvp.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.listfavorites.ListFavoritesFragment;
import com.chatapp.mvp.listnearby.ListNearbyFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.main_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.activity_home)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.bottomBar)
    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        // Init toolbar
        setSupportActionBar(toolbar);
        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(0, 0);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_item_nearyou:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                ListNearbyFragment.class.getName()));
                        break;
                    case R.id.tab_item_chat:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                ListChatFragment.class.getName()));
                        break;
                    case R.id.tab_item_friends:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                LisFriendsFragment.class.getName()));
                        break;
                    case R.id.tab_item_favorites:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                ListFavoritesFragment.class.getName()));
                        break;
                }
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.v_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState();
    }
}
