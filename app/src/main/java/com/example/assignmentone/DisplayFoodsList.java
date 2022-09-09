package com.example.assignmentone;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class DisplayFoodsList extends AppCompatActivity implements View.OnClickListener,
        ActivityResultCallback<ActivityResult> {

    ActivityResultLauncher<Intent> addFoodEntryLauncher;

    Button addFoodEntryButton;
    Button editFoodEntryButton;
    Button deleteFoodEntryButton;

    final ArrayList<Food> foodsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods_list);

        // Initialization of necessary objects (View, Activity Result API)
         addFoodEntryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);
        addFoodEntryButton = findViewById(R.id.addFoodEntryButton);

        addFoodEntryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == addFoodEntryButton.getId()) {
            // Create a new intent to bind Activity AddFoodEntry and Activity DisplayFoodList
            // together
            Intent intent = new Intent(getApplicationContext(), AddFoodEntry.class);

            // Launch Activity AddFoodEntry
            addFoodEntryLauncher.launch(intent);
        }
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == AddFoodEntry.correctResultCode) {
            Intent intent = result.getData();
            if (intent != null) {
                String count = intent.getStringExtra(AddFoodEntry.countResult);
                String description = intent.getStringExtra(AddFoodEntry.descriptionResult);
                String unitCost = intent.getStringExtra(AddFoodEntry.unitCostResult);

            }
        }
    }
}