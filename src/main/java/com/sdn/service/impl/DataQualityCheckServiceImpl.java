package com.sdn.service.impl;

import com.scienaptic.excel.ExcelBuilder;
import com.scienaptic.excel.ExcelHelper;
import com.sdn.dto.ViolationFieldsDTO;
import com.sdn.dto.sdn.SdnContractualMonitoringGoogleDTO;
import com.sdn.dto.sdn.SdnContractualMonitoringMarketPlacesDTO;
import com.sdn.dto.sdn.SdnTransactionMonitoringMarketplacesDTO;
import com.sdn.dto.sdn.SdnTransactionMonitoringWebsitesDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.model.SkuNumberMapping;
import com.sdn.model.SkuType;
import com.sdn.model.testbuy.TestBuy;
import com.sdn.repository.SkuNumberMappingRepository;
import com.sdn.repository.SkuTypeRepository;
import com.sdn.repository.sdn.*;
import com.sdn.repository.testbuy.TestBuyRepository;
import com.sdn.service.DataQualityCheckService;
import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class DataQualityCheckServiceImpl implements DataQualityCheckService {
    @Autowired
    protected SdnDataRepository sdnDataRepository;
    @Autowired
    protected SdnContractualMonitoringGoogleRepository sdnContractualMonitoringGoogleRepository;
    @Autowired
    protected SdnContractualMonitoringMarketplacesRepository sdnContractualMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringMarketplacesRepository sdnTransactionMonitoringMarketplacesRepository;
    @Autowired
    protected SdnTransactionMonitoringWebsitesRepository sdnTransactionMonitoringWebsitesRepository;
    @Autowired
    protected TestBuyRepository testBuyRepository;
    @Autowired
    protected SkuTypeRepository skuTypeRepository;
    @Autowired
    protected SkuNumberMappingRepository skuNumberMappingRepository;

    protected Logger logger = LoggerFactory.getLogger(DataQualityCheckServiceImpl.class);
    protected static final String SHEET = "sheet1";
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected ModelMapper modelMapper = new ModelMapper();
    protected static final String FILE_PATH = "src/main/resources/static";

    @Override
    public void getQualityCheckViolatingRowsAndGenerateReport() throws IOException {
        List<SdnContractualMonitoringMarketPlacesDTO> contractualMarketPlacesData = Arrays.asList(
                modelMapper.map(sdnContractualMonitoringMarketplacesRepository.findAll(),
                        SdnContractualMonitoringMarketPlacesDTO[].class
                ));
        List<SdnContractualMonitoringGoogleDTO> contractualGoogleData = Arrays.asList(
                modelMapper.map(sdnContractualMonitoringGoogleRepository.findAll(),
                        SdnContractualMonitoringGoogleDTO[].class
                ));
        List<SdnTransactionMonitoringMarketplacesDTO> transactionalMarketPlacesData = Arrays.asList(
                modelMapper.map(sdnTransactionMonitoringMarketplacesRepository.findAll(),
                        SdnTransactionMonitoringMarketplacesDTO[].class
                ));
        List<SdnTransactionMonitoringWebsitesDTO> transactionalWebsitesData = Arrays.asList(
                modelMapper.map(sdnTransactionMonitoringWebsitesRepository.findAll(),
                        SdnTransactionMonitoringWebsitesDTO[].class
                ));


        List<String> skuList = skuNumberMappingRepository.getAllSkuNumbers();
        List<String> skuTypeList = skuTypeRepository.getAllSkus();

        deleteAllFilesFromDirectory(new File(FILE_PATH));

        doQualityCheckOnContractualMarketPlacesData(contractualMarketPlacesData, skuList, skuTypeList);
        doQualityCheckOnContractualGoogleData(contractualGoogleData, skuList, skuTypeList);
        doQualityCheckOnTransactionalMarketPlacesData(transactionalMarketPlacesData, skuList, skuTypeList);
        doQualityCheckOnTransactionalWebsitesData(transactionalWebsitesData, skuList, skuTypeList);
    }

    private void doQualityCheckOnContractualMarketPlacesData(List<SdnContractualMonitoringMarketPlacesDTO> sdnData, List<String> skuList, List<String> skuTypeList) throws IOException {
        List<Object> violationDataList = getViolationDataList(sdnData, skuList, skuTypeList);
        if (!violationDataList.isEmpty()) {
            writeToExcel(violationDataList, "sdn_contractual_monitoring_marketplaces.xlsx");
        }
    }

    private void doQualityCheckOnContractualGoogleData(List<SdnContractualMonitoringGoogleDTO> sdnData, List<String> skuList, List<String> skuTypeList) throws IOException {
        List<Object> violationDataList = getViolationDataList(sdnData, skuList, skuTypeList);
        if (!violationDataList.isEmpty()) {
            writeToExcel(violationDataList, "sdn_contractual_monitoring_google.xlsx");
        }
    }

    private void doQualityCheckOnTransactionalMarketPlacesData(List<SdnTransactionMonitoringMarketplacesDTO> sdnData, List<String> skuList, List<String> skuTypeList) throws IOException {
        List<Object> violationDataList = getViolationDataList(sdnData, skuList, skuTypeList);
        if (!violationDataList.isEmpty()) {
            writeToExcel(violationDataList, "sdn_transactional_monitoring_marketplaces.xlsx");
        }
    }

    private void doQualityCheckOnTransactionalWebsitesData(List<SdnTransactionMonitoringWebsitesDTO> sdnData, List<String> skuList, List<String> skuTypeList) throws IOException {
        List<Object> violationDataList = getViolationDataList(sdnData, skuList, skuTypeList);
        if (!violationDataList.isEmpty()) {
            writeToExcel(violationDataList, "sdn_transactional_monitoring_websites.xlsx");
        }
    }

    private List<Object> getViolationDataList(Object dataObject, List<String> skuList, List<String> skuTypeList) {
        List<Object> violationDataList = new LinkedList<>();
        List<Object> dataObjectList = Arrays.asList(
                modelMapper.map(dataObject,
                        Object[].class
                ));
        List<ViolationFieldsDTO> sdnData = Arrays.asList(
                modelMapper.map(dataObject,
                        ViolationFieldsDTO[].class
                ));
        IntStream.range(0, sdnData.size())
                .forEach(index -> {
                            if (sdnData.get(index).getSku() !=null && (!skuList.contains(sdnData.get(index).getSku().trim()) || (
                                    !skuTypeList.contains(sdnData.get(index).getSkuType().trim()) &&
                                            (!sdnData.get(index).getSkuType().trim().equalsIgnoreCase("A-SKU Ink") &&
                                                    !sdnData.get(index).getSkuType().trim().equalsIgnoreCase("A-SKU Toner"))
                            ))) {
                                violationDataList.add(dataObjectList.get(index));
                            }
                        }
                )
        ;
        return violationDataList;
    }

    public void writeToExcel(List<Object> violationDataList, String fileName) throws IOException {
        try {
            ExcelHelper excelHelper = new ExcelBuilder().sheet().title("Sheet1").from(violationDataList).endSheet().build();
            excelHelper.toFile("src/main/resources/static/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSkuTypeofTestBuyData() {
        Iterable<TestBuy> testBuyList = testBuyRepository.findAll();
        try {
            testBuyList.forEach(testBuy -> {
                SkuNumberMapping skuNumberMapping = skuNumberMappingRepository.findBySkuNumber(testBuy.getSkuNumber());
                if (skuNumberMapping != null) {
                    Optional<SkuType> skuType = skuTypeRepository.findById(skuNumberMapping.getSkuTypeId());
                    if (skuType.isPresent()) {
                        testBuy.setSkuType(skuType.get().getType());
                        testBuyRepository.save(testBuy);
                    } else {
                        throw new CustomException("No Sku Type found for Sku Number: " + testBuy.getSkuNumber(), HttpStatus.BAD_REQUEST);
                    }
                }
            });
            logger.info("Test Buy Data Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAllFilesFromDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    deleteAllFilesFromDirectory(aFile);
                }
            }
        }
    }
}
