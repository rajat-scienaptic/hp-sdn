package com.sdn.controller.sdn;

import com.sdn.dto.sdn.SdnDataDTO;
import com.sdn.dto.sdn.SdnFilterRequestDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.service.UserValidationService;
import com.sdn.service.sdn.SdnCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@RequestMapping(value = "/api/v1/sdn")
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
    public final ResponseEntity<Object> getAllSdnDataWithFilter(@RequestBody SdnFilterRequestDTO sdnFilterRequestDTO) {
        return new ResponseEntity<>(sdnCrudService.getAllSdnDataWithFilter(sdnFilterRequestDTO), HttpStatus.OK);
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

    @PostMapping("/getRegions")
    public final ResponseEntity<Object> getRegions() {
        return new ResponseEntity<>(sdnCrudService.getRegions(), HttpStatus.OK);
    }

    @PostMapping("/getSkus")
    public final ResponseEntity<Object> getSkus(@RequestBody(required = false) SdnFilterRequestDTO sdnFilterRequestDTO) {
        return new ResponseEntity<>(sdnCrudService.getSkus(sdnFilterRequestDTO), HttpStatus.OK);
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
