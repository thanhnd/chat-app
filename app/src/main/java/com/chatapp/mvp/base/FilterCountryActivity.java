package com.chatapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chatapp.R;
import com.chatapp.service.models.response.CountryModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 4/1/17.
 */

public class FilterCountryActivity extends AppCompatActivity {
    public static final String KEY_OUTPUT = "selectedCountryId";
    public static final String EXTRA_INPUT_ARR = "inputItems";
    @Bind(R.id.list)
    ListView listView;

    ArrayAdapter<CountryModel> adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_value);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        final List<CountryModel> inputItems = (List<CountryModel>) intent.getSerializableExtra(EXTRA_INPUT_ARR);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, inputItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveFilter(inputItems.get(position));
            }
        });
    }

    public void saveFilter(CountryModel countryModel) {
        Intent intent = new Intent();
        // Create a bundle object
        Bundle b = new Bundle();
        b.putInt(KEY_OUTPUT, countryModel.getCountryId());

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        setResult(RESULT_OK, intent);

        finish();
    }
}
