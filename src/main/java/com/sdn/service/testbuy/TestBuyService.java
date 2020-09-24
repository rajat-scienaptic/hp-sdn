package com.sdn.service.testbuy;

import com.sdn.dto.testbuy.TestBuyFilterRequestDTO;
import com.sdn.model.testbuy.TestBuy;

import java.util.List;

public interface TestBuyService {
    List<TestBuy> getTestBuyData();
    List<TestBuy> getAllTestBuyDataWithFilter(TestBuyFilterRequestDTO testBuyFilterRequestDTO);
    List<String> getSkusByCountryMarketPlace(String countryMarketPlace);
    List<String> getAllSkus();
}
