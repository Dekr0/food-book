package com.example.assignmentone;

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

    // Note: everytime the instance of this fragment is closed, that instance
    // is destroyed / at the end of lifecycle

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                this, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        final Calendar c = Calendar.getInstance();

        c.set(i, i1, i2);

        Bundle result = new Bundle();

        result.putString(FRAGMENT_BUNDLE_KEY, Util.formatDate(c.getTime()));
        getParentFragmentManager().setFragmentResult(FRAGMENT_REQUEST_KEY, result);
    }
}
