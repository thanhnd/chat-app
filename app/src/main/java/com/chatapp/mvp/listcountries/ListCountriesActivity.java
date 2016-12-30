package com.chatapp.mvp.listcountries;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.mvp.base.BaseActivity;
import com.chatapp.service.models.response.CountryModel;
import com.chatapp.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListCountriesActivity extends BaseActivity implements ListCountriesView {

    private ListCountriesPresenter presenter;

    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.lv_countries)
    ListView lvCountries;
    @Bind(R.id.sv_filter)
    SearchView svFilter;

    ListCountriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_countries);
        ButterKnife.bind(this);

        adapter = new ListCountriesAdapter(this);
        lvCountries.setAdapter(adapter);

        presenter = new ListCountriesPresenterImpl(this);
        presenter.getListCountries();

        lvCountries.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Utils.hideSoftKeyboard(ListCountriesActivity.this);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //filter
        svFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        adapter.setOnFilterResultChange(new ListCountriesAdapter.OnFilterResultChange() {
            @Override
            public void onChanged(List<CountryModel> newResult) {
                tvEmpty.setVisibility(newResult.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public void onGetListCountriesSuccess(List<CountryModel> listCountries) {
        adapter.setCountries(listCountries);
    }

    @Override
    public void onGetListCountriesFail() {

    }
}
