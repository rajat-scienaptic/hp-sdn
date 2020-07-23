package com.sdn.repository;

import com.sdn.model.SdnTransactionMonitoringWebsites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnTransactionMonitoringWebsitesRepository extends JpaRepository<SdnTransactionMonitoringWebsites, Integer> {
}
