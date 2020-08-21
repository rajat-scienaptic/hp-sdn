package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnContractualMonitoringGoogle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnContractualMonitoringGoogleRepository extends JpaRepository<SdnContractualMonitoringGoogle, Integer> {
}
