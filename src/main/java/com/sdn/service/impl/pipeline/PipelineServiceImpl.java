package com.sdn.service.impl.pipeline;

import com.sdn.constants.Constants;
import com.sdn.exceptions.CustomException;
import com.sdn.repository.sdn.*;
import com.sdn.service.pipeline.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PipelineServiceImpl implements PipelineService {
    @Autowired
    protected SdnContractualMonitoringGoogleRepository sdnContractualMonitoringGoogleRepository;
    @Autowired
    protected SdnContractualMonitoringMarketplacesRepository sdnContractualMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringMarketplacesRepository sdnTransactionMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringWebsitesRepository sdnTransactionMonitoringWebsitesRepository;
    @Autowired
    protected SdnEnforcementsRepository sdnEnforcementsRepository;

    @Override
    public void pushDataToSdn() {
        pushDataToSdnContractualMonitoringMarketPlaces();
        pushDataToSdnContractualMonitoringGoogle();
        pushDataToSdnTransactionalMonitoringMarketPlaces();
        pushDataToSdnTransactionalMonitoringWebsites();
        pushDataToSdnEnforcement();

        sdnContractualMonitoringMarketplacesRepository.pushDataFromSCMMToSDN();
        sdnContractualMonitoringGoogleRepository.pushDataFromSCMGToSDN();
        sdnTransactionMonitoringMarketplacesRepository.pushDataFromSTMMToSDN();
        sdnTransactionMonitoringWebsitesRepository.pushDataFromSTMWToSDN();
    }

    private void pushDataToSdnContractualMonitoringMarketPlaces() {
        try {
            sdnContractualMonitoringMarketplacesRepository.deleteAll();
            sdnContractualMonitoringMarketplacesRepository.bulkLoadData();
            sdnContractualMonitoringMarketplacesRepository.updateSCMMSkuTypes();
            sdnContractualMonitoringMarketplacesRepository.deleteEmptyRows();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    private void pushDataToSdnContractualMonitoringGoogle() {
        try {
            sdnContractualMonitoringGoogleRepository.deleteAll();
            sdnContractualMonitoringGoogleRepository.bulkLoadData();
            sdnContractualMonitoringGoogleRepository.updateSCMGSkuTypes();
            sdnContractualMonitoringGoogleRepository.deleteEmptyRows();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    private void pushDataToSdnTransactionalMonitoringMarketPlaces() {
        try {
            sdnTransactionMonitoringMarketplacesRepository.deleteAll();
            sdnTransactionMonitoringMarketplacesRepository.bulkLoadData();
            sdnTransactionMonitoringMarketplacesRepository.deleteEmptyRows();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    private void pushDataToSdnTransactionalMonitoringWebsites() {
        try {
            sdnTransactionMonitoringWebsitesRepository.deleteAll();
            sdnTransactionMonitoringWebsitesRepository.bulkLoadData();
            sdnTransactionMonitoringWebsitesRepository.deleteEmptyRows();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    private void pushDataToSdnEnforcement() {
        try {
            sdnEnforcementsRepository.deleteAll();
            sdnEnforcementsRepository.bulkLoadData();
            sdnEnforcementsRepository.deleteEmptyRows();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public void bulkLoadDataToAllTables(String directoryPath) {
        File folder = new File(directoryPath);
        if (Objects.requireNonNull(folder.listFiles()).length > 0) {
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.getName().contains("Contractual Monitoring Marketplaces")) {
                    String sourceFile = directoryPath + fileEntry.getName();
                    String targetFile = directoryPath + Constants.SCMM_CSV_FILENAME;
                    renameFile(sourceFile, targetFile);
                    sdnContractualMonitoringMarketplacesRepository.bulkLoadDataToAllTables(
                            returnModifiedQuery(targetFile, "sdn_contractual_monitoring_marketplaces")
                    );
                    sdnContractualMonitoringMarketplacesRepository.updateSCMMSkuTypes();
                    renameFile(targetFile, sourceFile);
                }

                if (fileEntry.getName().contains("Contractual Monitoring Google")) {
                    String sourceFile = directoryPath + fileEntry.getName();
                    String targetFile = directoryPath + Constants.SCMG_CSV_FILENAME;
                    renameFile(sourceFile, targetFile);
                    sdnContractualMonitoringMarketplacesRepository.bulkLoadDataToAllTables(
                            returnModifiedQuery(targetFile, "sdn_contractual_monitoring_google")
                    );
                    sdnContractualMonitoringMarketplacesRepository.updateSCMGSkuTypes();
                    renameFile(targetFile, sourceFile);
                }

                if (fileEntry.getName().contains("Transactional Monitoring Marketplaces")) {
                    String sourceFile = directoryPath + fileEntry.getName();
                    String targetFile = directoryPath + Constants.STMM_CSV_FILENAME;
                    renameFile(sourceFile, targetFile);
                    sdnContractualMonitoringMarketplacesRepository.bulkLoadDataToAllTables(
                            returnModifiedQuery(targetFile, "sdn_transaction_monitoring_marketplaces")
                    );
                    renameFile(targetFile, sourceFile);
                }

                if (fileEntry.getName().contains("Transactional Monitoring Websites")) {
                    String sourceFile = directoryPath + fileEntry.getName();
                    String targetFile = directoryPath + Constants.STMW_CSV_FILENAME;
                    renameFile(sourceFile, targetFile);
                    sdnContractualMonitoringMarketplacesRepository.bulkLoadDataToAllTables(
                            returnModifiedQuery(targetFile, "sdn_transaction_monitoring_websites")
                    );
                    renameFile(targetFile, sourceFile);
                }

                if (fileEntry.getName().contains("Contractual Enforcement")) {
                    String sourceFile = directoryPath + fileEntry.getName();
                    String targetFile = directoryPath + Constants.CONTRACTUAL_ENFORCEMENT_CSV_FILENAME;
                    renameFile(sourceFile, targetFile);
                    sdnContractualMonitoringMarketplacesRepository.bulkLoadDataToAllTables(
                            returnModifiedQuery(targetFile, "sdn_enforcements")
                    );
                    renameFile(targetFile, sourceFile);
                }
            }
        }
    }

    private void renameFile(String source, String target) {
        Path f = Paths.get(source);
        Path rF = Paths.get(target);
        try {
            Files.move(f, rF, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String returnModifiedQuery(String filePath, String tableName) {
        if(tableName.equals("sdn_enforcements")){
            return "LOAD DATA LOCAL INFILE " +
                    "'" + filePath + "'" +
                    " INTO TABLE hp_sdn." + tableName + " FIELDS TERMINATED " +
                    "BY ',' ENCLOSED BY '\\\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES " +
                    "(url, platform, country_marketplace, seller_name, seller_id, seller_location, sku, sku_type, publication_date, notice_letter_sent_date, warning_letter_sent_date, status)";
        }
        return "LOAD DATA LOCAL INFILE " +
                "'" + filePath + "'" +
                " INTO TABLE hp_sdn." + tableName + " FIELDS TERMINATED " +
                "BY ',' ENCLOSED BY '\\\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES";
    }
}
