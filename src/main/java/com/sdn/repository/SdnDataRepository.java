package com.sdn.repository;

import com.sdn.model.SdnData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SdnDataRepository extends CrudRepository<SdnData, Integer>, JpaSpecificationExecutor<SdnData> {
    @Query(value = "select s from SdnData s where s.sdcScoring <= 10 and (s.date between (curdate() - 7) and curdate())")
    List<SdnData> getSdnData();
}
