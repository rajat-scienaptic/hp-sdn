package com.sdn.repository.sdn;

import com.sdn.model.sdn.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    Region findByCustomerMarket(String customerMarket);
}
