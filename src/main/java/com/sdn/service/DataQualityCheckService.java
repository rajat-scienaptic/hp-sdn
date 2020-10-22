package com.sdn.service;

import java.io.IOException;
import java.text.ParseException;

public interface DataQualityCheckService {
    void getQualityCheckViolatingRowsAndGenerateReport() throws ParseException, IOException;
    void updateSkuTypeofTestBuyData();
}
