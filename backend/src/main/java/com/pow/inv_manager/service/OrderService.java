package com.pow.inv_manager.service;

import com.pow.inv_manager.dto.OrderDTO;
import com.pow.inv_manager.exception.OrderException;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws OrderException;

    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) throws OrderException;

    OrderDTO getOrder(Long orderId) throws OrderException;

    List<OrderDTO> getAllOrders() throws OrderException;
}
