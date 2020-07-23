package com.sdn.repository;

import com.sdn.model.SdnContractualMonitoringGoogle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnContractualMonitoringGoogleRepository extends JpaRepository<SdnContractualMonitoringGoogle, Integer> {
}
