package com.whizpath.nutritionreport.controller;


import com.whizpath.nutritionreport.processor.NutritionReportProcessor;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class NutritionController {

    private final NutritionReportProcessor nutritionReportProcessor;

    @GetMapping("/report")
    public ResponseEntity getNutritionReport(@RequestParam("lang") String language) throws JRException {
        Locale locale= StringUtils.parseLocaleString(language);
        ByteArrayOutputStream reportStream=nutritionReportProcessor.generateReport(locale);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity(reportStream.toByteArray(),httpHeaders, HttpStatus.OK);
    }
}
