package com.chatapp.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.NumberPicker;

import com.chatapp.R;

import java.lang.reflect.Field;

/**
 * Created by thanhnguyen on 5/13/17.
 */

public class CustomNumberPicker extends NumberPicker {

    public CustomNumberPicker(Context context) {
        super(context);
        init();
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setDividerColor(ResourcesCompat.getColor(getResources(), R.color.purple, null));
    }


    public void setDividerColor(@ColorInt int color) {
        try {
            Field fDividerDrawable = NumberPicker.class.getDeclaredField("mSelectionDivider");
            fDividerDrawable.setAccessible(true);
            Drawable d = (Drawable) fDividerDrawable.get(this);
            d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            d.invalidateSelf();
            postInvalidate(); // Drawable is dirty
        }
        catch (Exception e) {

        }
    }
}
