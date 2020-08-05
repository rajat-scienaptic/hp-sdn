package com.sdn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterRequestDTO {
    private String customerMarket;
    private String sku;
    private String startDate;
    private String endDate;
    private String type;
    private Integer startScore;
    private Integer endScore;
    private String sdcViolations;
}
