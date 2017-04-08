package com.chatapp.mvp.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.mvp.base.FilterCountryActivity;
import com.chatapp.mvp.base.MultiSelectValueActivity;
import com.chatapp.mvp.filterresult.FilterResultActivity;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.service.models.response.ParamModel;
import com.chatapp.utils.CacheUtil;
import com.chatapp.utils.DialogUtils;
import com.chatapp.views.fragments.ChooseFilterValueDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thanhnguyen on 2/10/17.
 */
public class FilterActivity extends BaseActivity implements FilterMvp.View {

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 80;

    private static final int MIN_HEIGHT = 10;
    private static final int MAX_HEIGHT = 300;

    private static final int MIN_WEIGHT = 10;
    private static final int MAX_WEIGHT = 300;
    private static final int RC_FILTER_ETHNICITY = 1;
    private static final int RC_FILTER_BODY_TYPE = 2;
    private static final int RC_FILTER_TRIBES = 3;
    private static final int RC_FILTER_RELATIONSHIP_STATUS = 4;
    private static final int RC_FILTER_LOCATION = 5;

    @Bind(R.id.btn_filter_photo)
    Button btnFilterPhoto;

    @Bind(R.id.cb_filter_photo)
    CheckBox cbFilterPhoto;

    @Bind(R.id.btn_filter_online)
    Button btnFilterOnline;

    @Bind(R.id.cb_filter_online)
    CheckBox cbFilterOnline;

    @Bind(R.id.btn_filter_age)
    Button btnFilterAge;

    @Bind(R.id.btn_filter_height)
    Button btnFilterHeight;

    @Bind(R.id.btn_filter_ethnicities)
    Button btnFilterEthnicities;

    @Bind(R.id.btn_filter_body_type)
    Button btnFilterBodyType;

    @Bind(R.id.btn_filter_tribes)
    Button btnFilterTribes;

    @Bind(R.id.btn_filter_relationship_status)
    Button btnFilterRelationshipStatus;

    @Bind(R.id.btn_filter_location)
    Button btnFilterLocation;

    FilterMvp.Presenter presenter;

    boolean isFilterOnline, isFilterPhoto;
    int[] filterAge, filterHeight, filterWeight, filterEthnicities, filterBodyType, filterTribes, filterRelationshipStatus;
    int filterLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        presenter = new FilterPresenterImpl(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_filter_apply:
                saveFilter();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveFilter() {
        HashMap request = new HashMap();
        request.put("age_from", filterAge != null && filterAge.length > 0 ? filterAge[0] : 0);
        request.put("age_to", filterAge != null && filterAge.length > 0 ? filterAge[1] : 0);

        request.put("height_from", filterHeight != null && filterHeight.length > 0 ? filterHeight[0] : 0);
        request.put("height_to", filterHeight != null && filterHeight.length > 0 ? filterHeight[1] : 0);

        request.put("weight_from", filterWeight != null && filterWeight.length > 0 ? filterWeight[0] : 0);
        request.put("weight_to", filterWeight != null && filterWeight.length > 0 ? filterWeight[1] : 0);

        request.put("ethnicity_id", filterEthnicities != null ? filterEthnicities : new int[0]);
        request.put("body_type_id", filterBodyType != null ? filterBodyType : new int[0]);
        request.put("my_tribes_id", filterTribes != null ? filterTribes : new int[0]);
        request.put("relationship_status_id", filterRelationshipStatus != null ? filterRelationshipStatus : new int[0]);
        request.put("country_id", filterLocation);

        Intent intent = new Intent(this, FilterResultActivity.class);
        intent.putExtra(FilterResultActivity.ARG_QUERY, request);
        startActivity(intent);
    }

    @OnClick({R.id.btn_filter_online, R.id.cb_filter_online})
    void onClickShowDistance() {
        isFilterOnline = cbFilterOnline.isChecked();
    }

    @OnClick({R.id.btn_filter_photo, R.id.cb_filter_photo})
    void onClickShowPhoto() {
        isFilterPhoto = cbFilterPhoto.isChecked();
    }

    @OnClick(R.id.btn_filter_age)
    void onClickFilterAge() {

        int minAge = filterAge != null && filterAge.length > 0 ? filterAge[0] : MIN_AGE;
        int maxAge = filterAge != null && filterAge.length > 0 ? filterAge[1] : MAX_AGE;
        DialogUtils.showChooseFilterValuetDialog(this, "Age", minAge, maxAge, MIN_AGE, MAX_AGE, new ChooseFilterValueDialogFragment.OnFilterValueSetListener() {
            @Override
            public void onFilterAgeSet(int min, int max) {
                filterAge = new int[2];
                filterAge[0] = min;
                filterAge[1] = max;
            }
        });
    }

    @OnClick(R.id.btn_filter_height)
    void onClickFilterHeight() {

        int minHeight = filterHeight != null && filterHeight.length > 0 ? filterHeight[0] : MIN_HEIGHT;
        int maxHeight = filterHeight != null && filterHeight.length > 0 ? filterHeight[1] : MAX_HEIGHT;
        DialogUtils.showChooseFilterValuetDialog(this, "Height", minHeight, maxHeight,
                MIN_HEIGHT, MAX_HEIGHT, new ChooseFilterValueDialogFragment.OnFilterValueSetListener() {
                    @Override
                    public void onFilterAgeSet(int min, int max) {
                        filterHeight = new int[2];
                        filterHeight[0] = min;
                        filterHeight[1] = max;
                    }
                });
    }

    @OnClick(R.id.btn_filter_weight)
    void onClickFilterWeight() {

        int minWeight = filterWeight != null && filterWeight.length > 0 ? filterWeight[0] : MIN_WEIGHT;
        int maxWeight = filterWeight != null && filterWeight.length > 0 ? filterWeight[1] : MAX_WEIGHT;
        DialogUtils.showChooseFilterValuetDialog(this, "Weight", minWeight, maxWeight,
                MIN_HEIGHT, MAX_HEIGHT, new ChooseFilterValueDialogFragment.OnFilterValueSetListener() {
                    @Override
                    public void onFilterAgeSet(int min, int max) {
                        filterWeight = new int[2];
                        filterWeight[0] = min;
                        filterWeight[1] = max;
                    }
                });
    }

    @OnClick(R.id.btn_filter_ethnicities)
    void onClickFilterEthnicities() {
        if (CacheUtil.getListParamsModel() != null) {
            List<ParamModel> listModels = CacheUtil.getListParamsModel().getListEthnicity();
            Intent intent = new Intent(this, MultiSelectValueActivity.class);
            intent.putExtra(MultiSelectValueActivity.EXTRA_INPUT_ARR, (ArrayList<ParamModel>) listModels);
            intent.putExtra(MultiSelectValueActivity.EXTRA_INPUT_SELECTED, filterEthnicities);
            startActivityForResult(intent, RC_FILTER_ETHNICITY);
        }
    }

    @OnClick(R.id.btn_filter_body_type)
    void onClickFilterBodyType() {

        List<ParamModel> listModels = CacheUtil.getListParamsModel().getListBodyType();
        Intent intent = new Intent(this, MultiSelectValueActivity.class);
        intent.putExtra(MultiSelectValueActivity.EXTRA_INPUT_ARR, (ArrayList<ParamModel>) listModels);
        startActivityForResult(intent, RC_FILTER_BODY_TYPE);
    }

    @OnClick(R.id.btn_filter_tribes)
    void onClickFilterTribes() {

        List<ParamModel> listModels = CacheUtil.getListParamsModel().getListTribes();
        Intent intent = new Intent(this, MultiSelectValueActivity.class);
        intent.putExtra(MultiSelectValueActivity.EXTRA_INPUT_ARR, (ArrayList<ParamModel>) listModels);
        startActivityForResult(intent, RC_FILTER_TRIBES);
    }

    @OnClick(R.id.btn_filter_relationship_status)
    void onClickFilterRelationshipStatus() {

        List<ParamModel> listModels = CacheUtil.getListParamsModel().getListRelationship();
        Intent intent = new Intent(this, MultiSelectValueActivity.class);
        intent.putExtra(MultiSelectValueActivity.EXTRA_INPUT_ARR, (ArrayList<ParamModel>) listModels);
        startActivityForResult(intent, RC_FILTER_RELATIONSHIP_STATUS);
    }

    @OnClick(R.id.btn_filter_location)
    void onClickFilterLocation() {
        presenter.loadFilterCountry();
    }

    private void showChooseCountry(List<CountryModel> listCountries) {
        Intent intent = new Intent(this, FilterCountryActivity.class);
        intent.putExtra(FilterCountryActivity.EXTRA_INPUT_ARR, (ArrayList<CountryModel>) listCountries);
        startActivityForResult(intent, RC_FILTER_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case RC_FILTER_ETHNICITY:
                        filterEthnicities = data.getIntArrayExtra(MultiSelectValueActivity.KEY_OUTPUT_ARR);
                        return;
                    case RC_FILTER_BODY_TYPE:
                        filterBodyType = data.getIntArrayExtra(MultiSelectValueActivity.KEY_OUTPUT_ARR);
                        return;
                    case RC_FILTER_TRIBES:
                        filterTribes = data.getIntArrayExtra(MultiSelectValueActivity.KEY_OUTPUT_ARR);
                        return;
                    case RC_FILTER_RELATIONSHIP_STATUS:
                        filterRelationshipStatus = data.getIntArrayExtra(MultiSelectValueActivity.KEY_OUTPUT_ARR);
                        return;

                    case RC_FILTER_LOCATION:
                        filterLocation = data.getIntExtra(FilterCountryActivity.KEY_OUTPUT, 0);
                        return;
                }

            }
        }
    }

    @Override
    public void onLoadFilterCountriesSuccess(List<CountryModel> resultSet) {
        showChooseCountry(resultSet);
    }
}
