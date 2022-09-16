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


public class AddFoodEntry extends AppCompatActivity implements View.OnClickListener {
    final static public int CORRECT_RESULT_CODE = 0;
    final static public int INVALID_RESULT_CODE = 1;
    final static public int CANCEL_CODE = -1;

    final static public String COUNT_RESULT = "count";
    final static public String DESCRIPTION_RESULT = "description";
    final static public String LOCATION_RESULT = "location";
    final static public String UNIT_COST_RESULT = "unitCost";
    final static public String BEST_BEFORE_DATE_RESULT = "bestBeforeDate";

    Button cancelButton;
    Button pickBestBeforeDateButton;
    Button saveFoodEntryButton;
    EditText foodCountField;
    EditText foodDescriptionField;
    EditText foodUnitCostField;
    Spinner foodLocationSpinner;

    ArrayAdapter<String> adapter;

    String actionType;
    final ArrayList<String> locationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        Intent intent = getIntent();

        // Initialization of necessary objects
        cancelButton = findViewById(R.id.cancelButton);
        pickBestBeforeDateButton = findViewById(R.id.pickBestBeforeDateButton);
        saveFoodEntryButton = findViewById(R.id.saveFoodEntryButton);
        foodCountField = findViewById(R.id.foodCountField);
        foodDescriptionField = findViewById(R.id.foodDescriptionField);
        foodUnitCostField = findViewById(R.id.foodUnitCostField);
        foodLocationSpinner = findViewById(R.id.foodLocationSpinner);

        actionType = intent.getStringExtra(DisplayFoodsList.ACTION_TYPE);

        String[] locations = {"freeze", "fridge", "pantry"};
        locationList.addAll(Arrays.asList(locations));

        // Reference: https://developer.android.com/develop/ui/views/components/spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                locationList);

        saveFoodEntryButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        pickBestBeforeDateButton.setOnClickListener(this);
        pickBestBeforeDateButton.setText(Util.formatDate(Calendar.getInstance().getTime()));

        foodLocationSpinner.setAdapter(adapter);

        if (actionType.equals(DisplayFoodsList.EDIT)) {
            String bestBeforeDate = intent.getStringExtra(BEST_BEFORE_DATE_RESULT);
            String count = intent.getStringExtra(COUNT_RESULT);
            String description = intent.getStringExtra(DESCRIPTION_RESULT);
            String location = intent.getStringExtra(LOCATION_RESULT);
            String unitCost = intent.getStringExtra(UNIT_COST_RESULT);

            foodCountField.setText(count);
            foodDescriptionField.setText(description);
            foodLocationSpinner.setSelection(locationList.indexOf(location));
            foodUnitCostField.setText(unitCost);
            pickBestBeforeDateButton.setText(bestBeforeDate);
        }
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveFoodEntryButton.getId()) {
            // Create new binding for Activity AddFoodEntry and DisplayFoodList
            // For return information of a food entry
            Intent activityIntent = new Intent();

            int returnCode = CORRECT_RESULT_CODE;

            try {
                putFoodInfo(activityIntent);
            } catch (NullPointerException e) {
                e.printStackTrace();

                returnCode = INVALID_RESULT_CODE;
            }

            setResult(returnCode, activityIntent);

            // destroy this activity
            finish();
        } else if (viewId == pickBestBeforeDateButton.getId()) {
            DialogFragment fragment = new DatePickerFragment();

            getSupportFragmentManager().setFragmentResultListener(
                    DatePickerFragment.FRAGMENT_REQUEST_KEY,
                    this,
                    (requestKey, result) -> {
                        String bestBeforeDate = result
                                .getString(DatePickerFragment.FRAGMENT_BUNDLE_KEY);

                        pickBestBeforeDateButton.setText(bestBeforeDate);
                    });

            fragment.show(getSupportFragmentManager(), "datePicker");
        } else if (viewId == cancelButton.getId()) {
            setResult(CANCEL_CODE, null);

            finish();
        }
    }

    private void putFoodInfo(Intent activityIntent) throws NullPointerException {
        int count;
        int unitCost;

        count = Integer.getInteger(
                foodCountField.getText().toString(), 10);
        unitCost = Integer.getInteger(
                foodUnitCostField.getText().toString(), 10);

        String bestBeforeDate = pickBestBeforeDateButton.getText().toString();
        String description = foodDescriptionField.getText().toString();
        String location = foodLocationSpinner.getSelectedItem().toString();

        activityIntent.putExtra(DisplayFoodsList.ACTION_TYPE, actionType);
        activityIntent.putExtra(COUNT_RESULT, count);
        activityIntent.putExtra(UNIT_COST_RESULT, unitCost);
        activityIntent.putExtra(BEST_BEFORE_DATE_RESULT, bestBeforeDate);
        activityIntent.putExtra(DESCRIPTION_RESULT, description);
        activityIntent.putExtra(LOCATION_RESULT, location);
    }
}

