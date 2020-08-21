package com.sdn.dto.testbuy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestBuyFilterRequestDTO {
    private String customerMarket;
    private String sku;
    private String startDate;
    private String endDate;
    private String testBuyViolations;
}
