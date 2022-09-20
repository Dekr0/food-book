package com.example.assignmentone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;


// Reference: https://developer.android.com/develop/ui/views/components/pickers
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    final static public String FRAGMENT_REQUEST_KEY = "requestDate";
    final static public String FRAGMENT_BUNDLE_KEY = "bestBeforeDate";
    final private Calendar c;

    public DatePickerFragment() {
        c = Calendar.getInstance();
    }

    public DatePickerFragment(Calendar c) {
        this.c = c;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                AlertDialog.THEME_HOLO_DARK, this, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        final Calendar c = Calendar.getInstance();

        c.set(i, i1, i2);

        Bundle result = new Bundle();

        result.putString(FRAGMENT_BUNDLE_KEY, FoodFormatter.formatDate(c.getTime()));
        getParentFragmentManager().setFragmentResult(FRAGMENT_REQUEST_KEY, result);
    }
}
