package com.example.assignmentone;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FoodEntryViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private final ImageButton deleteButton;
    private final TextView descriptionTextView;
    private final TextView countTextView;
    private final TextView unitCostTextView;
    private final TextView locationTextView;
    private final TextView dateTextView;

    RecyclerViewListener listener;

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int pos = getAdapterPosition();

            if (view.getId() == deleteButton.getId()) {
                listener.onDeleteButtonClick(pos);
            } else if (pos != RecyclerView.NO_POSITION) {
                listener.onEntryClick(pos);
            }
        }
    }

    public FoodEntryViewHolder(RecyclerViewListener listener, View view) {
        super(view);

        this.listener = listener;

        deleteButton = view.findViewById(R.id.delete_food_btn);
        descriptionTextView = view.findViewById(R.id.entry_description_text_view);
        countTextView = view.findViewById(R.id.entry_count_text_view);
        unitCostTextView = view.findViewById(R.id.entry_unit_cost_text_view);
        locationTextView = view.findViewById(R.id.entry_location_text_view);
        dateTextView = view.findViewById(R.id.entry_date_text_view);

        view.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public TextView getCountTextView() {
        return countTextView;
    }

    public TextView getUnitCostTextView() {
        return unitCostTextView;
    }

    public TextView getLocationTextView() {
        return locationTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }
}
