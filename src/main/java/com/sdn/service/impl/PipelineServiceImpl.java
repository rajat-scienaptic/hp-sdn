package com.sdn.service.impl;

import com.sdn.model.sdn.*;
import com.sdn.repository.sdn.*;
import com.sdn.service.PipelineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PipelineServiceImpl implements PipelineService {
    @Autowired
    SdnContractualMonitoringGoogleRepository sdnContractualMonitoringGoogleRepository;
    @Autowired
    SdnContractualMonitoringMarketplacesRepository sdnContractualMonitoringMarketplacesRepository;
    @Autowired
    SdnTransactionMonitoringMarketplacesRepository sdnTransactionMonitoringMarketplacesRepository;
    @Autowired
    SdnTransactionMonitoringWebsitesRepository sdnTransactionMonitoringWebsitesRepository;
    @Autowired
    SdnDataRepository sdnDataRepository;
    @Autowired
    SdnEnforcementsRepository sdnEnforcementsRepository;

    @Override
    public void pushDataToSdn() {
        //final List<SdnContractualMonitoringGoogle> sdnContractualMonitoringGoogleList = sdnContractualMonitoringGoogleRepository.findAll();
        final List<SdnContractualMonitoringMarketPlaces> sdnContractualMonitoringMarketplacesRepositoryList = sdnContractualMonitoringMarketplacesRepository.findAll();
        //final List<SdnTransactionMonitoringWebsites> sdnTransactionMonitoringWebsitesList = sdnTransactionMonitoringWebsitesRepository.findAll();
        //final List<SdnTransactionMonitoringMarketplaces> sdnTransactionMonitoringMarketplacesList = sdnTransactionMonitoringMarketplacesRepository.findAll();
        final AtomicReference<SdnEnforcements> sdnEnforcements = new AtomicReference<>(new SdnEnforcements());

//        sdnContractualMonitoringGoogleList.forEach(x -> {
////                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
////                            x.getCountrySeller(),
////                            x.getSku(),
////                            x.getCollectionDate()));
//
//                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//                    Date date = null;
//
//                    try {
//                        date = format.parse(x.getCollectionDate());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    sdnDataRepository.save(SdnData.builder()
//                            .attendedSeller(null)
//                            .countryMarketPlace(x.getCountryMonitored())
//                            .countrySeller(x.getCountrySeller())
//                            .date(date)
//                            .partnerId(null)
//                            .sdcScoring(10)
//                            .sdcViolations(null)
//                            .sellerName(x.getCountrySeller())
//                            .skuNumber(x.getSku())
//                            .typeOfPartner(null)
//                            .typeOfSku(x.getSkuType())
//                            .notes(null)
//                            .type("Contractual")
//                            .enforcementsDate(sdnEnforcements.get().getPublicationDate())
//                            .enforcementsStatus(getEnforcementStatus(sdnEnforcements))
//                            .lastModifiedTimestamp(LocalDateTime.now())
//                            .url(x.getUrl())
//                            .build());
//                }
//        );
//
        sdnContractualMonitoringMarketplacesRepositoryList.forEach(x -> {
//                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
//                            x.getCountrySeller(),
//                            x.getSku(),
//                            x.getPublicationDate().toString()));

                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                    Date date = null;

                    try {
                        date = format.parse(x.getPublicationDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    sdnDataRepository.save(SdnData.builder()
                            .attendedSeller(null)
                            .countryMarketPlace(x.getCountryMarketPlace())
                            .countrySeller(x.getSellerLocation())
                            .date(date)
                            .partnerId(null)
                            .sdcScoring(10)
                            .sdcViolations(null)
                            .sellerName(x.getSellerName())
                            .skuNumber(x.getSku())
                            .typeOfPartner(null)
                            .typeOfSku(x.getSkuType())
                            .notes(null)
                            .type("Contractual")
                            .enforcementsDate(sdnEnforcements.get().getPublicationDate())
                            .enforcementsStatus(getEnforcementStatus(sdnEnforcements))
                            .lastModifiedTimestamp(LocalDateTime.now())
                            .url(x.getUrl())
                            .build());
                }
        );
//
//        sdnTransactionMonitoringMarketplacesList.forEach(x -> {
////                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
////                            x.getSellerLocation(),
////                            x.getSku(),
////                            x.getPublicationDate().toString()));
//
//                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//                    Date date = null;
//
//                    try {
//                        date = format.parse(x.getPublicationDate());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    sdnDataRepository.save(SdnData.builder()
//                            .attendedSeller(null)
//                            .countryMarketPlace(x.getCountryMarketplace())
//                            .countrySeller(x.getSellerLocation())
//                            .date(date)
//                            .partnerId(null)
//                            .sdcScoring((Integer) getViolationsAndScoring(x, null).get(Constants.SCORING))
//                            .sdcViolations((String) getViolationsAndScoring(x, null).get(Constants.VIOLATIONS))
//                            .sellerName(x.getSellerName())
//                            .skuNumber(x.getSku())
//                            .typeOfPartner(null)
//                            .typeOfSku(x.getSkuType())
//                            .notes(null)
//                            .type("Transactional")
//                            .enforcementsDate(sdnEnforcements.get().getPublicationDate())
//                            .enforcementsStatus(getEnforcementStatus(sdnEnforcements))
//                            .lastModifiedTimestamp(LocalDateTime.now())
//                            .url(x.getUrl())
//                            .build());
//                }
//        );

//        sdnTransactionMonitoringWebsitesList.forEach(y -> {
////                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
////                            y.getCountrySeller(),
////                            y.getSku(),
////                            y.getReviewDate().toString()));
//
//                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
//                    Date date = null;
//
//                    try {
//                        date = format.parse(y.getReviewDate());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    sdnDataRepository.save(SdnData.builder()
//                            .attendedSeller(null)
//                            .countryMarketPlace(y.getCountryMonitored())
//                            .countrySeller(y.getCountrySeller())
//                            .date(date)
//                            .partnerId(null)
//                            .sdcScoring((Integer) getViolationsAndScoring(null, y).get(Constants.SCORING))
//                            .sdcViolations((String) getViolationsAndScoring(null, y).get(Constants.VIOLATIONS))
//                            .sellerName(y.getDomainName())
//                            .skuNumber(y.getSku())
//                            .typeOfPartner(null)
//                            .typeOfSku(y.getSkuType())
//                            .notes(null)
//                            .type("Transactional")
//                            .enforcementsDate(null)
//                            .enforcementsStatus(null)
//                            .lastModifiedTimestamp(LocalDateTime.now())
//                            .url(y.getUrl())
//                            .build());
//                }
    }

    private Map<String, Object> getViolationsAndScoring(SdnTransactionMonitoringMarketplaces x, SdnTransactionMonitoringWebsites y) {
        SdcViolationsType sdcViolations = new SdcViolationsType();
        List<String> violationsList = new LinkedList<>();
        int sdcScore = 0;

        if (x != null) {
            BeanUtils.copyProperties(x, sdcViolations);
        } else {
            BeanUtils.copyProperties(y, sdcViolations);
        }

        if (sdcViolations.getAvoidCustomerConfusion() > 0) {
            violationsList.add("Avoid customer confusion");
            sdcScore = sdcScore + 10;
        }
        if (sdcViolations.getCompatiblePrinters() > 0) {
            violationsList.add("Compatible Printers");
        }
        if (sdcViolations.getCoOperation() > 0) {
            violationsList.add("Cooperation");
            sdcScore = sdcScore + 4;
        }
        if (sdcViolations.getCorrectImage() > 0) {
            violationsList.add("Correct image");
        }
        if (sdcViolations.getGreyTrade() > 0) {
            violationsList.add("Grey trade");
        }
        if (sdcViolations.getNaming() > 0) {
            violationsList.add("Naming");
        }
        if (sdcViolations.getSellerIdAndContractDetails() > 0) {
            violationsList.add("Seller identity and contact details");
            sdcScore = sdcScore + 4;
        }
        if (sdcViolations.getShoppingExperience() > 0) {
            violationsList.add("Shopping experience");
            sdcScore = sdcScore + 10;
        }

        List<String> sortedViolationList = violationsList.stream().sorted().collect(Collectors.toList());
        String violations = String.join(",", sortedViolationList);

        Map<String, Object> violationsAndScoringMap = new LinkedHashMap<>();
        violationsAndScoringMap.put("violations", violations);
        violationsAndScoringMap.put("scoring", sdcScore);

        return violationsAndScoringMap;
    }

    private String getEnforcementStatus(AtomicReference<SdnEnforcements> sdnEnforcements) {
        if (sdnEnforcements.get().getNoticeLetterSentDate() != null) {
            return sdnEnforcements.get().getNoticeLetterSentDate();
        }
        return sdnEnforcements.get().getWarningLetterSentDate();
    }
}
