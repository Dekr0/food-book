package com.example.assignmentone;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class FoodFormatter {

    final static public String BUNDLE_KEY = "food_bundle";
    final static public String POSITION_KEY = "position";
    final static public String BEST_BEFORE_DATE = "best_before_date";
    final static public String COUNT = "count";
    final static public String UNIT_COST = "unit_cost";
    final static public String DESCRIPTION = "description";
    final static public String LOCATION = "location";

    final static private SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CANADA
    );

    public static String formatDate(Date date) {
        return formatter.format(date);
    }

    public static Calendar getCalendar(String strDate) {
        final Calendar c = Calendar.getInstance();

        try {
            c.setTime(formatter.parse(strDate));
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        return c;
    }

    public static Bundle createBundle() {
        Bundle bundle = new Bundle();
        
        bundle.putString(BEST_BEFORE_DATE, formatDate(Calendar.getInstance().getTime()));
        bundle.putString(COUNT, "0");
        bundle.putString(UNIT_COST, "0");
        bundle.putString(DESCRIPTION, "");
        bundle.putString(LOCATION, "");
        
        return bundle;
    }

    public static Bundle createBundle(String bestBeforeDate, String count,
                                      String unitCost, String description,
                                      String location) {
        Bundle bundle = new Bundle();

        if (description.equals("")) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        bundle.putString(BEST_BEFORE_DATE, bestBeforeDate);
        bundle.putString(COUNT, count);
        bundle.putString(UNIT_COST, unitCost);
        bundle.putString(DESCRIPTION, description);
        bundle.putString(LOCATION, location);

        return bundle;
    }

    public static Bundle createBundle(Food food) {
        Bundle bundle = new Bundle();
        
        bundle.putString(BEST_BEFORE_DATE, food.getBestBeforeDate());
        bundle.putString(COUNT, String.valueOf(food.getCount()));
        bundle.putString(UNIT_COST, String.valueOf(food.getUnitCost()));
        bundle.putString(DESCRIPTION, String.valueOf(food.getDescription()));
        bundle.putString(LOCATION, String.valueOf(food.getLocation()));

        return bundle;
    }

    public static Food unpackBundle(Bundle bundle) {
        String bestBeforeDate = bundle.getString(BEST_BEFORE_DATE);
        int count = Integer.parseInt(bundle.getString(COUNT));
        int unitCost = Integer.parseInt(bundle.getString(UNIT_COST));
        String description = bundle.getString(DESCRIPTION);
        String location = bundle.getString(LOCATION);

        return new Food(getCalendar(bestBeforeDate).getTime(), count, unitCost,
                description, location);
    }

    public static void unpackBundle(Bundle bundle, Food food) {
        String bestBeforeDate = bundle.getString(BEST_BEFORE_DATE);
        int count = Integer.parseInt(bundle.getString(COUNT));
        int unitCost = Integer.parseInt(bundle.getString(UNIT_COST));
        String description = bundle.getString(DESCRIPTION);
        String location = bundle.getString(LOCATION);

        food.setBestBeforeDate(getCalendar(bestBeforeDate).getTime());
        food.setCount(count);
        food.setUnitCost(unitCost);
        food.setDescription(description);
        food.setLocation(location);
    }
}
