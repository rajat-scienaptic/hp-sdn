package com.sdn.dto.sdn;

import com.scienaptic.excel.annotations.ExcelColumn;
import lombok.Data;

@Data
public class SdnContractualMonitoringGoogleDTO {
    @ExcelColumn(header = "SKU", order = 1)
    private String sku;
    @ExcelColumn(header = "SKU Type", order = 2)
    private String skuType;
    @ExcelColumn(header = "Country Seller", order = 3)
    private String countrySeller;
    @ExcelColumn(header = "Domain Name", order = 4)
    private String domainName;
    @ExcelColumn(header = "URL", order = 5)
    private String url;
    @ExcelColumn(header = "Country Monitored", order = 6)
    private String countryMonitored;
    @ExcelColumn(header = "Position", order = 7)
    private Integer position;
    @ExcelColumn(header = "Collection Date", order = 8)
    private String collectionDate;
}
