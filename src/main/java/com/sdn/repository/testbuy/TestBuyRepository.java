package com.sdn.repository.testbuy;

import com.sdn.model.testbuy.TestBuy;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestBuyRepository extends CrudRepository<TestBuy, Integer>, JpaSpecificationExecutor<TestBuy> {
    @Query(value = "select t from TestBuy t")
    List<TestBuy> getTestBuyData();

    @Query(value = "select distinct(t.skuNumber) from TestBuy t where t.skuNumber is not null")
    List<String> getAllSkus();

    @Query(value = "select distinct(t.skuNumber) from TestBuy t where t.countryMarketPlace = :countryMarketPlace and t.skuNumber is not null")
    List<String> getSkuByCountryMarketPlace(@Param("countryMarketPlace") String countryMarketPlace);

    @Query(value = "select distinct(t.countryMarketPlace) from TestBuy t")
    List<String> getUniqueCountriesFromTestBuy();
}
