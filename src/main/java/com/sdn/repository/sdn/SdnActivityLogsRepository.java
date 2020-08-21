package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdnActivityLogsRepository extends JpaRepository<SdnActivityLogs, Integer> {
}
