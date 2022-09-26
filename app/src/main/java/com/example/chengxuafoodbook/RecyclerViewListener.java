package com.example.chengxuafoodbook;


/**
 * RecyclerViewListener
 *
 * An listener interface. The contract and methods of this interface is to
 * respond and provide appropriate event handling for which an OnClick event
 * fires in an entry of the RecyclerView by clicking either the edit button
 * or delete button.
 *
 */
public interface RecyclerViewListener {

    /**
     * Respond and provide appropriate event handling when edit button is
     * clicked
     * @param position The absolute position of an entry in RecyclerView whose
     *                 edit button is clicked.
     */
    void onEditButtonClick(int position);


    /**
     *
     * Respond and provide appropriate event handling when delete button is
     * clicked
     * @param position The absolute position of an entry in RecyclerView whose
     *                 delete button is clicked.
     */
    void onDeleteButtonClick(int position);
}
