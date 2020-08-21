package com.sdn.dto.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SdnDataDTO {
    private String countryMarketPlace;
    private String countrySeller;
    private String sellerName;
    private int partnerId;
    private String typeOfPartner;
    private String attendedSeller;
    private String url;
    private Date date;
    private int sdcScoring;
    private String enforcementsStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private String enforcementsDate;
    private String skuNumber;
    private String typeOfSku;
    private String sdcViolations;
    private String notes;
}
