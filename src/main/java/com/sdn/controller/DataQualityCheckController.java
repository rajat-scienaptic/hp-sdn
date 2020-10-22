package com.sdn.controller;

import com.sdn.service.DataQualityCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
public class DataQualityCheckController {
    @Autowired
    protected DataQualityCheckService dataQualityCheckService;

    @GetMapping("/createReport")
    public final ResponseEntity<Object> createReport() throws ParseException {
        try{
            dataQualityCheckService.getQualityCheckViolatingRowsAndGenerateReport();
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("report created", HttpStatus.OK);
    }
}
