package com.example.chengxuafoodbook;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * BundleUtility
 *
 * This class include only public static members that acts utility and tools to:
 *  help with creating bundle,
 *  extracting data from a bundle,
 *  formatting data in a bundle
 */
public class BundleUtility {

    // constant String for storing key pairs for bundles and intents so that
    // each class can follow and easily extract / put data into the bundle
    // with specific key
    final static public String BUNDLE_KEY = "food_bundle";
    final static public String POSITION_KEY = "position";

    final static private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CANADA
    );

    /**
     * Return String representation of a Date object in yyyy-mm-dd
     */
    public static String formatDate(Date date) {
        return formatter.format(date);
    }

    /**
     * Obtain a Calendar object from a string date with format yyyy-mm-dd
     */
    public static Calendar getCalendar(String strDate) {
        final Calendar c = Calendar.getInstance();

        try {
            c.setTime(formatter.parse(strDate));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return c;
    }

    /**
     * Create and return a bundle that contains default string representations of
     * attributes in a Food object
     *
     * @return
     */
    public static Bundle createBundle() {
        Bundle bundle = new Bundle();

        // need to convert to String representation since object cannot be
        // stored in bundle
        bundle.putString(Food.BEST_BEFORE_DATE, formatDate(Calendar.getInstance().getTime()));
        bundle.putString(Food.COUNT, "0");
        bundle.putString(Food.UNIT_COST, "0");
        bundle.putString(Food.DESCRIPTION, "");
        bundle.putString(Food.LOCATION, "");
        
        return bundle;
    }

    /**
     * Different signature of createBundle()
     *
     * Used for storing string value from user inputs into a bundle
     */
    public static Bundle createBundle(String bestBeforeDate, String count,
                                      String unitCost, String description,
                                      String location)
            throws NumberFormatException {

        Bundle bundle = new Bundle();

        if (description.equals("") || count.equals("") || unitCost.equals("")) {
            throw new IllegalArgumentException(
                    "Required field (with \"(*)\" in front) cannot be empty");
        }


        Integer.parseInt(count);
        Integer.parseInt(unitCost);

        bundle.putString(Food.BEST_BEFORE_DATE, bestBeforeDate);
        bundle.putString(Food.COUNT, count);
        bundle.putString(Food.UNIT_COST, unitCost);
        bundle.putString(Food.DESCRIPTION, description);
        bundle.putString(Food.LOCATION, location);

        return bundle;
    }

    /**
     * Different signature of createBundle()
     *
     * Use when MainActivity needs to send the information of an existence Food
     * object in foodList to AddFoodActivity
     *
     * Store the information of an existence Food object into a bundle
     */
    public static Bundle createBundle(Food food) {
        Bundle bundle = new Bundle();
        
        bundle.putString(Food.BEST_BEFORE_DATE, food.getBestBeforeDate());
        bundle.putString(Food.COUNT, String.valueOf(food.getCount()));
        bundle.putString(Food.UNIT_COST, String.valueOf(food.getUnitCost()));
        bundle.putString(Food.DESCRIPTION, String.valueOf(food.getDescription()));
        bundle.putString(Food.LOCATION, String.valueOf(food.getLocation()));

        return bundle;
    }

    /**
     * Use only in MainActivity. Extracting data from a bundle and create a
     * new Food object with the extracting data
     *
     * @param bundle bundle received from the AddFoodActivity
     */
    public static Food unpackBundle(Bundle bundle) {
        String bestBeforeDate = bundle.getString(Food.BEST_BEFORE_DATE);
        int count = Integer.parseInt(bundle.getString(Food.COUNT));
        int unitCost = Integer.parseInt(bundle.getString(Food.UNIT_COST));
        String description = bundle.getString(Food.DESCRIPTION);
        String location = bundle.getString(Food.LOCATION);

        return new Food(getCalendar(bestBeforeDate).getTime(), count, unitCost,
                description, location);
    }

    /**
     * Different signature for unpackBundle()
     *
     * Use only in MainActivity. Extracting data from a bundle and setting new
     * values for a existence Food object
     *
     * @param bundle bundle received from the AddFoodActivity
     * @param food a reference to an existence Food object in foodList
     */
    public static void unpackBundle(Bundle bundle, Food food) {
        String bestBeforeDate = bundle.getString(Food.BEST_BEFORE_DATE);
        int count = Integer.parseInt(bundle.getString(Food.COUNT));
        int unitCost = Integer.parseInt(bundle.getString(Food.UNIT_COST));
        String description = bundle.getString(Food.DESCRIPTION);
        String location = bundle.getString(Food.LOCATION);

        food.setBestBeforeDate(getCalendar(bestBeforeDate).getTime());
        food.setCount(count);
        food.setUnitCost(unitCost);
        food.setDescription(description);
        food.setLocation(location);
    }
}
