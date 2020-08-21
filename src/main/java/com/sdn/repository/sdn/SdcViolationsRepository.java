package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdcViolations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdcViolationsRepository extends JpaRepository<SdcViolations, Integer> {
}
