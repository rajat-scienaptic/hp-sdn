package com.sdn.service.impl;

import com.sdn.constants.Constants;
import com.sdn.model.*;
import com.sdn.repository.*;
import com.sdn.service.PipelineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        final List<SdnContractualMonitoringGoogle> sdnContractualMonitoringGoogleList = sdnContractualMonitoringGoogleRepository.findAll();
        final List<SdnContractualMonitoringMarketPlaces> sdnContractualMonitoringMarketplacesRepositoryList = sdnContractualMonitoringMarketplacesRepository.findAll();
        final List<SdnTransactionMonitoringWebsites> sdnTransactionMonitoringWebsitesList = sdnTransactionMonitoringWebsitesRepository.findAll();
        final List<SdnTransactionMonitoringMarketplaces> sdnTransactionMonitoringMarketplacesList = sdnTransactionMonitoringMarketplacesRepository.findAll();
        final AtomicReference<SdnEnforcements> sdnEnforcements = new AtomicReference<>(new SdnEnforcements());

        sdnContractualMonitoringGoogleList.forEach(x -> {
                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
                            x.getCountrySeller(),
                            x.getSku(),
                            x.getCollectionDate()));

                    sdnDataRepository.save(SdnData.builder()
                            .attendedSeller(null)
                            .countryMarketPlace(null)
                            .countrySeller(x.getCountrySeller())
                            .date(null)
                            .partnerId(null)
                            .sdcScoring(10)
                            .sdcViolations(null)
                            .sellerName(null)
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

        sdnContractualMonitoringMarketplacesRepositoryList.forEach(x -> {
                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
                            x.getCountrySeller(),
                            x.getSku(),
                            x.getPublicationDate().toString()));

                    sdnDataRepository.save(SdnData.builder()
                            .attendedSeller(null)
                            .countryMarketPlace(x.getCountryMarketPlace())
                            .countrySeller(x.getCountrySeller())
                            .date(x.getPublicationDate())
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

        sdnTransactionMonitoringMarketplacesList.forEach(x -> {
                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
                            x.getCountrySeller(),
                            x.getSku(),
                            x.getPublicationDate().toString()));

                    sdnDataRepository.save(SdnData.builder()
                            .attendedSeller(null)
                            .countryMarketPlace(x.getCountryMarketplace())
                            .countrySeller(x.getCountrySeller())
                            .date(x.getPublicationDate())
                            .partnerId(null)
                            .sdcScoring((Integer) getViolationsAndScoring(x, null).get(Constants.SCORING))
                            .sdcViolations((String) getViolationsAndScoring(x, null).get(Constants.VIOLATIONS))
                            .sellerName(x.getSellerName())
                            .skuNumber(x.getSku())
                            .typeOfPartner(null)
                            .typeOfSku(x.getSkuType())
                            .notes(null)
                            .type("Transactional")
                            .enforcementsDate(sdnEnforcements.get().getPublicationDate())
                            .enforcementsStatus(getEnforcementStatus(sdnEnforcements))
                            .lastModifiedTimestamp(LocalDateTime.now())
                            .url(x.getUrl())
                            .build());
                }
        );

        sdnTransactionMonitoringWebsitesList.forEach(y -> {
                    sdnEnforcements.set(sdnEnforcementsRepository.findBySellerNameAndSkuAndPublicationDate(
                            y.getCountrySeller(),
                            y.getSku(),
                            y.getReviewDate().toString()));

                    sdnDataRepository.save(SdnData.builder()
                            .attendedSeller(null)
                            .countryMarketPlace(null)
                            .countrySeller(y.getCountrySeller())
                            .date(y.getReviewDate())
                            .partnerId(null)
                            .sdcScoring((Integer) getViolationsAndScoring(null, y).get(Constants.SCORING))
                            .sdcViolations((String) getViolationsAndScoring(null, y).get(Constants.VIOLATIONS))
                            .sellerName(null)
                            .skuNumber(y.getSku())
                            .typeOfPartner(null)
                            .typeOfSku(y.getSkuType())
                            .notes(null)
                            .type("Transactional")
                            .enforcementsDate(sdnEnforcements.get().getPublicationDate())
                            .enforcementsStatus(getEnforcementStatus(sdnEnforcements))
                            .lastModifiedTimestamp(LocalDateTime.now())
                            .url(y.getUrl())
                            .build());
                }
        );
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
