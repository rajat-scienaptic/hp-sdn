package com.sdn.repository;

import com.sdn.model.SdcViolations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SdcViolationsRepository extends JpaRepository<SdcViolations, Integer> {
}
