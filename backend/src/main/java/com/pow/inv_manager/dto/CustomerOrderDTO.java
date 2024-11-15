package com.pow.inv_manager.dto;

import com.pow.inv_manager.model.Client;
import com.pow.inv_manager.model.OrderItem;
import com.pow.inv_manager.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDTO {
    private Long id;
    private Client client;
    private Date orderDate;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private double totalAmount;
}
