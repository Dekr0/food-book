package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


public class AddFoodEntry extends AppCompatActivity implements View.OnClickListener {
    final static public int CORRECT_RESULT_CODE = 0;
    final static public int INVALID_RESULT_CODE = 1;
    final static public int CANCEL_CODE = -1;

    final static public String COUNT_RESULT = "count";
    final static public String DESCRIPTION_RESULT = "description";
    final static public String LOCATION_RESULT = "location";
    final static public String UNIT_COST_RESULT = "unitCost";

    private Button cancelButton;
    private Button pickBestBeforeDateButton;
    private Button saveFoodEntryButton;
    private EditText foodCountField;
    private EditText foodDescriptionField;
    private EditText foodUnitCostField;
    private Spinner foodLocationSpinner;

    private ItemViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        // Reference: https://developer.android.com/develop/ui/views/components/spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.food_location, android.R.layout.simple_spinner_item);

        saveFoodEntryButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        pickBestBeforeDateButton.setOnClickListener(this);
        pickBestBeforeDateButton.setText(DatePickerFragment.formatDate(Calendar.getInstance()));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodLocationSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveFoodEntryButton.getId()) {
            // Create new binding for Activity AddFoodEntry and DisplayFoodList
            // For return information of a food entry
            Intent activityIntent = new Intent();

            // create name for each type of result and set its values
            activityIntent.putExtra(COUNT_RESULT, foodCountField.getText().toString());
            activityIntent.putExtra(DESCRIPTION_RESULT, foodDescriptionField.getText().toString());
            activityIntent.putExtra(LOCATION_RESULT, foodLocationSpinner.getSelectedItemId());
            activityIntent.putExtra(UNIT_COST_RESULT, foodUnitCostField.getText().toString());

            setResult(CORRECT_RESULT_CODE, activityIntent);

            // destroy this activity
            finish();
        } else if (viewId == pickBestBeforeDateButton.getId()) {
            DialogFragment fragment = new DatePickerFragment();

            fragment.show(getSupportFragmentManager(), "datePicker");

            viewModel.getSelectedString().observe(this, item -> {
                pickBestBeforeDateButton.setText(item);
            });
        } else if (viewId == cancelButton.getId()) {
            setResult(CANCEL_CODE, null);
        }
    }

}

