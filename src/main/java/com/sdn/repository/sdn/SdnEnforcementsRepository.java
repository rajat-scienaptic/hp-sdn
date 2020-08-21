package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnEnforcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnEnforcementsRepository extends JpaRepository<SdnEnforcements, Integer> {
    SdnEnforcements findBySellerNameAndSkuAndPublicationDate(String sellerName, String sku, String publicationDate);
}
