package com.sdn.service.impl.testbuy;

import com.sdn.constants.Constants;
import com.sdn.dto.testbuy.TestBuyFilterRequestDTO;
import com.sdn.exceptions.CustomException;
import com.sdn.model.sdn.Country;
import com.sdn.model.sdn.Region;
import com.sdn.model.testbuy.TestBuy;
import com.sdn.repository.sdn.RegionRepository;
import com.sdn.repository.testbuy.TestBuyRepository;
import com.sdn.service.testbuy.TestBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TestBuyServiceImpl implements TestBuyService {
    @Autowired
    TestBuyRepository testBuyRepository;
    @Autowired
    RegionRepository regionRepository;

    SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);

    @Override
    public List<TestBuy> getTestBuyData() {
//        Date date = new Date();
//        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(date);
//        cal.add(Calendar.DATE, -7);
        return testBuyRepository.getTestBuyData();
    }

    @Override
    public List<TestBuy> getAllSTestBuyDataWithFilter(TestBuyFilterRequestDTO testBuyFilterRequestDTO) {
        if (testBuyFilterRequestDTO.getCustomerMarket() == null || testBuyFilterRequestDTO.getCustomerMarket().isEmpty()) {
            return findTestBuyDataByCriteria(testBuyFilterRequestDTO, null);
        } else {
            Region region = regionRepository.findByCustomerMarket(testBuyFilterRequestDTO.getCustomerMarket());
            List<TestBuy> testBuyDataList = new LinkedList<>();
            for (Country country : region.getCountries()) {
                List<TestBuy> sdnData = findTestBuyDataByCriteria(testBuyFilterRequestDTO, country.getCountryMarketPlace());
                testBuyDataList = Stream.concat(testBuyDataList.stream(), sdnData.stream())
                        .collect(Collectors.toList());
            }
            return testBuyDataList;
        }
    }

    public List<TestBuy> findTestBuyDataByCriteria(TestBuyFilterRequestDTO testBuyFilterRequestDTO, String countryMarketPlace) {
        return testBuyRepository.findAll(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (countryMarketPlace != null && !countryMarketPlace.isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("countryMarketPlace"), countryMarketPlace)));
                    }
                    if (testBuyFilterRequestDTO.getSku() != null && !testBuyFilterRequestDTO.getSku().isEmpty()) {
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("skuNumber"), testBuyFilterRequestDTO.getSku())));
                    }
                    if ((testBuyFilterRequestDTO.getStartDate() != null && !testBuyFilterRequestDTO.getStartDate().isEmpty()) && (testBuyFilterRequestDTO.getEndDate() != null && !testBuyFilterRequestDTO.getEndDate().isEmpty())) {
                        try {
                            predicates.add(criteriaBuilder.between(root.get("dateOfRequest"), sdf.parse(testBuyFilterRequestDTO.getStartDate()), sdf.parse(testBuyFilterRequestDTO.getEndDate())));
                        } catch (ParseException e) {
                            throw new CustomException("Invalid Date Format !", HttpStatus.BAD_REQUEST);
                        }
                    }
                    if (testBuyFilterRequestDTO.getTestBuyViolations() != null && !testBuyFilterRequestDTO.getTestBuyViolations().isEmpty()) {
                        List<String> sortedViolationList = Arrays.stream(testBuyFilterRequestDTO.getTestBuyViolations().split(",")).sorted().collect(Collectors.toList());
                        String violations = String.join(",", sortedViolationList);
                        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("testBuyViolations"), violations)));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
        );
    }
}