package com.example.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class AddFoodEntry extends AppCompatActivity implements View.OnClickListener {
    final static public int correctResultCode = 0;
    final static public int invalidResultCode = 1;
    final static public int cancelCode = -1;

    final static public String resultNameCount = "count";
    final static public String resultNameDescription = "description";
    final static public String resultNameUnitCost = "unitCost";

    Button cancelButton;
    Button saveFoodEntryButton;
    EditText foodCountField;
    EditText foodDescriptionField;
    EditText foodUnitCostField;
    Spinner foodLocationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        // Initialization of necessary objects
        cancelButton = findViewById(R.id.cancelButton);
        saveFoodEntryButton = findViewById(R.id.saveFoodEntryButton);
        foodCountField = findViewById(R.id.foodCountField);
        foodDescriptionField = findViewById(R.id.foodDescriptionField);
        foodUnitCostField = findViewById(R.id.foodUnitCostField);
        foodLocationSpinner = findViewById(R.id.foodLocationSpinner);

        saveFoodEntryButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveFoodEntryButton.getId()) {
            // Create new binding for Activity AddFoodEntry and DisplayFoodList
            // For return information of a food entry
            Intent intent = new Intent();

            // create name for each type of result and set its values
            intent.putExtra(resultNameCount, foodCountField.getText().toString());
            intent.putExtra(resultNameDescription, foodDescriptionField.getText().toString());
            intent.putExtra(resultNameUnitCost, foodUnitCostField.getText().toString());

            setResult(correctResultCode, intent);

            // destroy this activity
            finish();
        } else if (viewId == cancelButton.getId()) {
            setResult(cancelCode, null);
        }
    }
}