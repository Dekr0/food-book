package com.example.assignmentone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    final static public int CANCEL_CODE = -1;

    private Button cancelButton;
    private Button pickDateButton;
    private Button saveButton;
    private EditText countField;
    private EditText descriptionField;
    private EditText unitCostField;
    private Spinner locationSpinner;

    private final ArrayList<String> locationList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        setResult(CANCEL_CODE, null);

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveButton.getId()) {
            // Create new binding for Activity AddFoodActivity and DisplayFoodList
            // For return information of a food entry
            Intent hostIntent = getIntent();
            try {
                Intent resultIntent = saveResult(hostIntent);

                sendResult(CORRECT_RESULT_CODE, resultIntent);
            } catch (IllegalArgumentException e) {
                showAlertDialog(e.getMessage());
            } catch (NullPointerException e) {
                Log.e("ActivityBinding", e.getMessage());
            }
        } else if (viewId == pickDateButton.getId()) {
            showDatePickerDialog();
        } else if (viewId == cancelButton.getId()) {
            sendResult(CANCEL_CODE, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        Intent hostIntent = getIntent();
        if (hostIntent != null) {
            getView();

            String[] locations = {"freezer", "fridge", "pantry"};
            locationList.addAll(Arrays.asList(locations));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    locationList);

            setOnClickListener();

            locationSpinner.setAdapter(adapter);

            Bundle hostBundle = hostIntent.getBundleExtra(FoodFormatter.BUNDLE_KEY);
            if (hostBundle != null) {
                populateField(hostBundle);
            }
        }
    }

    private void getView() {
        cancelButton = findViewById(R.id.cancel_btn);
        pickDateButton = findViewById(R.id.pick_date_btn);
        saveButton = findViewById(R.id.save_btn);
        countField = findViewById(R.id.count_edit);
        descriptionField = findViewById(R.id.description_edit);
        unitCostField = findViewById(R.id.unit_cost_edit);
        locationSpinner = findViewById(R.id.spinner);
    }

    private void packageResult(int position, Intent resultIntent)
            throws IllegalArgumentException {
        Bundle returnBundle = FoodFormatter.createBundle(
                pickDateButton.getText().toString(),
                countField.getText().toString(),
                unitCostField.getText().toString(),
                descriptionField.getText().toString(),
                locationSpinner.getSelectedItem().toString()
        );

        returnBundle.putInt(FoodFormatter.POSITION_KEY, position);
        resultIntent.putExtra(FoodFormatter.BUNDLE_KEY, returnBundle);
    }

    private void populateField(Bundle hostBundle) {
        pickDateButton.setText(hostBundle.getString(FoodFormatter.BEST_BEFORE_DATE));
        countField.setText(hostBundle.getString(FoodFormatter.COUNT));
        unitCostField.setText(hostBundle.getString(FoodFormatter.UNIT_COST));
        descriptionField.setText(hostBundle.getString(FoodFormatter.DESCRIPTION));
        locationSpinner.setSelection(locationList.indexOf(
                hostBundle.getString(FoodFormatter.LOCATION)), false);
    }

    private Intent saveResult(Intent hostIntent) throws IllegalArgumentException,
            NullPointerException {
        Intent resultIntent = new Intent();

        if (hostIntent != null) {
            Bundle hostBundle = hostIntent.getBundleExtra(FoodFormatter.BUNDLE_KEY);

            if (hostBundle != null) {
                int position = hostBundle.getInt(FoodFormatter.POSITION_KEY);
                packageResult(position, resultIntent);
            } else {
                throw new NullPointerException("Data was not passed from MainActivity");
            }
        } else {
            throw new NullPointerException("MainActivity is not bind with AddFoodActivity");
        }

        return resultIntent;
    }

    private void sendResult(int code, Intent resultIntent) {
        setResult(code, resultIntent);
        finish();
    }

    private void setOnClickListener() {
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        pickDateButton.setOnClickListener(this);
        pickDateButton.setText(FoodFormatter.
                formatDate(Calendar.getInstance().getTime()));
    }

    private void showAlertDialog(String message) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(new NotificationDialogFragment(message), "alertDialog")
                .commit();
    }

    private void showDatePickerDialog() {
        DialogFragment fragment = new DatePickerFragment(
                FoodFormatter.getCalendar(pickDateButton.getText().toString())
        );

        getSupportFragmentManager().setFragmentResultListener(
                DatePickerFragment.FRAGMENT_REQUEST_KEY,
                this,
                (requestKey, result) -> {
                    String bestBeforeDate = result
                            .getString(DatePickerFragment.FRAGMENT_BUNDLE_KEY);

                    pickDateButton.setText(bestBeforeDate);
                });

        fragment.show(getSupportFragmentManager(), "datePicker");
    }
}