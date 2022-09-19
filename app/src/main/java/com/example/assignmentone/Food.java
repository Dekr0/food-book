package com.example.assignmentone;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;


public class Food {

  private Date bestBeforeDate;
  private int count = 0;
  private int unitCost = 0;
  private String description = "";
  private String location = "";

   public Food() {
       bestBeforeDate = Calendar.getInstance().getTime();
   }

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
       return description + ", location: " + location + ", unit count: " + count +
               ", unit cost: " + unitCost + ", best before date: " +
               getBestBeforeDate();

    }

    public String getBestBeforeDate() {
        return FoodFormatter.formatDate(bestBeforeDate);
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
