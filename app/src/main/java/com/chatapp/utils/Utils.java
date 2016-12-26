package com.chatapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by thanhnguyen on 12/27/16.
 */

public class Utils {
    public static void hideSoftKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = ((Activity) context).getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            view.clearFocus();
        }
    }

    public static void hideSoftKeyboard(Context context, View view) {
        if (view == null) {
            return;
        }
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
