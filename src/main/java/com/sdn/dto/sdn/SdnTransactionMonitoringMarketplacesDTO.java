package com.sdn.dto.sdn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scienaptic.excel.annotations.ExcelColumn;
import lombok.Data;

@Data
public class SdnTransactionMonitoringMarketplacesDTO {
    @ExcelColumn(name = "URL", order = 1)
    private String url;
    @ExcelColumn(name = "Platform", order = 2)
    private String platform;
    @ExcelColumn(name = "Country Marketplace", order = 3)
    private String countryMarketplace;
    @ExcelColumn(name = "Seller Name", order = 4)
    private String sellerName;
    @ExcelColumn(name = "Seller ID", order = 5)
    private String sellerId;
    @ExcelColumn(name = "Seller Location", order = 6)
    private String sellerLocation;
    @ExcelColumn(name = "SKU", order = 7)
    private String sku;
    @ExcelColumn(name = "SKU Type", order = 8)
    private String skuType;
    @ExcelColumn(name = "Publication Date", order = 9)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private String publicationDate;
    @ExcelColumn(name = "Seller Id and Contract Details", order = 10)
    private String sellerIdAndContractDetails;
    @ExcelColumn(name = "Vat", order = 11)
    private String vat;
    @ExcelColumn(name = "Physical Address", order = 12)
    private String physicalAddress;
    @ExcelColumn(name = "Email Address", order = 13)
    private String emailAddress;
    @ExcelColumn(name = "Phone Number", order = 14)
    private String phoneNumber;
    @ExcelColumn(name = "Correct Image", order = 15)
    private String correctImage;
    @ExcelColumn(name = "Naming", order = 16)
    private String naming;
    @ExcelColumn(name = "Product Name", order = 17)
    private String productName;
    @ExcelColumn(name = "SKU Naming", order = 18)
    private String skuNaming;
    @ExcelColumn(name = "Compatible Printers", order = 19)
    private String compatiblePrinters;
    @ExcelColumn(name = "Co Operation", order = 20)
    private String coOperation;
    @ExcelColumn(name = "Avoid Consumer Confusion", order = 21)
    private String avoidCustomerConfusion;
    @ExcelColumn(name = "Grey Trade", order = 22)
    private String greyTrade;
    @ExcelColumn(name = "Shopping Experience", order = 23)
    private String shoppingExperience;
}
