package com.sdn.controller;

import com.sdn.dto.ApiResponseDTO;
import com.sdn.dto.CsvDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.repository.sdn.SdnContractualMonitoringGoogleRepository;
import com.sdn.repository.sdn.SdnContractualMonitoringMarketplacesRepository;
import com.sdn.repository.sdn.SdnTransactionMonitoringMarketplacesRepository;
import com.sdn.repository.sdn.SdnTransactionMonitoringWebsitesRepository;
import com.sdn.scheduler.SDNScheduler;
import com.sdn.service.CSVConverterService;
import com.sdn.service.pipeline.FileSystemService;
import com.sdn.service.pipeline.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/api/v1")
@RestController
public class PipelineController {
    @Autowired
    protected FileSystemService fileSystemService;
    @Autowired
    protected SDNScheduler sdnScheduler;
    @Autowired
    protected CSVConverterService csvConverterService;
    @Autowired
    protected PipelineService pipelineService;
    @Autowired
    protected SdnContractualMonitoringGoogleRepository sdnContractualMonitoringGoogleRepository;
    @Autowired
    protected SdnContractualMonitoringMarketplacesRepository sdnContractualMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringMarketplacesRepository sdnTransactionMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringWebsitesRepository sdnTransactionMonitoringWebsitesRepository;

    @GetMapping("/pushDataToSdn")
    public final ResponseEntity<Object> pushDataToSdn() {
        try {
            sdnScheduler.pushDataToSDNAndDoQualityCheck();
            return new ResponseEntity<>(ApiResponseDTO.builder()
                    .message("Data Successfully Pushed To SDN")
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/downloadFileFromFTP")
    public final void downloadFileFromFTP() throws Exception {
        fileSystemService.downloadFile("index.php", "src/main/resources/index.php");
    }

    @PostMapping("/convertToCSV")
    public final void convertToCSV(@RequestBody CsvDTO csvDTO) throws Exception {
        csvConverterService.convertToCSVInBulk(csvDTO.getDirectoryPath());
    }

    @PostMapping("/bulkLoadDataToAllTables")
    public final void bulkLoadDataToAllTables(@RequestBody CsvDTO csvDTO) {
        pipelineService.bulkLoadDataToAllTables(csvDTO.getDirectoryPath());
    }

    @GetMapping("/pushDataToFromAllTablesToSdn")
    public final void pushDataToFromAllTablesToSdn(){
        sdnContractualMonitoringMarketplacesRepository.pushDataFromSCMMToSDN();
        sdnContractualMonitoringGoogleRepository.pushDataFromSCMGToSDN();
        sdnTransactionMonitoringMarketplacesRepository.pushDataFromSTMMToSDN();
        sdnTransactionMonitoringWebsitesRepository.pushDataFromSTMWToSDN();
    }
}
