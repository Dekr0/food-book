package com.example.assignmentone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


// Reference: https://developer.android.com/develop/ui/views/components/pickers
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    // Note: everytime the instance of this fragment is closed, that instance
    // is destroyed / at the end of lifecycle
    private Calendar bestBeforeDate;
    final static private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CANADA
    );

    private ItemViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int year = bestBeforeDate == null ? c.get(Calendar.YEAR) :
                bestBeforeDate.get(Calendar.YEAR);
        int month = bestBeforeDate == null ? c.get(Calendar.MONTH) :
                bestBeforeDate.get(Calendar.MONTH);
        int day = bestBeforeDate == null ? c.get(Calendar.DAY_OF_MONTH) :
                bestBeforeDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                this, year, month, day);

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if (bestBeforeDate == null) {
            bestBeforeDate = Calendar.getInstance();
        }

        bestBeforeDate.set(i, i1, i2);
        viewModel.selectString(formatDate(bestBeforeDate));
    }

    public static String formatDate(Calendar c) {
        return formatter.format(c.getTime());
    }

}
