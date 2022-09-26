package com.example.chengxuafoodbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


/**
 * NotificationDialogFragment
 *
 * A simply custom DialogFragment that display an alert dialog to display
 * an error / warning message
 */
public class NotificationDialogFragment extends DialogFragment {

    private final String message;

    public NotificationDialogFragment(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                });

        return builder.create();
    }
}
