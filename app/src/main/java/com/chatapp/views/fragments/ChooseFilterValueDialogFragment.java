package com.chatapp.views.fragments;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.chatapp.R;


public class ChooseFilterValueDialogFragment extends RetainedDialogFragment {

    private int min, max, filterMin, filterMax;
    private String title;
    private OnFilterValueSetListener onFilterValueSetListener;

    public static ChooseFilterValueDialogFragment instantiate(String title, int minValue, int maxValue, int filterMin, int filterMax,
                                                              OnFilterValueSetListener onFilterValueSetListener) {
        ChooseFilterValueDialogFragment frag = new ChooseFilterValueDialogFragment();
        frag.title = title;
        frag.min = minValue;
        frag.max = maxValue;
        frag.filterMin = filterMin;
        frag.filterMax = filterMax;
        frag.setOnFilterValueSetListener(onFilterValueSetListener);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialog);
        dialog.setContentView(R.layout.dialog_filter_range);
        String values[] = new String[filterMax - filterMin + 1];
        for (int i = 0; i <= filterMax - filterMin; i++) {
            values[i] = String.valueOf(i + filterMin);
        }
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        final NumberPicker npMinAge = (NumberPicker) dialog.findViewById(R.id.np_min);
        npMinAge.setMinValue(filterMin);
        npMinAge.setMaxValue(filterMax);
        npMinAge.setValue(min);

        final NumberPicker npMaxAge = (NumberPicker) dialog.findViewById(R.id.np_max);
        npMaxAge.setMinValue(filterMin);
        npMaxAge.setMaxValue(filterMax);
        npMaxAge.setValue(max);

        npMinAge.setDisplayedValues(values);
        npMaxAge.setDisplayedValues(values);
        TextView tvOk = ((TextView) dialog.findViewById(R.id.btnOk));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFilterValueSetListener != null) {
                    onFilterValueSetListener.onFilterAgeSet(npMinAge.getValue(), npMaxAge.getValue());
                    dialog.dismiss();
                }
            }
        });

        TextView tvCancel = ((TextView) dialog.findViewById(R.id.btnCancel));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void setOnFilterValueSetListener(OnFilterValueSetListener onFilterValueSetListener) {
        this.onFilterValueSetListener = onFilterValueSetListener;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public interface OnFilterValueSetListener {
        void onFilterAgeSet(int min, int max);
    }
}
