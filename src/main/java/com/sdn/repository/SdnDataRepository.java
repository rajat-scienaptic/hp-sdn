package com.sdn.repository;

import com.sdn.model.SdnData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SdnDataRepository extends CrudRepository<SdnData, Integer>, JpaSpecificationExecutor<SdnData> {
    @Query(value = "select s from SdnData s where s.sdcScoring <= 10 and (s.date between (curdate() - 7) and curdate())")
    List<SdnData> getSdnData();

    @Query(value = "select distinct(s.countryMarketPlace) from SdnData s")
    List<String> getCountries();

    @Query(value = "select distinct(s.countryMarketPlace) from SdnData s where s.type = :type")
    List<String> getCountriesByType(@Param("type") String type);

    @Query(value = "select distinct(s.typeOfSku) from SdnData s where s.countryMarketPlace = :countryMarketPlace")
    List<String> getSKUs(@Param("countryMarketPlace") String countryMarketPlace);
}
