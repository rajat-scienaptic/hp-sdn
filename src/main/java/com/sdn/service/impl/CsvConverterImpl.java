package com.sdn.service.impl;

import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.sdn.service.CSVConverterService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

/**
 * @author RAJAT MEENA
 */
@Service
public class CsvConverterImpl implements CSVConverterService {
    @Override
    public void convertToCSV(String sourceFileName, String targetFileName) throws Exception {
        Workbook workbook = new Workbook(sourceFileName);
        // Save output CSV file
        workbook.save(targetFileName, SaveFormat.CSV);
    }

    @Override
    public void convertToCSVInBulk(String directoryPath) throws Exception {
        File folder = new File(directoryPath);
        if (Objects.requireNonNull(folder.listFiles()).length > 0) {
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
              convertToCSV(directoryPath+fileEntry.getName(), directoryPath+"csv\\"+fileEntry.getName().split("\\.")[0]+".csv");
            }
        }
    }
}
