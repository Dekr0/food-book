package com.example.chengxuafoodbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;


/**
 * FoodEntryAdapter
 *
 * This class is a custom Adapter class for RecyclerView to corporate with
 * custom ViewHolder class (FoodEntryViewHolder).
 */
public class FoodEntryAdapter extends RecyclerView.Adapter<FoodEntryViewHolder> {

    // Should be pointer to MainActivity, and will be assigned to
    // each custom ViewHolder when init
    private final RecyclerViewListener listener;

    // Should be pointed to MainActivity
    private final Context context;

    // MainActivity and FoodEntryAdapter share the same reference value of
    // foodList
    private final ArrayList<Food> foodList;

    public FoodEntryAdapter(Context context,
                            ArrayList<Food> foodList, RecyclerViewListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    /**
     * Return a custom ViewHolder for each entry in RecyclerView after
     * inflating layout defined in food_entry.xml and attach to the parent
     * ViewGroup.
     * @param parent
     * @param viewType
     * @return a custom ViewHolder object
     */
    @NonNull
    @Override
    public FoodEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.food_entry, parent, false);

        return new FoodEntryViewHolder(listener, view);
    }

    /**
     * When binding a custom ViewHolder object to each entry in RecyclerView,
     * get all the TextView objects and set appropriate text.
     * @param holder
     * @param position
     */
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
