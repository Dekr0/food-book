package com.example.assignmentone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;


public class FoodEntryAdapter extends RecyclerView.Adapter<FoodEntryViewHolder> {

    private final RecyclerViewListener listener;

    private final Context context;

    private final ArrayList<Food> foodList;

    public FoodEntryAdapter(Context context,
                            ArrayList<Food> foodList, RecyclerViewListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.food_entry, parent, false);

        return new FoodEntryViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodEntryViewHolder holder, int position) {
        String count = String.format(Locale.getDefault(),
                "Qty: %d",
                foodList.get(position).getCount());
        String unitCost = String.format(Locale.getDefault(),
                "Unit cost: $%d",
                foodList.get(position).getUnitCost());

        holder.getDescriptionTextView()
                .setText(foodList.get(position).getDescription());
        holder.getCountTextView().setText(count);
        holder.getUnitCostTextView().setText(unitCost);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
