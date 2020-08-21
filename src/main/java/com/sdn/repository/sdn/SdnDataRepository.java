package com.sdn.repository.sdn;

import com.sdn.model.sdn.SdnData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface SdnDataRepository extends CrudRepository<SdnData, Integer>, JpaSpecificationExecutor<SdnData> {
    @Query(value = "select s from SdnData s where s.sdcScoring <= 10 and (s.date between :d1 and :d2)")
    List<SdnData> getSdnData(@Param("d1") Date d1, @Param("d2") Date d2);

    @Query(value = "select distinct(s.countryMarketPlace) from SdnData s")
    List<String> getCountries();

    @Query(value = "select distinct(s.countryMarketPlace) from SdnData s where s.type = :type")
    List<String> getCountriesByType(@Param("type") String type);

    @Query(value = "select distinct(s.typeOfSku) from SdnData s where s.typeOfSku is not null")
    List<String> getAllSkus();

    @Query(value = "select distinct(s.typeOfSku) from SdnData s where s.type = :type and s.typeOfSku is not null")
    List<String> getSkuByType(@Param("type") String type);

    @Query(value = "select distinct(s.typeOfSku) from SdnData s where s.countryMarketPlace = :countryMarketPlace and s.typeOfSku is not null")
    List<String> getSkuByCountryMarketPlace(@Param("countryMarketPlace") String countryMarketPlace);

    @Query(value = "select distinct(s.typeOfSku) from SdnData s where s.countryMarketPlace = :countryMarketPlace and s.type = :type and s.typeOfSku is not null")
    List<String> getSkuByCustomerMarketAndType(@Param("countryMarketPlace") String countryMarketPlace, @Param("type") String type);
}
