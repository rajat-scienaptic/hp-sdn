package com.sdn.controller.testbuy;

import com.sdn.dto.testbuy.TestBuyFilterRequestDTO;
import com.sdn.service.testbuy.TestBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1")
@RestController
public class TestBuyController {
    @Autowired
    TestBuyService testBuyService;

    @GetMapping("/getAllTestBuyData")
    public final ResponseEntity<Object> getTestBuyData(){
        return new ResponseEntity<>(testBuyService.getTestBuyData(), HttpStatus.OK);
    }

    @PostMapping("/getAllTestBuyDataWithFilter")
    public final ResponseEntity<Object> getAllSdnDataWithFilter(@RequestBody TestBuyFilterRequestDTO testBuyFilterRequestDTO) {
        return new ResponseEntity<>(testBuyService.getAllSTestBuyDataWithFilter(testBuyFilterRequestDTO), HttpStatus.OK);
    }
}
