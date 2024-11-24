package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByInventory_Id(Long id);
}
