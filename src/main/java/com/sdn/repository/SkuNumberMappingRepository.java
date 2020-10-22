package com.sdn.repository;

import com.sdn.model.SkuNumberMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuNumberMappingRepository extends JpaRepository<SkuNumberMapping, Integer> {
    SkuNumberMapping findBySkuNumber(String skuNumber);

    @Query(value = "Select distinct(s.skuNumber) from SkuNumberMapping s")
    List<String> getAllSkuNumbers();
}
