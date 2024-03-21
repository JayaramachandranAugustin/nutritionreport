package com.whizpath.nutritionreport.processor;

import com.whizpath.nutritionreport.model.Food;
import com.whizpath.nutritionreport.model.MacroNutrient;
import com.whizpath.nutritionreport.model.Nutrition;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NutritionReportProcessor {

    private final MessageSource messageSource;
    public ByteArrayOutputStream generateReport(Locale locale) throws JRException {

        String filePath="D:\\Learning\\Jasper_report\\workspace\\tutorial\\nutritionreport\\nutritionreport\\src\\main\\resources\\templates\\nutritionreport.jrxml";
        Nutrition protein = new Nutrition("Protein", 62, 83, "g");
        Nutrition carbohydrates = new Nutrition("Carbohydrates", 180, 206, "g");
        Nutrition fiber = new Nutrition("Fiber", 20, 38, "g");
        Nutrition sugars = new Nutrition("Sugars", 68, 62, "g");
        Nutrition fat = new Nutrition("Fat", 60, 55, "g");
        Nutrition cholesterol = new Nutrition("Cholesterol", 84, 300, "mg");
        Nutrition sodium = new Nutrition("Sodium", 2200, 2300, "mg");
        Nutrition potassium = new Nutrition("Potassium", 2000, 3500, "mg");
        Nutrition calcium = new Nutrition("Calcium", 62, 100, "%");
        Nutrition iron = new Nutrition("Iron", 38, 100, "%");

        List<Nutrition> nutritionList = new ArrayList<>();
        nutritionList.add(protein);
        nutritionList.add(carbohydrates);
        nutritionList.add(fiber);
        nutritionList.add(sugars);
        nutritionList.add(fat);
        nutritionList.add(cholesterol);
        nutritionList.add(sodium);
        nutritionList.add(potassium);
        nutritionList.add(calcium);
        nutritionList.add(iron);

        MacroNutrient carbMacroNutrient=new MacroNutrient("Carbohydrates",48);
        MacroNutrient fatMacroNutrient=new MacroNutrient("Fat",32);
        MacroNutrient proteinMacroNutrient=new MacroNutrient("Protein",20);

        List<MacroNutrient> macroNutrientList = new ArrayList<>();
        macroNutrientList.add(carbMacroNutrient);
        macroNutrientList.add(fatMacroNutrient);
        macroNutrientList.add(proteinMacroNutrient);

        JRBeanCollectionDataSource nutritionDataSource=new JRBeanCollectionDataSource(nutritionList);
        JRBeanCollectionDataSource macroNutrientDataSource=new JRBeanCollectionDataSource(macroNutrientList);

        Map<String,Object> parameters=new HashMap<>();
        parameters.put("firstName","John");
        parameters.put("lastName","Smith");
        parameters.put("dob","07/09/1972");
        parameters.put("age",50);
        parameters.put("nutritionDataSet",nutritionDataSource);
        parameters.put("macroNutrientDataSet",macroNutrientDataSource);
        parameters.put("foodReport",getFoodReport());
        parameters.put("foodParameter",getFoodParameter());
        setLocalizedParameters(parameters,locale);
        JasperReport report= JasperCompileManager.compileReport(filePath);
        JRStaticText generateDate= (JRStaticText) report.getPageHeader().getElementByKey("generated_date");
        generateDate.setText("Dummy value");
        JasperPrint print= JasperFillManager.fillReport(report,parameters,new JREmptyDataSource());
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        JRPdfExporter exporter=new JRPdfExporter();
        SimplePdfExporterConfiguration configuration=new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }

    private void setLocalizedParameters(Map<String,Object> parameters, Locale locale){
        MessageSourceResourceBundle resourceBundle=new MessageSourceResourceBundle(messageSource,locale);
        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE,resourceBundle);
        parameters.put(JRParameter.REPORT_LOCALE,locale);
    }

    private JRBeanCollectionDataSource getFoodDataSource(){
        List<Food> foodList = new ArrayList<>();
        Food banana =new Food("banana", "breakfast",0,28,1);
        Food avocado =new Food("avocado","breakfast",22,13,3);
        Food milk =new Food("milk","breakfast",8,12,8);
        Food chicken=new Food("chicken","lunch",2,0,26);
        Food rice=new Food("rice","lunch",0,45,26);
        Food egg=new Food("egg","lunch",5,0,6);
        Food potato=new Food("potato","lunch",5,37,4);
        Food oats=new Food("oats","dinner",5,51,13);

        foodList.add(banana);
        foodList.add(avocado);
        foodList.add(milk);
        foodList.add(chicken);
        foodList.add(rice);
        foodList.add(egg);
        foodList.add(potato);
        foodList.add(oats);
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(foodList);
        return dataSource;
    }

    private Map getFoodParameter() {
        Map<String,Object> foodParameter=new HashMap<>();
        foodParameter.put("foodDataset",getFoodDataSource());
        return foodParameter;
    }

    private JasperReport getFoodReport() {

        String filePath = "D:/Learning/Jasper_report/workspace/tutorial/nutritionreport/nutritionreport/src/main/resources/templates/food_nutrition.jrxml";
        JasperReport report = null;
        try {
            report = JasperCompileManager.compileReport(filePath);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return report;
    }
}
