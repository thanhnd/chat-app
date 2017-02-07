package com.chatapp.views.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;


/**
 * Created by thanhnguyen on 12/19/16.
 */

public class DatePickerDialogFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar calendar;

    public static DatePickerDialogFragment instantiate(DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerDialogFragment frag = new DatePickerDialogFragment();
        frag.setOnDateSetListener(onDateSetListener);
        return frag;
    }

    public static DatePickerDialogFragment instantiate(Calendar calendar, DatePickerDialog.OnDateSetListener onDateSetListener) {
        DatePickerDialogFragment frag = new DatePickerDialogFragment();
        frag.setOnDateSetListener(onDateSetListener);
        frag.setCalendar(calendar);
        return frag;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }
}
