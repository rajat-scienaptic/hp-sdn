package com.sdn.controller;

import com.sdn.dto.FilterRequestDTO;
import com.sdn.dto.SdnDataDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.service.SdnCrudService;
import com.sdn.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@RequestMapping(value = "/api/v1")
@RestController
public class SDNController {
    @Autowired
    SdnCrudService sdnCrudService;
    @Autowired
    UserValidationService userValidationService;

    @GetMapping("/getAllSdnData")
    public final ResponseEntity<Object> getAllSdnData() {
        return new ResponseEntity<>(sdnCrudService.getAllSdnData(), HttpStatus.OK);
    }

    @PostMapping("/getAllSdnDataWithFilter")
    public final ResponseEntity<Object> getAllSdnDataWithFilter(@RequestBody FilterRequestDTO filterRequestDTO) {
        return new ResponseEntity<>(sdnCrudService.getAllSdnDataWithFilter(filterRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/fillSdnData")
    public final ResponseEntity<Object> fillSDNData(@RequestParam("file") MultipartFile file) {
        try {
            sdnCrudService.fillSDNData(file);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Data Added", HttpStatus.CREATED);
    }

    @GetMapping("/getCountries")
    public final ResponseEntity<Object> getCountries() {
        return new ResponseEntity<>(sdnCrudService.getCountries(), HttpStatus.OK);
    }

    @GetMapping("/getCountriesByType/{type}")
    public final ResponseEntity<Object> getCountriesByType(@PathVariable(value = "type", required = false) String type){
        return new ResponseEntity<>(sdnCrudService.getCountriesByType(type), HttpStatus.OK);
    }

    @GetMapping("/getSku/{countryMarketPlace}")
    public final ResponseEntity<Object> getSkuByCountryId(@PathVariable("countryMarketPlace") String countryMarketPlace) {
        return new ResponseEntity<>(sdnCrudService.getSku(countryMarketPlace), HttpStatus.OK);
    }

    @PutMapping("/updateSdnData/{id}")
    public final ResponseEntity<Object> updateSdnData(@PathVariable("id") int id, @RequestBody SdnDataDTO sdnDataDTO, @RequestHeader(value = "Cookie", required = false) String cookie) throws ParseException {
        return new ResponseEntity<>(sdnCrudService.updateSdnData(id, sdnDataDTO, cookie), HttpStatus.OK);
    }

    @GetMapping("/archive/{id}")
    public final ResponseEntity<Object> archiveSdnData(@PathVariable("id") int id) {
        return new ResponseEntity<>(sdnCrudService.archiveSdnData(id), HttpStatus.OK);
    }

    @GetMapping("/getSdcViolations")
    public final ResponseEntity<Object> getSdcViolations() {
        return new ResponseEntity<>(sdnCrudService.getSdcViolations(), HttpStatus.OK);
    }

}
