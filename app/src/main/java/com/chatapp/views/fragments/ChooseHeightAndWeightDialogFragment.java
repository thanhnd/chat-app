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


public class ChooseHeightAndWeightDialogFragment extends RetainedDialogFragment {

    private int height, weight;
    private OnHeightAndWeightSetListener onHeightAndWeightSetListener;


    public static ChooseHeightAndWeightDialogFragment instantiate(int height, int weight, OnHeightAndWeightSetListener onHeightAndWeightSetListener) {
        ChooseHeightAndWeightDialogFragment frag = new ChooseHeightAndWeightDialogFragment();
        frag.setHeight(height);
        frag.setWeight(weight);
        frag.setOnHeightAndWeightSetListener(onHeightAndWeightSetListener);
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
        dialog.setContentView(R.layout.dialog_update_profile_height_and_weight);
        String values[] = new String[300];
        for (int i = 0; i < 300; i++) {
            values[i] = String.valueOf(i + 1);
        }
        final NumberPicker npHeight = (NumberPicker) dialog.findViewById(R.id.np_height);
        npHeight.setMinValue(1);
        npHeight.setMaxValue(300);
        npHeight.setValue(height > 0 ? height : 170);

        final NumberPicker npWeight = (NumberPicker) dialog.findViewById(R.id.np_weight);
        npWeight.setMinValue(1);
        npWeight.setMaxValue(300);
        npWeight.setValue(weight > 0 ? weight : 70);

        npHeight.setDisplayedValues(values);
        npWeight.setDisplayedValues(values);
        TextView tvOk = ((TextView) dialog.findViewById(R.id.btnOk));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHeightAndWeightSetListener != null) {
                    npHeight.clearFocus();
                    npWeight.clearFocus();
                    onHeightAndWeightSetListener.onHeightAndWeightSet(npHeight.getValue(), npWeight.getValue());
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

    public void setOnHeightAndWeightSetListener(OnHeightAndWeightSetListener onHeightAndWeightSetListener) {
        this.onHeightAndWeightSetListener = onHeightAndWeightSetListener;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public interface OnHeightAndWeightSetListener {
        void onHeightAndWeightSet(int height, int weight);
    }


}
