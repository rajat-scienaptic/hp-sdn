package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnEnforcements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SdnEnforcementsRepository extends JpaRepository<SdnEnforcements, Integer> {
    @Modifying
    @Transactional
    @Query(value="LOAD DATA LOCAL INFILE 'src/main/resources/pipeline/sdn_contractual_enforcements.csv' INTO TABLE hp_sdn.sdn_enforcements FIELDS TERMINATED BY ',' ENCLOSED BY '\\\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES (" +
            "url, platform, country_marketplace, seller_name, seller_id, seller_location, sku, sku_type, publication_date, notice_letter_sent_date, warning_letter_sent_date, status);", nativeQuery = true)
    void bulkLoadData();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hp_sdn.sdn_enforcements where url LIKE '%Aspose%';", nativeQuery = true)
    void deleteEmptyRows();
}
