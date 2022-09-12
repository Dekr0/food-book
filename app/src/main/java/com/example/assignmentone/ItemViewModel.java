package com.example.assignmentone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


// Reference: https://developer.android.com/guide/fragments/communicate#host-activity
public class ItemViewModel extends ViewModel {
    private final MutableLiveData<String> selectedString = new
            MutableLiveData<String>();

    public void selectString(String string) {
        selectedString.setValue(string);
    }

    public LiveData<String> getSelectedString() {
        return selectedString;
    }
}
