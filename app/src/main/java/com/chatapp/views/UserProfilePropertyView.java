package com.chatapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chatapp.R;

/**
 * Created by thanhnguyen on 2/4/17.
 */

public class UserProfilePropertyView extends LinearLayout {

    private View rootView;
    TextView tvLabel, tvValue;

    public UserProfilePropertyView(Context context, AttributeSet attrs) {
        super(context, attrs);


        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.layout_user_profile_property, this, true);

        tvLabel = (TextView) rootView.findViewById(R.id.tv_label);
        tvValue = (TextView) rootView.findViewById(R.id.tv_value);

    }

    public UserProfilePropertyView(Context context) {
        this(context, null);
    }

    public UserProfilePropertyView update(String label, String value) {
        this.tvLabel.setText(label);
        this.tvValue.setText(value);

        return this;
    }

}
