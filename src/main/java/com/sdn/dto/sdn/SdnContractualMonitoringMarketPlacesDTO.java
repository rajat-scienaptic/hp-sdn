package com.sdn.dto.sdn;

import com.scienaptic.excel.annotations.ExcelColumn;
import lombok.Data;

@Data
public class SdnContractualMonitoringMarketPlacesDTO {
    @ExcelColumn(name = "URL", order = 1)
    private String url;
    @ExcelColumn(name = "Platform", order = 2)
    private String platform;
    @ExcelColumn(name = "Country Marketplace", order = 3)
    private String countryMarketplace;
    @ExcelColumn(name = "Seller Name", order = 4)
    private String sellerName;
    @ExcelColumn(name = "Seller Id", order = 5)
    private String sellerId;
    @ExcelColumn(name = "Seller Location", order = 6)
    private String sellerLocation;
    @ExcelColumn(name = "SKU", order = 7)
    private String sku;
    @ExcelColumn(name = "SKU Type", order = 8)
    private String skuType;
    @ExcelColumn(name = "Publication Date", order = 9)
    private String publicationDate;
}
