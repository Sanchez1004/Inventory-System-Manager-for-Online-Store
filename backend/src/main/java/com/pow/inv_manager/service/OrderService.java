package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.exception.OrderException;
import com.pow.inv_manager.utils.OrderStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    CustomerOrderDTO createOrder(CustomerOrderDTO customerOrderDTO) throws OrderException;

    @Transactional
    CustomerOrderDTO updateOrder(Long orderId, CustomerOrderDTO customerOrderDTO) throws OrderException;

    CustomerOrderDTO getOrder(Long orderId) throws OrderException;

    List<CustomerOrderDTO> getAllOrders() throws OrderException;

    @Transactional
    void updateOrderStatus(Long orderId, OrderStatus status) throws OrderException;

    List<CustomerOrderDTO> getUserOrders(Long userId) throws OrderException;

    @Transactional
    void deleteOrder(Long orderId) throws OrderException;

    @Transactional
    void confirmOrder(Long orderId) throws OrderException;
}
