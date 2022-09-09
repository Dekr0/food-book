package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


public class AddFoodEntry extends AppCompatActivity implements View.OnClickListener {
    final static public int correctResultCode = 0;
    final static public int invalidResultCode = 1;
    final static public int cancelCode = -1;

    final static public String countResult = "count";
    final static public String descriptionResult = "description";
    final static public String locationResult = "location";
    final static public String unitCostResult = "unitCost";

    public static class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

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

        }
    }

    Button cancelButton;
    Button pickBestBeforeDateButton;
    Button saveFoodEntryButton;
    EditText foodCountField;
    EditText foodDescriptionField;
    EditText foodUnitCostField;
    Spinner foodLocationSpinner;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        // Initialization of necessary objects
        cancelButton = findViewById(R.id.cancelButton);
        pickBestBeforeDateButton = findViewById(R.id.pickBestBeforeDateButton);
        saveFoodEntryButton = findViewById(R.id.saveFoodEntryButton);
        foodCountField = findViewById(R.id.foodCountField);
        foodDescriptionField = findViewById(R.id.foodDescriptionField);
        foodUnitCostField = findViewById(R.id.foodUnitCostField);
        foodLocationSpinner = findViewById(R.id.foodLocationSpinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.food_location,
                android.R.layout.simple_spinner_item);

        saveFoodEntryButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        pickBestBeforeDateButton.setOnClickListener(this);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodLocationSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveFoodEntryButton.getId()) {
            // Create new binding for Activity AddFoodEntry and DisplayFoodList
            // For return information of a food entry
            Intent intent = new Intent();

            // create name for each type of result and set its values
            intent.putExtra(countResult, foodCountField.getText().toString());
            intent.putExtra(descriptionResult, foodDescriptionField.getText().toString());
            intent.putExtra(locationResult, foodLocationSpinner.getSelectedItemId());
            intent.putExtra(unitCostResult, foodUnitCostField.getText().toString());

            setResult(correctResultCode, intent);

            // destroy this activity
            finish();
        } else if (viewId == pickBestBeforeDateButton.getId()) {
            DialogFragment fragment = new DatePickerFragment();
            fragment.show(getSupportFragmentManager(), "datePicker");
        } else if (viewId == cancelButton.getId()) {
            setResult(cancelCode, null);
        }
    }
}