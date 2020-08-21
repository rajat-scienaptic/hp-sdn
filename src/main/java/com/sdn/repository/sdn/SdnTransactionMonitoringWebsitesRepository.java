package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnTransactionMonitoringWebsites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnTransactionMonitoringWebsitesRepository extends JpaRepository<SdnTransactionMonitoringWebsites, Integer> {
}
