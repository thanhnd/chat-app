package com.chatapp.mvp.listcountries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.service.models.response.CountryModel;

import java.util.ArrayList;
import java.util.List;

public class ListCountriesAdapter extends BaseAdapter implements Filterable {
    private List<CountryModel> countries;
    private List<CountryModel> filteredData;
    private Context context;
    private ItemFilter mFilter;
    private OnFilterResultChange onFilterResultChange;

    public ListCountriesAdapter(Context context) {
        this.context = context;
        mFilter = new ItemFilter();
    }

    @Override
    public int getCount() {
        if (filteredData != null) {
            return filteredData.size();
        }
        return 0;
    }

    public void setCountries(List<CountryModel> listCountries) {
        this.countries = listCountries;
        if (filteredData != null && !filteredData.isEmpty()) {
            filteredData.clear();
            filteredData.addAll(listCountries);
        } else {
            filteredData = new ArrayList<>(listCountries);
        }


        notifyDataSetChanged();
    }

    @Override
    public CountryModel getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_list_country, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ViewHolder {
        TextView tvName;
        TextView tvCode;

        public ViewHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().trim().toLowerCase();

            FilterResults results = new FilterResults();

            int count = countries.size();
            final ArrayList<CountryModel> nlist = new ArrayList<>(count);

            String filterableString;

            CountryModel country;
            for (int i = 0; i < count; i++) {
                country = countries.get(i);
                filterableString = country.getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(country);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<CountryModel>) results.values;
            notifyDataSetChanged();
            if (onFilterResultChange != null) {
                onFilterResultChange.onChanged(filteredData);
            }
        }

    }

    public void setOnFilterResultChange(OnFilterResultChange onFilterResultChange) {
        this.onFilterResultChange = onFilterResultChange;
    }

    public interface OnFilterResultChange {
        void onChanged(List<CountryModel> newResult);
    }

}
