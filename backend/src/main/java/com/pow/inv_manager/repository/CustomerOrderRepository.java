package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByClientId(Long userId);
}
