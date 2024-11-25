package com.pow.inv_manager.repository;

import com.pow.inv_manager.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByClientId(Long userId);

    void deleteCustomerOrderByClient_Id(Long clientId);

    @Query("SELECT co FROM CustomerOrder co JOIN FETCH co.orderItems oi JOIN FETCH co.client")
    List<CustomerOrder> findAllWithOrderItemsAndClient();
}
