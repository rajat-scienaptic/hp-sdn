package com.sdn.repository;

import com.sdn.model.SKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuRepository extends JpaRepository<SKU, Integer> {
    List<SKU> findAllByCountryId(int id);
}
