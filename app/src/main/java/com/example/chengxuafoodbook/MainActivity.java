package com.example.chengxuafoodbook;

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

    // For starting an new activity to wait for users input data
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
                Bundle bundle = intent.getBundleExtra(BundleUtility.BUNDLE_KEY);

                if (bundle != null) {
                    int position = bundle.getInt(BundleUtility.POSITION_KEY);

                    if (position == foodList.size()) {
                        foodList.add(BundleUtility.unpackBundle(bundle));
                        adapter.notifyItemInserted(position);
                    } else {
                        BundleUtility.unpackBundle(bundle, foodList.get(position));
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
            Bundle bundle = BundleUtility.createBundle();
            Intent intent = new Intent(this, AddFoodActivity.class);

            bundle.putInt(BundleUtility.POSITION_KEY, foodList.size());
            intent.putExtra(BundleUtility.BUNDLE_KEY, bundle);

            launcher.launch(intent);
        }
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
        Bundle bundle = BundleUtility.createBundle(foodList.get(position));
        Intent intent = new Intent(this, AddFoodActivity.class);

        bundle.putInt(BundleUtility.POSITION_KEY, position);
        intent.putExtra(BundleUtility.BUNDLE_KEY, bundle);

        launcher.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foods_list);

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
}