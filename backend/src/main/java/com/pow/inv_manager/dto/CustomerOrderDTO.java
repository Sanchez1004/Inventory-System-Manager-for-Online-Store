package com.pow.inv_manager.dto;

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
    private Long clientId;
    private String clientCountry;
    private String clientCity;
    private String clientStreet;
    private String clientPhone;
    private String clientEmail;
    private String clientFirstName;
    private String clientLastName;
    private Date orderDate;
    private String status;
    private List<OrderItemDTO> orderItems;
    private double totalAmount;
}
