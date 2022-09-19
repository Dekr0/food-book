package com.example.assignmentone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FoodEntryAdapter extends RecyclerView.Adapter<FoodEntryViewHolder> {

    private final RecyclerViewListener listener;

    private final ArrayList<Food> foodList;

    public FoodEntryAdapter(ArrayList<Food> foodList, RecyclerViewListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.food_entry, parent, false);

        return new FoodEntryViewHolder(listener, view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodEntryViewHolder holder, int position) {
        holder.getDescriptionTextView()
                .setText(foodList.get(position).getDescription());
        holder.getCountTextView()
                .setText(String.valueOf(foodList.get(position).getCount()));
        holder.getUnitCostTextView()
                .setText(String.valueOf(foodList.get(position).getUnitCost()));
        holder.getLocationTextView()
                .setText(foodList.get(position).getLocation());
        holder.getDateTextView()
                .setText(foodList.get(position).getBestBeforeDate());

    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
