package com.whizpath.nutritionreport;

import com.whizpath.nutritionreport.model.Food;
import com.whizpath.nutritionreport.model.MacroNutrient;
import com.whizpath.nutritionreport.model.Nutrition;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class NutritionReportApplication {

	public static void main(String[] args) throws JRException {
		SpringApplication.run(NutritionReportApplication.class, args);


	}

}
