package com.sdn.service.impl.sdn;

import com.sdn.constants.Constants;
import com.sdn.dto.sdn.SdnDataDTO;
import com.sdn.dto.sdn.SdnFilterRequestDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.model.sdn.*;
import com.sdn.repository.SkuTypeRepository;
import com.sdn.repository.sdn.RegionRepository;
import com.sdn.repository.sdn.SdcViolationsRepository;
import com.sdn.repository.sdn.SdnDataChangeLogsRepository;
import com.sdn.repository.sdn.SdnDataRepository;
import com.sdn.service.UserValidationService;
import com.sdn.service.sdn.SdnCrudService;
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
import java.util.stream.Stream;

@Service
public class SdnServiceImpl implements SdnCrudService {
    @Autowired
    protected SdcViolationsRepository sdcViolationsRepository;
    @Autowired
    protected SdnDataRepository sdnDataRepository;
    @Autowired
    protected SdnDataChangeLogsRepository sdnDataChangeLogsRepository;
    @Autowired
    protected UserValidationService userValidationService;
    @Autowired
    protected RegionRepository regionRepository;
    @Autowired
    protected SkuTypeRepository skuTypeRepository;

    Logger logger = LoggerFactory.getLogger(SdnServiceImpl.class);
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);

    @Override
    public List<SdnData> getAllSdnData() {
        Date date = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -7);
        return sdnDataRepository.getSdnData(cal.getTime(), date);
    }

    @Override
    public List<SdnData> getAllSdnDataWithFilter(SdnFilterRequestDTO sdnFilterRequestDTO) {
        if (sdnFilterRequestDTO.getCustomerMarket() == null || sdnFilterRequestDTO.getCustomerMarket().isEmpty()) {
            return findSdnDataByCriteria(sdnFilterRequestDTO, null);
        } else {
            Region region = regionRepository.findByCustomerMarket(sdnFilterRequestDTO.getCustomerMarket());
            List<SdnData> sdnDataList = new LinkedList<>();
            for (Country country : region.getCountries()) {
                List<SdnData> sdnData = findSdnDataByCriteria(sdnFilterRequestDTO, country.getCountryMarketPlace());
                sdnDataList = Stream.concat(sdnDataList.stream(), sdnData.stream())
                        .collect(Collectors.toList());
            }
            return sdnDataList;
        }
    }

    public List<SdnData> findSdnDataByCriteria(SdnFilterRequestDTO sdnFilterRequestDTO, String countryMarketPlace) {
        return sdnDataRepository.findAll(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (countryMarketPlace != null && !countryMarketPlace.isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("countryMarketPlace"), countryMarketPlace)));
                    }
                    if (sdnFilterRequestDTO.getSku() != null && !sdnFilterRequestDTO.getSku().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("typeOfSku"), sdnFilterRequestDTO.getSku())));
                    }
                    if ((sdnFilterRequestDTO.getStartDate() != null && !sdnFilterRequestDTO.getStartDate().isEmpty()) && (sdnFilterRequestDTO.getEndDate() != null && !sdnFilterRequestDTO.getEndDate().isEmpty())) {
                        try {
                            predicates.add(criteriaBuilder.between(root.get("date"), sdf.parse(sdnFilterRequestDTO.getStartDate()), sdf.parse(sdnFilterRequestDTO.getEndDate())));
                        } catch (ParseException e) {
                            throw new CustomException("Invalid Date Format !", HttpStatus.BAD_REQUEST);
                        }
                    }
                    if (sdnFilterRequestDTO.getStartScore() != null && sdnFilterRequestDTO.getEndScore() != null) {
                        predicates.add(criteriaBuilder.between(root.get("sdcScoring"), sdnFilterRequestDTO.getStartScore(), sdnFilterRequestDTO.getEndScore()));
                    }
                    if (sdnFilterRequestDTO.getSdcViolations() != null && !sdnFilterRequestDTO.getSdcViolations().isEmpty()) {
                        List<String> sortedViolationList = Arrays.stream(sdnFilterRequestDTO.getSdcViolations().split(",")).sorted().collect(Collectors.toList());
                        String violations = String.join(",", sortedViolationList);
                        predicates.add(
                                criteriaBuilder.and(
                                        criteriaBuilder.or(
                                                criteriaBuilder.like(root.get("sdcViolations"), "%" + violations + "%"),
                                                criteriaBuilder.like(root.get("sdcViolations"), "%" + violations),
                                                criteriaBuilder.like(root.get("sdcViolations"), violations + "%")
                                        )
                                )
                        );
                    }
                    if (sdnFilterRequestDTO.getType() != null && !sdnFilterRequestDTO.getType().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"), sdnFilterRequestDTO.getType())));
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
    public List<String> getRegions() {
        List<Region> regions = regionRepository.findAll();
        List<String> regionList = new LinkedList<>();
        regions.forEach(region -> regionList.add(region.getCustomerMarket()));
        return regionList;
    }

    @Override
    public List<String> getSkus(SdnFilterRequestDTO sdnFilterRequestDTO) {
        List<String> mappingSKUsList = skuTypeRepository.getAllSkus();
        List<String> filteredSKUsList = new LinkedList<>();
        if (sdnFilterRequestDTO.getType().equalsIgnoreCase("both")
                && (sdnFilterRequestDTO.getCustomerMarket() == null || sdnFilterRequestDTO.getCustomerMarket().isEmpty())) {
            filteredSKUsList = mappingSKUsList.stream()
                    .filter(e -> sdnDataRepository.getAllSkus().contains(e)).collect(Collectors.toList());
            return filteredSKUsList;
        }
        if ((sdnFilterRequestDTO.getType().equalsIgnoreCase("contractual") || sdnFilterRequestDTO.getType().equalsIgnoreCase("transactional"))
                && (sdnFilterRequestDTO.getCustomerMarket() == null || sdnFilterRequestDTO.getCustomerMarket().isEmpty())) {
            filteredSKUsList = mappingSKUsList.stream()
                    .filter(e -> sdnDataRepository.getSkuByType(sdnFilterRequestDTO.getType()).contains(e)).collect(Collectors.toList());
            return filteredSKUsList;
        }
        Region region = regionRepository.findByCustomerMarket(sdnFilterRequestDTO.getCustomerMarket());
        List<String> skuList = new LinkedList<>();
        for (Country country : region.getCountries()) {
            List<String> sdnData;
            if (sdnFilterRequestDTO.getType().equalsIgnoreCase("both")) {
                sdnData = sdnDataRepository.getSkuByCountryMarketPlace(country.getCountryMarketPlace());
            } else {
                sdnData = sdnDataRepository.getSkuByCustomerMarketAndType(country.getCountryMarketPlace(), sdnFilterRequestDTO.getType());
            }
            skuList = Stream.concat(skuList.stream(), sdnData.stream())
                    .collect(Collectors.toList());
            filteredSKUsList = mappingSKUsList.stream()
                    .filter(skuList::contains).collect(Collectors.toList());
        }
        return filteredSKUsList;
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
