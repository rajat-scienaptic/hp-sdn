package com.sdn.dto;

import lombok.Data;

@Data
public class FilterRequestDTO {
    private String country;
    private String sku;
    private String startDate;
    private String endDate;
    private String type;
    private Integer startScore;
    private Integer endScore;
    private String sdcViolations;
}
