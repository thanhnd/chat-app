package com.chatapp.views.fragments;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chatapp.R;
import com.chatapp.utils.DimenUtils;


public class ConfirmDialogFragment extends RetainedDialogFragment {

    private String title;
    private String message;
    private String lblOk;
    private String lblCancel;
    private OnConfirmListener onConfirmListener;
    private boolean hasCancelButton;
    private boolean isAlignLeft;
    private TextView tvMessage;

    public static ConfirmDialogFragment instantiate(String title, String message,
                                                    String lblOk, String lblCancel,
                                                    OnConfirmListener onConfirmListener,
                                                    OnCancelListener onCancelListener) {
        ConfirmDialogFragment frag = new ConfirmDialogFragment();
        frag.message = message;
        frag.title = title;
        frag.lblOk = lblOk;
        frag.lblCancel = lblCancel;
        frag.setOnCancelListener(onCancelListener);
        frag.setOnConfirmListener(onConfirmListener);
        frag.hasCancelButton = true;
        return frag;
    }

    public static ConfirmDialogFragment instantiate(String title, String message,
                                                    String lblOk, boolean isAlignLeft,
                                                    OnConfirmListener onConfirmListener) {
        ConfirmDialogFragment frag = new ConfirmDialogFragment();
        frag.message = message;
        frag.title = title;
        frag.lblOk = lblOk;
        frag.isAlignLeft = isAlignLeft;
        frag.setOnConfirmListener(onConfirmListener);
        frag.hasCancelButton = false;
        return frag;
    }

    public static ConfirmDialogFragment instantiate(String title, String message,
                                                    String lblOk,
                                                    OnConfirmListener onConfirmListener) {
        ConfirmDialogFragment frag = new ConfirmDialogFragment();
        frag.message = message;
        frag.title = title;
        frag.lblOk = lblOk;
        frag.setOnConfirmListener(onConfirmListener);
        frag.hasCancelButton = false;
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
        dialog.setContentView(R.layout.dialog_confirm);

        if (!TextUtils.isEmpty(title)) {
            TextView tvTitle = ((TextView) dialog.findViewById(R.id.tvTitle));
            tvTitle.setText(Html.fromHtml(title));
        }

        if (!TextUtils.isEmpty(message)) {
            tvMessage = ((TextView) dialog.findViewById(R.id.tvMessage));
            if (isAlignLeft)
                tvMessage.setGravity(Gravity.START);
            tvMessage.setText(Html.fromHtml(message));

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                tvMessage.setMaxHeight(DimenUtils.convertDpToPixel(180, getContext()));
        }

        TextView tvOk = ((TextView) dialog.findViewById(R.id.btnOk));
        if (!TextUtils.isEmpty(lblOk)) {
            tvOk.setText(lblOk);
        }

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmListener != null) {
                    onConfirmListener.onConfirm(ConfirmDialogFragment.this);
                }
            }
        });

        TextView tvCancel = ((TextView) dialog.findViewById(R.id.btnCancel));
        if (hasCancelButton) {
            tvCancel.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(lblCancel)) {
                tvCancel.setText(lblCancel);
            }
        } else {
            tvCancel.setVisibility(View.GONE);
        }

        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
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

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (tvMessage != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                tvMessage.setMaxHeight(DimenUtils.convertDpToPixel(180, getContext()));
            else
                tvMessage.setMaxHeight(Integer.MAX_VALUE);
        }
    }

    public interface OnConfirmListener {
        void onConfirm(RetainedDialogFragment fragment);
    }
}
