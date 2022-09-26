package com.example.chengxuafoodbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


/**
 * AddFoodActivity
 *
 * AddFoodActivity is an activity class. The responsibility of this class is
 * to receive the input data from the users, package that input data into a
 * Bundle object using the static methods provided BundleUtility Class, and
 * pass that Bundle object back to MainActivity object
 *
 * The design of this class involve minimum or zero interaction (creation,
 * access, modification instance) with Food class as the responsibility stated
 * above - receive the input data and send back to the MainActivity object
 * which is responsible for creating, modifying, removing the Food objects.
 */
public class AddFoodActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnKeyListener {

    // Return code when calling setResult() method to notify MainActivity
    // whether an operation is success, fail or cancelled.
    final static public int CORRECT_RESULT_CODE = 0;
    final static public int CANCEL_CODE = -1;

    // View objects
    private Button cancelButton;
    private Button pickDateButton;
    private Button saveButton;
    private EditText countField;
    private EditText descriptionField;
    private EditText unitCostField;
    private Spinner locationSpinner;

    private final ArrayList<String> locationList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        setResult(CANCEL_CODE, null);

        super.onBackPressed();
    }

    /**
     * Override default behavior of back arrow in the action bar so that
     * it will not destroy the state of MainActivity object.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();

        if (viewId == saveButton.getId()) {
            Intent hostIntent = getIntent();

            try {
                Intent resultIntent = saveResult(hostIntent);

                sendResult(CORRECT_RESULT_CODE, resultIntent);
            } catch (IllegalArgumentException e) {
                validateInput();
                showAlertDialog();
            } catch (NullPointerException e) {
                Log.e("ActivityBinding", e.getMessage());
            }
        } else if (viewId == pickDateButton.getId()) {
            showDatePickerDialog();
        } else if (viewId == cancelButton.getId()) {
            sendResult(CANCEL_CODE, null);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        validateInput();

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_entry);

        Intent hostIntent = getIntent();
        if (hostIntent != null) {
            getView();

            String[] locations = {"freezer", "fridge", "pantry"};

            locationList.addAll(Arrays.asList(locations));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    locationList);

            setOnClickListener();

            locationSpinner.setAdapter(adapter);

            Bundle hostBundle = hostIntent.getBundleExtra(BundleUtility.BUNDLE_KEY);
            if (hostBundle != null) {
                populateField(hostBundle);
            }
        }
    }

    /**
     * For organizing code
     *
     * find and assign all the necessary View objects in the ViewGroup object
     */
    private void getView() {
        cancelButton = findViewById(R.id.cancel_btn);
        pickDateButton = findViewById(R.id.pick_date_btn);
        saveButton = findViewById(R.id.save_btn);
        countField = findViewById(R.id.count_edit);
        descriptionField = findViewById(R.id.description_edit);
        unitCostField = findViewById(R.id.unit_cost_edit);
        locationSpinner = findViewById(R.id.spinner);
    }

    /**
     * Change the text of all the EditText View and the date string of the
     * pick best before button with the attributes of the Food object being
     * added / edited.
     * @param hostBundle The Bundle object contains the string representation
     *                   of each attribute of a Food object being added / edited
     */
    private void populateField(Bundle hostBundle) {
        pickDateButton.setText(hostBundle.getString(Food.BEST_BEFORE_DATE));
        countField.setText(hostBundle.getString(Food.COUNT));
        unitCostField.setText(hostBundle.getString(Food.UNIT_COST));
        descriptionField.setText(hostBundle.getString(Food.DESCRIPTION));
        locationSpinner.setSelection(locationList.indexOf(
                hostBundle.getString(Food.LOCATION)), false);
    }

    /**
     * For organizing code
     *
     * set the corresponding OnClickListener instance to each Button View
     */
    private void setOnClickListener() {
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        pickDateButton.setOnClickListener(this);
        pickDateButton.setText(BundleUtility.
                formatDate(Calendar.getInstance().getTime()));
        descriptionField.setOnKeyListener(this);
        countField.setOnKeyListener(this);
        unitCostField.setOnKeyListener(this);
    }

    /**
     * Show a alert dialog and highlight all the required text field when
     * users do not enter all the required text field
     */
    private void showAlertDialog() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(new NotificationDialogFragment("Invalid Input"), "alertDialog")
                .commit();
    }

    private void showDatePickerDialog() {
        DialogFragment fragment = new DatePickerFragment(
                BundleUtility.getCalendar(pickDateButton.getText().toString())
        );

        getSupportFragmentManager().setFragmentResultListener(
                DatePickerFragment.FRAGMENT_REQUEST_KEY,
                this,
                (requestKey, result) -> {

                    // Set the current text of pick best before date button to
                    // the date user just pick
                    String bestBeforeDate = result
                            .getString(DatePickerFragment.FRAGMENT_BUNDLE_KEY);

                    pickDateButton.setText(bestBeforeDate);
                });

        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * For organizing code
     *
     * This method includes few steps to save the result entered by the users:
     *  1. Init an intent that will be passed back to MainActivity and being
     *  used inside the callback `onActivityResult` method.
     *  2. call packageBundle(int, Intent) method, see packageMethod(int, Intent)
     *  for more detail
     *
     * @param hostIntent intent from the MainActivity, most important part is
     *                   position of the food object in foodList being modify.
     *
     * @return An intent that is ready to pass into setResult() method which
     * will notify result will be returned to MainActivity
     *
     * @throws IllegalArgumentException Invalid input from the users
     *
     * @throws NullPointerException Intent lost from MainActivity, binding
     * between two activities fail
     */
    private Intent saveResult(Intent hostIntent) throws IllegalArgumentException,
            NullPointerException {
        Intent resultIntent = new Intent();

        if (hostIntent != null) {
            Bundle hostBundle = hostIntent.getBundleExtra(BundleUtility.BUNDLE_KEY);

            if (hostBundle != null) {
                // location of food object in foodList being modify
                int position = hostBundle.getInt(BundleUtility.POSITION_KEY);
                packageResult(position, resultIntent);
            } else {
                throw new NullPointerException("Data was not passed from " +
                        "MainActivity");
            }
        } else {
            throw new NullPointerException("MainActivity is not bind with " +
                    "AddFoodActivity");
        }

        return resultIntent;
    }

    /**
     * For organizing code
     *
     * call setResult() to notify MainActivity and return the intent with result
     * in it
     *
     */
    private void sendResult(int code, Intent resultIntent) {
        setResult(code, resultIntent);
        finish();
    }

    /**
     * For organizing code
     *
     * This method mainly is to:
     *  1. read in the input from the users,
     *  2. send the values from each input into BundleUtility.createBundle()
     *  method to package those values into a bundle
     *  3.store it into the intent used for sending data back to MainActivity.
     *
     * @param position location of the food object being modified in foodList
     * @param resultIntent intent used for sending data back to MainActivity
     *                     Notice that the modification on this intent will
     *                     be reflect to the caller.
     * @throws IllegalArgumentException trigger when invalid input is detected
     */
    private void packageResult(int position, Intent resultIntent)
            throws IllegalArgumentException {

        Bundle returnBundle = BundleUtility.createBundle(
                pickDateButton.getText().toString(),
                countField.getText().toString(),
                unitCostField.getText().toString(),
                descriptionField.getText().toString(),
                locationSpinner.getSelectedItem().toString()
        );

        returnBundle.putInt(BundleUtility.POSITION_KEY, position);
        resultIntent.putExtra(BundleUtility.BUNDLE_KEY, returnBundle);
    }

    private void validateInput() {
        if (descriptionField.getText().toString().equals("")) {
            descriptionField.setError("Description cannot be empty");
        } else {
            descriptionField.setError(null);
        }

        try {
            Integer.parseInt(countField.getText().toString());
        } catch (NumberFormatException e) {
            countField.setError("Invalid Input");
        }

        try {
            Integer.parseInt(unitCostField.getText().toString());
        } catch (NumberFormatException e) {
            unitCostField.setError("Invalid Input");
        }
    }
}