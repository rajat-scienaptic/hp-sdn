package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnContractualMonitoringMarketPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnContractualMonitoringMarketplacesRepository extends JpaRepository<SdnContractualMonitoringMarketPlaces, Integer> {
}
