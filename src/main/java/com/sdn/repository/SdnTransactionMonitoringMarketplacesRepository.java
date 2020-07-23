package com.sdn.repository;

import com.sdn.model.SdnTransactionMonitoringMarketplaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnTransactionMonitoringMarketplacesRepository extends JpaRepository<SdnTransactionMonitoringMarketplaces, Integer> {
}
