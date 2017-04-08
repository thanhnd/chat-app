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


public class ChooseUnitDialogFragment extends RetainedDialogFragment {

    private int unitValue;
    private OnUnitSystemSetListener onUnitSystemSetListener;


    public static ChooseUnitDialogFragment instantiate(int unit, OnUnitSystemSetListener onUnitSystemSetListener) {
        ChooseUnitDialogFragment frag = new ChooseUnitDialogFragment();
        frag.setUnitValue(unit);
        frag.setOnUnitSystemSetListener(onUnitSystemSetListener);
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
        dialog.setContentView(R.layout.dialog_update_unit_system);

        final NumberPicker pickerUnitSystem = (NumberPicker) dialog.findViewById(R.id.np_unit_system);
        pickerUnitSystem.setMinValue(0);
        pickerUnitSystem.setMaxValue(1);
        pickerUnitSystem.setDisplayedValues( new String[] { "cm/kg", "ft/lb" } );
        pickerUnitSystem.setValue(unitValue);
        TextView tvOk = ((TextView) dialog.findViewById(R.id.btnOk));
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUnitSystemSetListener != null) {
                    pickerUnitSystem.clearFocus();
                    onUnitSystemSetListener.onSetUnitSystem(pickerUnitSystem.getValue());
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

    public void setOnUnitSystemSetListener(OnUnitSystemSetListener onUnitSystemSetListener) {
        this.onUnitSystemSetListener = onUnitSystemSetListener;
    }

    public void setUnitValue(int unitValue) {
        this.unitValue = unitValue;
    }

    public interface OnUnitSystemSetListener {
        void onSetUnitSystem(int unitValue);
    }


}
