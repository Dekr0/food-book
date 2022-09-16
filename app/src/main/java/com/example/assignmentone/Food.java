package com.example.assignmentone;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Food {

   Date bestBeforeDate;
   int count;
   int unitCost;
   String description;
   String location;

    public Food(Date bestBeforeDate, int count, int unitCost, String description,
                String location) {
        this.bestBeforeDate = bestBeforeDate;
        this.count = count;
        this.unitCost = unitCost;
        this.description = description;
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }

    public String getBestBeforeDate() {
        return Util.formatDate(bestBeforeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bestBeforeDate, count, unitCost, description, location);
    }

    public int getCount() {
        return count;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
