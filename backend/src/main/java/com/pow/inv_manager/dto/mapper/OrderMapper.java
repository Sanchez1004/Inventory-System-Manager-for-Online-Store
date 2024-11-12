package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.OrderDTO;
import com.pow.inv_manager.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .build();
    }

    public Order toEntity(OrderDTO orderDTO) {
        return Order.builder()
                .id(orderDTO.getId())
                .build();
    }
}

