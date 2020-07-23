package com.sdn.repository;

import com.sdn.model.SdnEnforcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnEnforcementsRepository extends JpaRepository<SdnEnforcements, Integer> {
    SdnEnforcements findBySellerNameAndSkuAndPublicationDate(String sellerName, String sku, String publicationDate);
}
