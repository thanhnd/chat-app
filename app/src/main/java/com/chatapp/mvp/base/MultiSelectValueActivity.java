package com.chatapp.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chatapp.R;
import com.chatapp.service.models.response.ParamModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by thanhnguyen on 4/1/17.
 */

public class MultiSelectValueActivity extends CustomActionBarActivity {
    public static final String KEY_OUTPUT_ARR = "selectedItems";
    public static final String EXTRA_INPUT_ARR = "inputItems";
    public static final String EXTRA_INPUT_SELECTED = "inputSelectedItems";
    public static final String EXTRA_TITLE = "title";
    @Bind(R.id.list)
    ListView listView;

    ArrayAdapter<ParamModel> adapter;
    private int[] selectedArr;
    private String title;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_value);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        List<ParamModel> inputItems = (List<ParamModel>) intent.getSerializableExtra(EXTRA_INPUT_ARR);
        selectedArr = intent.getIntArrayExtra(EXTRA_INPUT_SELECTED);
        title = intent.getStringExtra(EXTRA_TITLE);
        setTitle(title);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, inputItems);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

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

    public void saveFilter() {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<ParamModel> selectedItems = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        selectedArr = new int[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            selectedArr[i] = selectedItems.get(i).getId();
        }

        Intent intent = new Intent();
        // Create a bundle object
        Bundle b = new Bundle();
        b.putIntArray(KEY_OUTPUT_ARR, selectedArr);

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        setResult(RESULT_OK, intent);

        finish();
    }
}
