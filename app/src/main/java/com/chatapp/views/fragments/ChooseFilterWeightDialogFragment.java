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


public class ChooseFilterWeightDialogFragment extends RetainedDialogFragment {

    private int minAge, maxAge;
    private OnFilterAgeSetListener onFilterAgeSetListener;


    public static ChooseFilterWeightDialogFragment instantiate(int minAge, int maxAge,
                                                               OnFilterAgeSetListener onFilterAgeSetListener) {
        ChooseFilterWeightDialogFragment frag = new ChooseFilterWeightDialogFragment();
        frag.setMinAge(minAge);
        frag.setMaxAge(maxAge);
        frag.setOnFilterAgeSetListener(onFilterAgeSetListener);
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
        String values[] = new String[80-18+1];
        for (int i = 18; i <= 80; i++) {
            values[i] = String.valueOf(i + 1);
        }
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        tvTitle.setText("Age");
        final NumberPicker npMinAge = (NumberPicker) dialog.findViewById(R.id.np_min);
        npMinAge.setMinValue(18);
        npMinAge.setMaxValue(80);
        npMinAge.setValue(minAge);

        final NumberPicker npMaxAge = (NumberPicker) dialog.findViewById(R.id.np_max);
        npMaxAge.setMinValue(18);
        npMaxAge.setMaxValue(80);
        npMaxAge.setValue(maxAge);

        npMinAge.setDisplayedValues(values);
        npMaxAge.setDisplayedValues(values);
        TextView tvOk = ((TextView) dialog.findViewById(R.id.btnOk));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFilterAgeSetListener != null) {
                    onFilterAgeSetListener.onFilterAgeSet(npMinAge.getValue(), npMaxAge.getValue());
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

    public void setOnFilterAgeSetListener(OnFilterAgeSetListener onFilterAgeSetListener) {
        this.onFilterAgeSetListener = onFilterAgeSetListener;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public interface OnFilterAgeSetListener {
        void onFilterAgeSet(int height, int weight);
    }
}
