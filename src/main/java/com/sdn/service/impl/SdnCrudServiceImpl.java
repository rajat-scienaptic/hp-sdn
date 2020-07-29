package com.sdn.service.impl;

import com.sdn.constants.Constants;
import com.sdn.dto.CountrySkuDTO;
import com.sdn.dto.FilterRequestDTO;
import com.sdn.dto.SdnDataDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.model.SdcViolations;
import com.sdn.model.SdnData;
import com.sdn.model.SdnDataChangeLogs;
import com.sdn.repository.*;
import com.sdn.service.SdnCrudService;
import com.sdn.service.UserValidationService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SdnCrudServiceImpl implements SdnCrudService {
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    SdcViolationsRepository sdcViolationsRepository;
    @Autowired
    SdnDataRepository sdnDataRepository;
    @Autowired
    SdnDataChangeLogsRepository sdnDataChangeLogsRepository;
    @Autowired
    UserValidationService userValidationService;

    Logger logger = LoggerFactory.getLogger(SdnCrudServiceImpl.class);
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);

    @Override
    public List<SdnData> getAllSdnData() {
        return sdnDataRepository.getSdnData();
    }

    @Override
    public List<SdnData> getAllSdnDataWithFilter(FilterRequestDTO filterRequestDTO) {
        return findSdnDataByCriteria(filterRequestDTO);
    }

    public List<SdnData> findSdnDataByCriteria(FilterRequestDTO filterRequestDTO) {
        return sdnDataRepository.findAll(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (filterRequestDTO.getCountry() != null && !filterRequestDTO.getCountry().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("countryMarketPlace"), filterRequestDTO.getCountry())));
                    }
                    if (filterRequestDTO.getSku() != null && !filterRequestDTO.getSku().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("typeOfSku"), filterRequestDTO.getSku())));
                    }
                    if ((filterRequestDTO.getStartDate() != null && !filterRequestDTO.getStartDate().isEmpty()) && (filterRequestDTO.getEndDate() != null && !filterRequestDTO.getEndDate().isEmpty())) {
                        try {
                            predicates.add(criteriaBuilder.between(root.get("date"), sdf.parse(filterRequestDTO.getStartDate()), sdf.parse(filterRequestDTO.getEndDate())));
                        } catch (ParseException e) {
                            throw new CustomException("Invalid Date Format !", HttpStatus.BAD_REQUEST);
                        }
                    }
                    if (filterRequestDTO.getStartScore() != null && filterRequestDTO.getEndScore() != null) {
                        predicates.add(criteriaBuilder.between(root.get("sdcScoring"), filterRequestDTO.getStartScore(), filterRequestDTO.getEndScore()));
                    }
                    if (filterRequestDTO.getSdcViolations() != null && !filterRequestDTO.getSdcViolations().isEmpty()) {
                        List<String> sortedViolationList = Arrays.stream(filterRequestDTO.getSdcViolations().split(",")).sorted().collect(Collectors.toList());
                        String violations = String.join(",", sortedViolationList);
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sdcViolations"), violations)));
                    }
                    if (filterRequestDTO.getType() != null && !filterRequestDTO.getType().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), filterRequestDTO.getType())));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });
    }

    @Override
    public Optional<SdnData> updateSdnData(int id, SdnDataDTO sdnDataDTO, String cookie) throws ParseException {
        Optional<SdnData> sdnData = sdnDataRepository.findById(id);
        if (sdnData.isPresent()) {
            SdnData sd = sdnData.get();
            sd.setEnforcementsStatus(sdnDataDTO.getEnforcementsStatus());
            sd.setEnforcementsDate(sdnDataDTO.getEnforcementsDate());
            sd.setLastModifiedTimestamp(LocalDateTime.now());
            if (sdnDataDTO.getNotes() != null) {
                sd.setNotes(sdnDataDTO.getNotes());
            }
            sdnDataRepository.save(sd);
            updateSdnLogs(id, cookie);
            String message = "Record with id " + id + " has been successfully updated !";
            logger.info(message);
            return sdnDataRepository.findById(id);
        } else {
            throw new CustomException("Record that you are trying to update is not found !", HttpStatus.NOT_FOUND);
        }
    }

    private void updateSdnLogs(int id, String cookie) {
        Optional<SdnData> sdnData = sdnDataRepository.findById(id);
        sdnData.ifPresent(data -> sdnDataChangeLogsRepository.save(SdnDataChangeLogs.builder()
                .url(data.getUrl())
                .typeOfSku(data.getTypeOfSku())
                .userName(userValidationService.getUserNameFromCookie(cookie))
                .skuNumber(data.getSkuNumber())
                .typeOfPartner(data.getTypeOfPartner())
                .sellerName(data.getSellerName())
                .sdcViolations(data.getSdcViolations())
                .countrySeller(data.getCountrySeller())
                .countryMarketPlace(data.getCountryMarketPlace())
                .attendedSeller(data.getAttendedSeller())
                .partnerId(data.getPartnerId())
                .sdcScoring(data.getSdcScoring())
                .enforcementsStatus(data.getEnforcementsStatus())
                .enforcementsDate(data.getEnforcementsDate())
                .notes(data.getNotes())
                .date(data.getDate())
                .dataId(data.getId())
                .lastModifiedTimestamp(LocalDateTime.now())
                .build()));
    }

    @Override
    public List<SdnDataChangeLogs> archiveSdnData(int id) {
        List<SdnDataChangeLogs> sdnDataChangeLogsList = sdnDataChangeLogsRepository.findAllByDataId(id);
        if (!sdnDataChangeLogsList.isEmpty()) {
            return sdnDataChangeLogsList;
        } else {
            throw new CustomException("No history found for record with id : " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<String> getCountries(CountrySkuDTO countrySkuDTO) {
        if (countrySkuDTO.getType().equalsIgnoreCase("both")) {
            return sdnDataRepository.getCountries();
        }
        return sdnDataRepository.getCountriesByType(countrySkuDTO.getType());
    }

    @Override
    public List<String> getSku(CountrySkuDTO countrySkuDTO) {
        if(countrySkuDTO.getType().equalsIgnoreCase("both")){
          return sdnDataRepository.getSKUs(countrySkuDTO.getCountryMarketPlace());
        }
        return sdnDataRepository.getSKUsByMarketPlaceAndType(countrySkuDTO.getCountryMarketPlace(), countrySkuDTO.getType());
    }

    @Override
    public void fillSDNData(MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            Sheet sheet = workbook.getSheet("sheet1");
            Iterator<Row> rows = sheet.iterator();

            List<SdnData> sdnDataList = new LinkedList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                SdnData sdnData = new SdnData();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            sdnData.setCountryMarketPlace(currentCell.getStringCellValue());
                            break;
                        case 1:
                            sdnData.setCountrySeller(currentCell.getStringCellValue());
                            break;
                        case 2:
                            sdnData.setSellerName(currentCell.getStringCellValue());
                            break;
                        case 3:
                            sdnData.setPartnerId((int) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            sdnData.setAttendedSeller(currentCell.getStringCellValue());
                            break;
                        case 5:
                            sdnData.setTypeOfPartner(currentCell.getStringCellValue());
                            break;
                        case 6:
                            sdnData.setTypeOfSku(currentCell.getStringCellValue());
                            break;
                        case 7:
                            sdnData.setSkuNumber(currentCell.getStringCellValue());
                            break;
                        case 8:
                            sdnData.setUrl(currentCell.getStringCellValue());
                            break;
                        case 9:
                            sdnData.setDate(sdf.parse(currentCell.getStringCellValue()));
                            break;
                        case 10:
                            sdnData.setSdcScoring((int) currentCell.getNumericCellValue());
                            break;
                        case 11:
                            sdnData.setSdcViolations(currentCell.getStringCellValue());
                            break;
                        case 12:
                            sdnData.setEnforcementsStatus(currentCell.getStringCellValue());
                            break;
                        case 13:
                            sdnData.setEnforcementsDate(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                sdnData.setLastModifiedTimestamp(LocalDateTime.now());
                sdnDataList.add(sdnData);
            }
            workbook.close();
            sdnDataRepository.saveAll(sdnDataList);
        } catch (IOException | ParseException e) {
            throw new CustomException("fail to parse Excel file: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<SdcViolations> getSdcViolations() {
        return sdcViolationsRepository.findAll();
    }

}
