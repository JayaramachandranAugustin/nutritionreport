package com.whizpath.nutritionreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private String foodName;
    private String mealtime;
    private int fat;
    private int carbohydrate;
    private int protein;
}
