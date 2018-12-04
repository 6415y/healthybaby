package com.example.james.healthybaby;

import java.util.List;

/**
 * FoodIngredients class
 * @author lolhi
 *
 * firebase에 음식 리스트 업로드 하기 위한 class
 */
class FoodResult {
    private List<String> foodIngredientsStr;

    public FoodResult() {}

    public FoodResult(List<String> foodIngredientsStr) {
        this.foodIngredientsStr = foodIngredientsStr;
    }

    public List<String> getFoodIngredientsStr() {
        return foodIngredientsStr;
    }
}
