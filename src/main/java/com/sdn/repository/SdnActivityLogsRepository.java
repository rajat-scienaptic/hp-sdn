package com.sdn.repository;

import com.sdn.model.SdnActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnActivityLogsRepository extends JpaRepository<SdnActivityLogs, Integer> {
}
