package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
