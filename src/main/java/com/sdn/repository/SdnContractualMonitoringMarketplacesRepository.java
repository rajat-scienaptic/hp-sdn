package com.sdn.repository;

import com.sdn.model.SdnContractualMonitoringMarketPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnContractualMonitoringMarketplacesRepository extends JpaRepository<SdnContractualMonitoringMarketPlaces, Integer> {
}
