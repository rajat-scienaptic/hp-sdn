package com.sdn.repository;

import com.sdn.model.SdnDataChangeLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdnDataChangeLogsRepository extends JpaRepository<SdnDataChangeLogs,Integer> {
  List<SdnDataChangeLogs> findAllByDataId(int id);
}
