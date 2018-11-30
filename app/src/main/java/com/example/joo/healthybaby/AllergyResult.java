package com.example.joo.healthybaby;

import java.util.List;

public class AllergyResult {
    private String occurrenceArea;
    private String occurrenceDate;
    private String allergyIntensity;
    private List<String> foodIngredients;
    private double xvalue;
    private double yvalue;

    public AllergyResult() {
    }

    public AllergyResult(String occurrenceArea, String occurrenceDate, String allergyIntensity, List<String> foodIngredients, double XValue, double YValue) {
        this.occurrenceArea = occurrenceArea;
        this.occurrenceDate = occurrenceDate;
        this.allergyIntensity = allergyIntensity;
        this.foodIngredients = foodIngredients;
        this.xvalue = XValue;
        this.yvalue = YValue;
    }

    public String getOccurrenceArea() {
        return occurrenceArea;
    }

    public String getOccurrenceDate() {
        return occurrenceDate;
    }

    public String getAllergyIntensity() {
        return allergyIntensity;
    }

    public List<String> getFoodIngredients() {
        return foodIngredients;
    }

    public double getXValue() {
        return xvalue;
    }

    public double getYValue() {
        return yvalue;
    }
}
