package com.example.assignmentone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class DisplayFoodsList extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, ActivityResultCallback<ActivityResult> {

    final static public String ACTION_TYPE = "action";
    final static public String NEW = "new";
    final static public String EDIT = "edit";

    ActivityResultLauncher<Intent> addFoodEntryLauncher;

    Button addFoodEntryButton;
    ListView foodEntryListView;

    ArrayAdapter<Food> adapter;

    final HashMap<String, Food> foodHashMap = new HashMap<>();
    final ArrayList<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods_list);

        // Initialization of necessary objects (View, Activity Result API)
        // Reference: https://developer.android.com/training/basics/intents/result
        addFoodEntryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);

        addFoodEntryButton = findViewById(R.id.addFoodEntryButton);
        foodEntryListView = findViewById(R.id.food_entry_list_view);

        adapter = new ArrayAdapter<>(this, R.layout.food_entry, foodList);

        addFoodEntryButton.setOnClickListener(this);

        foodEntryListView.setAdapter(adapter);
        foodEntryListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == addFoodEntryButton.getId()) {
            // Create a new intent to bind Activity AddFoodEntry and Activity DisplayFoodList
            // together
            Intent intent = new Intent(getApplicationContext(), AddFoodEntry.class);

            intent.putExtra(ACTION_TYPE, NEW);

            // Launch Activity AddFoodEntry
            addFoodEntryLauncher.launch(intent);
        }
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == AddFoodEntry.CORRECT_RESULT_CODE) {
            Intent intent = result.getData();
            if (intent != null) {

                int count = intent.getIntExtra(AddFoodEntry.COUNT_RESULT, 0);
                int unitCost = intent.getIntExtra(AddFoodEntry.UNIT_COST_RESULT, 0);
                String actionType = intent.getStringExtra(ACTION_TYPE);
                String bestBeforeDate = intent.getStringExtra(AddFoodEntry.BEST_BEFORE_DATE_RESULT);
                String description = intent.getStringExtra(AddFoodEntry.DESCRIPTION_RESULT);
                String location = intent.getStringExtra(AddFoodEntry.LOCATION_RESULT);
                final Calendar c = Util.strDateToCalendar(bestBeforeDate);

                if (actionType.equals(NEW)) {

                } else if (actionType.equals(EDIT)) {

                }

                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Food food = (Food) adapterView.getItemAtPosition(i);

        Intent intent = new Intent(getApplicationContext(), AddFoodEntry.class);

        intent.putExtra(ACTION_TYPE, EDIT);
        intent.putExtra(AddFoodEntry.COUNT_RESULT, food.getCount());
        intent.putExtra(AddFoodEntry.UNIT_COST_RESULT, food.getUnitCost());
        intent.putExtra(AddFoodEntry.BEST_BEFORE_DATE_RESULT, food.getBestBeforeDate());
        intent.putExtra(AddFoodEntry.DESCRIPTION_RESULT, food.getDescription());
        intent.putExtra(AddFoodEntry.LOCATION_RESULT, food.getLocation());

        addFoodEntryLauncher.launch(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}