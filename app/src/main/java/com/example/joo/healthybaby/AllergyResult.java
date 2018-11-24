package com.example.joo.healthybaby;

import java.util.List;

public class AllergyResult {
    private String occurrenceArea;
    private String occurrenceDate;
    private String allergyIntensity;
    private List<String> foodIngredients;

    public AllergyResult() {
    }

    public AllergyResult(String occurrenceArea, String occurrenceDate, String allergyIntensity, List<String> foodIngredients) {
        this.occurrenceArea = occurrenceArea;
        this.occurrenceDate = occurrenceDate;
        this.allergyIntensity = allergyIntensity;
        this.foodIngredients = foodIngredients;
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
}
