package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnTransactionMonitoringMarketplaces;
import com.sdn.repository.pipeline.CustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SdnTransactionMonitoringMarketplacesRepository extends JpaRepository<SdnTransactionMonitoringMarketplaces, Integer>, CustomRepository {
    @Modifying
    @Transactional
    @Query(value="LOAD DATA LOCAL INFILE 'src/main/resources/pipeline/sdn_transactional_monitoring_marketplaces.csv' INTO TABLE hp_sdn.sdn_transaction_monitoring_marketplaces FIELDS TERMINATED BY ',' ENCLOSED BY '\\\"' LINES TERMINATED BY '\\r\\n' IGNORE 1 LINES;", nativeQuery = true)
    void bulkLoadData();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM hp_sdn.sdn_transaction_monitoring_marketplaces where url LIKE '%Aspose%';", nativeQuery = true)
    void deleteEmptyRows();
}
