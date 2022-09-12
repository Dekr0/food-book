package com.example.assignmentone;

import java.util.Date;
import java.util.Objects;

public class Food {

   private Date bestBeforeDate;
   private int count;
   private int unitCost;
   private String description;
   private String location;

    public Food(Date bestBeforeDate, int count, int unitCost, String description,
                String location) {
        this.bestBeforeDate = bestBeforeDate;
        this.count = count;
        this.unitCost = unitCost;
        this.description = description;
        this.location = location;
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

    public Date getBestBeforeDate() {
        return bestBeforeDate;
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
}
