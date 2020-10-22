package com.sdn.repository;

import com.sdn.model.SkuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuTypeRepository extends JpaRepository<SkuType, Integer> {
    @Query(value = "Select distinct(s.type) from SkuType s")
    List<String> getAllSkus();
}
