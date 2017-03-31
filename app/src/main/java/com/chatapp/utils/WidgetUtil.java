package com.chatapp.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class WidgetUtil {

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null
                || activity.getWindow().getDecorView().getWindowToken() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void setUpHideSoftKeyboard(final Activity activity, final View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) { //If a layout container, iterate over children and seed recursion.
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpHideSoftKeyboard(activity, innerView);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void switchView(final View showView, final View hideView, final int duration) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            // Set the "show" view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            showView.setAlpha(0f);
            showView.setVisibility(View.VISIBLE);

            // Animate the "show" view to 100% opacity, and clear any animation listener set on
            // the view. Remember that listeners are not limited to the specific animation
            // describes in the chained method calls. Listeners are set on the
            // ViewPropertyAnimator object for the view, which persists across several
            // animations.
            showView.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(null);

            // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
            // to GONE as an optimization step (it won't participate in layout passes, etc.)
            hideView.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideView.setVisibility(View.GONE);
                        }
                    });
        } else {
            hideView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);
        }
    }
}
