package com.sdn.repository.testbuy;

import com.sdn.model.testbuy.TestBuy;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TestBuyRepository extends CrudRepository<TestBuy, Integer>, JpaSpecificationExecutor<TestBuy> {
    @Query(value = "select t from TestBuy t")
    List<TestBuy> getTestBuyData();
}
