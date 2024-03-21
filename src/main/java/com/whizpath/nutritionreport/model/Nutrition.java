package com.whizpath.nutritionreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Nutrition {
    private String nutritionName;
    private int total;
    private int goal;
    private String metric;
}
