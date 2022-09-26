package com.example.chengxuafoodbook;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * FoodEntryViewHolder
 *
 * A custom ViewHolder class that defines the layout of each food entry in the
 * RecyclerView.
 */
public class FoodEntryViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private final ImageButton deleteButton;
    private final ImageButton editButton;
    private final TextView descriptionTextView;
    private final TextView countTextView;
    private final TextView unitCostTextView;

    // A copy to of RecyclerViewListener instance so that in OnClick
    // callback corresponding method from listener can be called
    RecyclerViewListener listener;

    @Override
    public void onClick(View view) {
        if (listener != null) {
            int pos = getAbsoluteAdapterPosition();

            if (view.getId() == deleteButton.getId()) {
                listener.onDeleteButtonClick(pos);
            } else if (view.getId() == editButton.getId()) {
                listener.onEditButtonClick(pos);
            }
        }
    }

    public FoodEntryViewHolder(RecyclerViewListener listener, View view) {
        super(view);

        this.listener = listener;

        deleteButton = view.findViewById(R.id.delete_food_btn);
        editButton = view.findViewById(R.id.edit_food_btn);
        descriptionTextView = view.findViewById(R.id.entry_description_text_view);
        countTextView = view.findViewById(R.id.entry_count_text_view);
        unitCostTextView = view.findViewById(R.id.entry_unit_cost_text_view);

        editButton.setOnClickListener(this);
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
}
