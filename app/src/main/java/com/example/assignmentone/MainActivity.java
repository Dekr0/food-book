package com.example.assignmentone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        RecyclerViewListener, ActivityResultCallback<ActivityResult> {

    private final static String FOOD_ARRAY_LIST = "food_array_list";

    private ActivityResultLauncher<Intent> launcher;

    private Button addFoodButton;
    private RecyclerView recyclerView;

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

        // Initialization of necessary objects (View, Activity Result API)
        // Reference: https://developer.android.com/training/basics/intents/result
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);

        addFoodButton = findViewById(R.id.add_btn);
        recyclerView = findViewById(R.id.recycler_view);

        adapter = new FoodEntryAdapter(foodList, this);

        addFoodButton.setOnClickListener(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDeleteButtonClick(int position) {
        foodList.remove(position);

        adapter.notifyItemRemoved(position);

        assert adapter.getItemCount() == foodList.size();
    }

    @Override
    public void onEntryClick(int position) {
        Bundle bundle = FoodFormatter.createBundle(foodList.get(position));
        Intent intent = new Intent(this, AddFoodActivity.class);

        bundle.putInt(FoodFormatter.POSITION_KEY, position);
        intent.putExtra(FoodFormatter.BUNDLE_KEY, bundle);

        launcher.launch(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}