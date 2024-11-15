package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.model.CustomerOrder;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderMapper {

    public CustomerOrderDTO toDTO(CustomerOrder customerOrder) {
        return CustomerOrderDTO.builder()
                .id(customerOrder.getId())
                .status(customerOrder.getStatus())
                .client(customerOrder.getClient())
                .orderDate(customerOrder.getOrderDate())
                .orderItems(customerOrder.getOrderItems())
                .totalAmount(customerOrder.getTotalAmount())
                .build();
    }

    public CustomerOrder toEntity(CustomerOrderDTO customerOrderDTO) {
        return CustomerOrder.builder()
                .id(customerOrderDTO.getId())
                .client(customerOrderDTO.getClient())
                .orderDate(customerOrderDTO.getOrderDate())
                .orderItems(customerOrderDTO.getOrderItems())
                .status(customerOrderDTO.getStatus())
                .totalAmount(customerOrderDTO.getTotalAmount())
                .build();
    }
}

