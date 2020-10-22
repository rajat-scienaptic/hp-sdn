package com.sdn.dto.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scienaptic.excel.annotations.ExcelColumn;
import lombok.Data;

@Data
public class SdnTransactionMonitoringWebsitesDTO {
    @ExcelColumn(name = "SKU", order = 1)
    private String sku;
    @ExcelColumn(name = "SKU Type", order = 2)
    private String skuType;
    @ExcelColumn(name = "Country Seller", order = 3)
    private String countrySeller;
    @ExcelColumn(name = "Domain Name", order = 4)
    private String domainName;
    @ExcelColumn(name = "URL", order = 5)
    private String url;
    @ExcelColumn(name = "Country Monitored", order = 6)
    private String countryMonitored;
    @ExcelColumn(name = "Review Date", order = 7)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private String reviewDate;
    @ExcelColumn(name = "Seller Id and Contract Details", order = 8)
    private String sellerIdAndContractDetails;
    @ExcelColumn(name = "Vat", order = 9)
    private String vat;
    @ExcelColumn(name = "Physical Address", order = 10)
    private String physicalAddress;
    @ExcelColumn(name = "Email Address", order = 11)
    private String emailAddress;
    @ExcelColumn(name = "Phone Number", order = 12)
    private String phoneNumber;
    @ExcelColumn(name = "Correct Image", order = 13)
    private String correctImage;
    @ExcelColumn(name = "Naming", order = 14)
    private String naming;
    @ExcelColumn(name = "Product Name", order = 15)
    private String productName;
    @ExcelColumn(name = "SKU Naming", order = 16)
    private String skuNaming;
    @ExcelColumn(name = "Compatible Printers", order = 17)
    private String compatiblePrinters;
    @ExcelColumn(name = "Co Operation", order = 18)
    private String coOperation;
    @ExcelColumn(name = "Avoid Consumer Confusion", order = 19)
    private String avoidCustomerConfusion;
    @ExcelColumn(name = "Grey Trade", order = 20)
    private String greyTrade;
    @ExcelColumn(name = "Shopping Experience", order = 21)
    private String shoppingExperience;
}
