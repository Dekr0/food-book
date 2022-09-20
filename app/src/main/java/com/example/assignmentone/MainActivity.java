package com.example.assignmentone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        RecyclerViewListener, ActivityResultCallback<ActivityResult> {

    private ActivityResultLauncher<Intent> launcher;

    private Button addFoodButton;
    private TextView totalCostView;

    private FoodEntryAdapter adapter;

    private final ArrayList<Food> foodList = new ArrayList<>();

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == AddFoodActivity.CORRECT_RESULT_CODE) {
            Intent intent = result.getData();

            if (intent != null) {
                Bundle bundle = intent.getBundleExtra(FoodFormatter.BUNDLE_KEY);

                if (bundle != null) {
                    int position = bundle.getInt(FoodFormatter.POSITION_KEY);

                    if (position == foodList.size()) {
                        foodList.add(FoodFormatter.unpackBundle(bundle));
                        adapter.notifyItemInserted(position);
                    } else {
                        FoodFormatter.unpackBundle(bundle, foodList.get(position));
                        adapter.notifyItemChanged(position);
                    }
                }

                updateTotalCost();
            }
        }

        assert adapter.getItemCount() == foodList.size();
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == addFoodButton.getId()) {
            // Create a new intent to bind Activity AddFoodActivity and Activity DisplayFoodList
            // together

            Bundle bundle = FoodFormatter.createBundle();
            Intent intent = new Intent(this, AddFoodActivity.class);

            bundle.putInt(FoodFormatter.POSITION_KEY, foodList.size());
            intent.putExtra(FoodFormatter.BUNDLE_KEY, bundle);

            // Launch Activity AddFoodActivity
            launcher.launch(intent);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods_list);

        test();
        // Initialization of necessary objects (View, Activity Result API)
        // Reference: https://developer.android.com/training/basics/intents/result
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);

        addFoodButton = findViewById(R.id.add_btn);
        totalCostView = findViewById(R.id.total_cost_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new FoodEntryAdapter(this, foodList, this);

        addFoodButton.setOnClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        updateTotalCost();
    }

    @Override
    public void onDeleteButtonClick(int position) {
        foodList.remove(position);

        adapter.notifyItemRemoved(position);

        assert adapter.getItemCount() == foodList.size();

        updateTotalCost();
    }

    @Override
    public void onEditButtonClick(int position) {
        Bundle bundle = FoodFormatter.createBundle(foodList.get(position));
        Intent intent = new Intent(this, AddFoodActivity.class);

        bundle.putInt(FoodFormatter.POSITION_KEY, position);
        intent.putExtra(FoodFormatter.BUNDLE_KEY, bundle);

        launcher.launch(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateTotalCost() {
        int totalCost = 0;

        for (Food food :
                foodList) {
            totalCost += food.getCount() * food.getUnitCost();
        }

        totalCostView.setText(String.format(Locale.CANADA, "Total cost: $%d",
                totalCost));
    }

    private void test() {
        Food tuna = new Food(FoodFormatter.getCalendar("2024-09-23").getTime(), 3, 1,
                "canned light tuna", "pantry");
        Food eggs = new Food(FoodFormatter.getCalendar("2022-09-30").getTime(), 1, 4,
                "dozen eggs", "fridge");
        Food broccoli = new Food(FoodFormatter.getCalendar("2024-09-23").getTime(), 3, 5,
                "frozen broccoli", "freezer");
        Food pepsi = new Food(FoodFormatter.getCalendar("2023-09-19").getTime(), 3, 3,
                "pepsi", "fridge");
        Food bread = new Food(FoodFormatter.getCalendar("2023-09-27").getTime(), 3, 4,
                "bread", "pantry");

        foodList.add(tuna);
        foodList.add(eggs);
        foodList.add(broccoli);
        foodList.add(pepsi);
        foodList.add(bread);
    }
}