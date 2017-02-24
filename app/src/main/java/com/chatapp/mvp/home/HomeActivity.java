package com.chatapp.mvp.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.chatapp.R;
import com.chatapp.chat.ui.activity.DialogsFragment;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.listfavorites.ListFavoritesFragment;
import com.chatapp.mvp.listfriends.ListFriendsFragment;
import com.chatapp.mvp.listnearby.ListNearbyFragment;
import com.chatapp.mvp.listrecommendedfriends.ListRecommendedFriendsActivity;
import com.chatapp.mvp.searchuser.SearchActivity;
import com.chatapp.mvp.updateprofile.RequireLoginException;
import com.chatapp.utils.AccountUtils;
import com.chatapp.utils.LocationChangeObservable;
import com.chatapp.utils.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeMvp.View,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 100;
    @Bind(R.id.main_tool_bar)
    Toolbar toolbar;

    @Bind(R.id.activity_home)
    CoordinatorLayout coordinatorLayout;

    @Bind(R.id.bottomBar)
    BottomBar bottomBar;

    HomeMvp.Presenter presenter;

    private GoogleApiClient mGoogleApiClient;

    private List<LocationChangeObservable> locationChangeObservableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        presenter = new PresenterImpl(this);
        locationChangeObservableList = new ArrayList<>();

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
                                DialogsFragment.class.getName()));
                        break;
                    case R.id.tab_item_friends:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                ListFriendsFragment.class.getName()));
                        break;
                    case R.id.tab_item_favorites:
                        switchFragment(Fragment.instantiate(HomeActivity.this,
                                ListFavoritesFragment.class.getName()));
                        break;
                }
            }
        });

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.v_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.ib_search)
    public void clickSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ib_add_friend)
    public void clickAddFriends() {
        Intent intent = new Intent(this, ListRecommendedFriendsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .addConnectionCallbacks(this)
                                .addOnConnectionFailedListener(this)
                                .addApi(LocationServices.API)
                                .build();
                    }
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocation();
        Log.d("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude status: " + status);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        updateLocation();
    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

            AccountUtils.setLatitude(mLastLocation.getLatitude());
            AccountUtils.setLongitude(mLastLocation.getLongitude());

            Log.d("Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude());

            try {
                presenter.updateLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            } catch (RequireLoginException e) {
                onRequiredLogin();
            }

            notifyUpdateLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    public void addToLocationChangeObservableList(LocationChangeObservable observable) {
        if (locationChangeObservableList == null) {
            locationChangeObservableList = new ArrayList<>();
        }

        locationChangeObservableList.add(observable);

    }

    public void removeFromLocationChangeObservableList(LocationChangeObservable observable) {
        if (locationChangeObservableList != null) {
            locationChangeObservableList.remove(observable);
        }
    }

    private void notifyUpdateLocation(double latitude, double longitude) {
        for (LocationChangeObservable observable: locationChangeObservableList) {
            if (observable != null) {
                observable.updateLocation(latitude, longitude);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        super.onStart();
    }

    protected void onStop() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }
}
