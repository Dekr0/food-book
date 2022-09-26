package com.example.chengxuafoodbook;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;


/**
 * Food
 *
 * Note: planning to implement Parcelable such that avoid using Serializable
 * because of performance issue as well as provide an easy way to pass through
 * bundle. However, Parcelable seems not to be design for this purpose.
 *
 * (Source:
 *  https://developer.android.com/reference/android/os/Parcel.html#primitives
 *  https://stackoverflow.com/questions/3323074/android-difference-between-parcelable-and-serializable
 *  https://stackoverflow.com/questions/5550670/benefit-of-using-parcelable-instead-of-serializing-object
 *  )
 *
 * Thus, Food class does not implements Parcelable and pass pure String
 * representation of each attribute into bundle.
 */
public class Food {

    // constant String for storing key pairs for bundles and intents so that
    // each class can follow and easily extract / put data into the bundle
    // with specific key
    final static public String BEST_BEFORE_DATE = "best_before_date";
    final static public String COUNT = "count";
    final static public String UNIT_COST = "unit_cost";
    final static public String DESCRIPTION = "description";
    final static public String LOCATION = "location";

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
        return BundleUtility.formatDate(bestBeforeDate);
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
