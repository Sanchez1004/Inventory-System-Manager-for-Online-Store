package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByEmail(String email);
}
