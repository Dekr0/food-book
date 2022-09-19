package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class AddFoodActivity extends AppCompatActivity implements View.OnClickListener {
    final static public int CORRECT_RESULT_CODE = 0;
    final static public int INVALID_RESULT_CODE = 1;
    final static public int CANCEL_CODE = -1;

    private Button cancelButton;
    private Button pickDateButton;
    private Button saveButton;
    private EditText countField;
    private EditText descriptionField;
    private EditText unitCostField;
    private Spinner locationSpinner;

    private ArrayAdapter<String> adapter;

    private final ArrayList<String> locationList = new ArrayList<>();

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveButton.getId()) {
            // Create new binding for Activity AddFoodActivity and DisplayFoodList
            // For return information of a food entry
            Intent hostIntent = getIntent();
            Intent resultIntent = new Intent();

            try {
                if (hostIntent != null) {
                    Bundle hostBundle = hostIntent.getBundleExtra(FoodFormatter.BUNDLE_KEY);
                    if (hostBundle != null) {
                        Bundle returnBundle = FoodFormatter.createBundle(
                                pickDateButton.getText().toString(),
                                countField.getText().toString(),
                                unitCostField.getText().toString(),
                                descriptionField.getText().toString(),
                                locationSpinner.getSelectedItem().toString()
                        );

                        returnBundle.putInt(FoodFormatter.POSITION_KEY,
                                hostBundle.getInt(FoodFormatter.POSITION_KEY));
                        resultIntent.putExtra(FoodFormatter.BUNDLE_KEY, returnBundle);
                    }
                }

                setResult(CORRECT_RESULT_CODE, resultIntent);

                finish();
            } catch (IllegalArgumentException e) {
                showAlertDialog(e.getMessage());
            }
        } else if (viewId == pickDateButton.getId()) {
            DialogFragment fragment = new DatePickerFragment();

            getSupportFragmentManager().setFragmentResultListener(
                    DatePickerFragment.FRAGMENT_REQUEST_KEY,
                    this,
                    (requestKey, result) -> {
                        String bestBeforeDate = result
                                .getString(DatePickerFragment.FRAGMENT_BUNDLE_KEY);

                        pickDateButton.setText(bestBeforeDate);
                    });

            fragment.show(getSupportFragmentManager(), "datePicker");
        } else if (viewId == cancelButton.getId()) {
            setResult(CANCEL_CODE, null);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        Intent hostIntent = getIntent();
        if (hostIntent != null) {
            // Initialization of necessary objects
            cancelButton = findViewById(R.id.cancel_btn);
            pickDateButton = findViewById(R.id.pick_date_btn);
            saveButton = findViewById(R.id.save_btn);
            countField = findViewById(R.id.count_edit);
            descriptionField = findViewById(R.id.description_edit);
            unitCostField = findViewById(R.id.unit_cost_edit);
            locationSpinner = findViewById(R.id.spinner);

            String[] locations = {"freezer", "fridge", "pantry"};
            locationList.addAll(Arrays.asList(locations));

            // Reference: https://developer.android.com/develop/ui/views/components/spinner
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    locationList);

            saveButton.setOnClickListener(this);
            cancelButton.setOnClickListener(this);

            pickDateButton.setOnClickListener(this);
            pickDateButton.setText(FoodFormatter.
                    formatDate(Calendar.getInstance().getTime()));

            locationSpinner.setAdapter(adapter);

            Bundle hostBundle = hostIntent.getBundleExtra(FoodFormatter.BUNDLE_KEY);
            if (hostBundle != null) {

                pickDateButton.setText(hostBundle.getString(FoodFormatter
                        .BEST_BEFORE_DATE));
                countField.setText(hostBundle.getString(FoodFormatter.COUNT));
                unitCostField.setText(hostBundle.getString(FoodFormatter.UNIT_COST));
                descriptionField.setText(hostBundle.getString(FoodFormatter.
                        DESCRIPTION));
                locationSpinner.setSelection(locationList.indexOf(
                        hostBundle.getString(FoodFormatter.LOCATION)), false);
            }
        }
    }

    private void showAlertDialog(String message) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(new NotificationDialogFragment(message), "alertDialog")
                .commit();
    }

    @Override
    public void onBackPressed() {
        setResult(CANCEL_CODE, null);

        super.onBackPressed();
    }
}