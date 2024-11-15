package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findFiltered(Optional<String> category, Optional<Long> supplierId, Optional<Double> minPrice, Optional<Double> maxPrice);
}
