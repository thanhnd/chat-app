package com.chatapp.utils;

import android.animation.AnimatorSet;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.chatapp.R;
import com.chatapp.views.fragments.AlertDialogFragment;
import com.chatapp.views.fragments.ConfirmDialogFragment;
import com.chatapp.views.fragments.DatePickerDialogFragment;
import com.chatapp.views.fragments.ProgressDialogFragment;
import com.chatapp.views.fragments.RetainedDialogFragment;

import java.util.WeakHashMap;

/**
 * Base class to extent from for all menu_dashboard activities
 */
public class DialogUtils {

    private static WeakHashMap<FragmentActivity, Integer> loadingDialogs = new WeakHashMap<>();

    private static AnimatorSet firstAnimationSet = null;
    private static AnimatorSet secondAnimationSet = null;

    private static AnimatorSet getFirstAnimation() {
        if (firstAnimationSet == null) {
            firstAnimationSet = new AnimatorSet();
        }
        return firstAnimationSet;
    }

    private static AnimatorSet getSecondAnimation() {
        if (secondAnimationSet == null) {
            secondAnimationSet = new AnimatorSet();
        }
        return secondAnimationSet;
    }

    public static void clearProgressDialog(FragmentActivity activity) {
        if (loadingDialogs.containsKey(activity)) {
            loadingDialogs.put(activity, 1);
            hideProgressDialog(activity);
        }
    }

    public static void hideProgressDialog(final FragmentActivity activity) {
        if (activity == null || activity.isFinishing()) return;
        if (loadingDialogs.containsKey(activity)) {
            int numberLoadingDialog = loadingDialogs.get(activity);
            if (numberLoadingDialog == 1) {
                Fragment fragment = activity.getSupportFragmentManager()
                        .findFragmentByTag(DialogUtils.class.getName() + ":progress");
                if (fragment != null && fragment instanceof ProgressDialogFragment) {
                    ((ProgressDialogFragment) fragment).dismiss();
                }
                getFirstAnimation().end();
                getSecondAnimation().end();
                loadingDialogs.remove(activity);
            } else {
                numberLoadingDialog--;
                loadingDialogs.put(activity, numberLoadingDialog);
            }
        } else {
            Fragment fragment = activity.getSupportFragmentManager()
                    .findFragmentByTag(DialogUtils.class.getName() + ":progress");
            if (fragment != null && fragment instanceof ProgressDialogFragment) {
                ((ProgressDialogFragment) fragment).dismiss();
            }
            getFirstAnimation().end();
            getSecondAnimation().end();
        }
    }

    public static void showProgressDialog(final FragmentActivity activity) {
        showProgressDialog(activity, activity.getString(R.string.loading));
    }

    public static void showProgressDialog(final FragmentActivity activity, final String message) {
        if (activity == null || activity.isFinishing()) return;
        if (loadingDialogs.containsKey(activity)) {
            int numberLoadingDialog = loadingDialogs.get(activity);
            numberLoadingDialog++;
            loadingDialogs.put(activity, numberLoadingDialog);
        } else {
            loadingDialogs.put(activity, 1);
        }
        Fragment fragment =
                activity.getSupportFragmentManager().findFragmentByTag(DialogUtils.class.getName() + ":progress");
        if (fragment == null) {
            ProgressDialogFragment
                    .instantiate(message, false, null)
                    .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":progress");
        }
    }

    public static void showGeneralErrorAlert(final FragmentActivity activity) {
        if (activity == null || activity.isFinishing()) return;
        AlertDialogFragment
                .instantiate(activity.getString(R.string.general_error_title), activity.getString(R.string.general_error_message), AlertDialogFragment.Type.ERROR, null)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":alert");
    }

    public static void showGeneralErrorAlert(final FragmentActivity activity, String message) {
        if (activity == null || activity.isFinishing()) return;
        AlertDialogFragment
                .instantiate(activity.getString(R.string.general_error_title), message, AlertDialogFragment.Type.ERROR, null)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":alert");
    }

    public static void showGeneralAlert(final FragmentActivity activity, String title, String message) {
        if (activity == null || activity.isFinishing()) return;
        AlertDialogFragment
                .instantiate(title, message, AlertDialogFragment.Type.WARNING, null)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":alert");
    }

    public static void showGeneralAlert(final FragmentActivity activity, final String title, final String message,
                                        final RetainedDialogFragment.OnDismissListener onDismissListener) {
        if (activity == null || activity.isFinishing()) return;
        AlertDialogFragment
                .instantiate(title, message, AlertDialogFragment.Type.WARNING, onDismissListener)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":alert");
    }


    public static void showConfirmDialog(final FragmentActivity activity, String title, String message,
                                         String lblOk, String lblCancel,
                                         ConfirmDialogFragment.OnConfirmListener confirmListener,
                                         RetainedDialogFragment.OnCancelListener cancelListener) {
        if (activity == null || activity.isFinishing()) return;
        ConfirmDialogFragment
                .instantiate(title, message, lblOk, lblCancel, confirmListener, cancelListener)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":confirm");
    }

    public static void showConfirmDialog(final FragmentActivity activity,
                                         String title, String message,
                                         String lblOk,
                                         ConfirmDialogFragment.OnConfirmListener confirmListener) {
        if (activity == null || activity.isFinishing()) return;
        ConfirmDialogFragment
                .instantiate(title, message, lblOk, confirmListener)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":confirm");
    }

    public static void showDatePickerDialog(final FragmentActivity activity,
                                            DatePickerDialog.OnDateSetListener onDateSetListener) {
        if (activity == null || activity.isFinishing()) return;
        DatePickerDialogFragment
                .instantiate(onDateSetListener)
                .show(activity.getSupportFragmentManager(), DialogUtils.class.getName() + ":date_picker");
    }

}