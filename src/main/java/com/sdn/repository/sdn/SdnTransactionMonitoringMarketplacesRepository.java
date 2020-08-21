package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnTransactionMonitoringMarketplaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnTransactionMonitoringMarketplacesRepository extends JpaRepository<SdnTransactionMonitoringMarketplaces, Integer> {
}
