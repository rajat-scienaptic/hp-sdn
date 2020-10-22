package com.sdn.service;

/**
 * @author RAJAT MEENA
 */
public interface CSVConverterService {
    void convertToCSV(String sourceFileName, String targetFileName) throws Exception;
    void convertToCSVInBulk(String directoryPath) throws Exception;
}
