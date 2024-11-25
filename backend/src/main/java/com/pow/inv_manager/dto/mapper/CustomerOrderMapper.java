package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.CustomerOrderDTO;
import com.pow.inv_manager.dto.OrderItemDTO;
import com.pow.inv_manager.model.Client;
import com.pow.inv_manager.model.CustomerOrder;
import com.pow.inv_manager.model.OrderItem;
import com.pow.inv_manager.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderMapper {

    private final ClientRepository clientRepository;
    private final OrderItemMapper orderItemMapper;

    public CustomerOrderMapper(ClientRepository clientRepository, OrderItemMapper orderItemMapper) {
        this.clientRepository = clientRepository;
        this.orderItemMapper = orderItemMapper;
    }

    public CustomerOrderDTO toDTO(CustomerOrder customerOrder) {
        return CustomerOrderDTO.builder()
                .id(customerOrder.getId())
                .status(customerOrder.getStatus())
                .orderDate(customerOrder.getOrderDate())
                .orderItems(customerOrder.getOrderItems().stream()
                        .map(orderItem -> OrderItemDTO.builder()
                                .id(orderItem.getId())
                                .unitPrice(orderItem.getUnitPrice())
                                .subtotal(orderItem.getSubtotal())
                                .inventoryName(orderItem.getInventory().getItem().getName())
                                .inventoryId(orderItem.getInventory().getId())
                                .quantity(orderItem.getQuantity())
                                .build()).toList())
                .clientFirstName(customerOrder.getClient().getFirstName())
                .clientLastName(customerOrder.getClient().getLastName())
                .clientPhone(customerOrder.getClient().getPhone())
                .clientEmail(customerOrder.getClient().getEmail())
                .clientCountry(customerOrder.getClient().getAddress().getCountry())
                .clientCity(customerOrder.getClient().getAddress().getCity())
                .clientStreet(customerOrder.getClient().getAddress().getStreet())
                .clientId(customerOrder.getClient().getId())
                .totalAmount(customerOrder.getTotalAmount())
                .build();
    }

    public CustomerOrder toEntity(CustomerOrderDTO customerOrderDTO) {
        Client client = clientRepository.findById(customerOrderDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + customerOrderDTO.getClientId()));

        List<OrderItem> orderItems = customerOrderDTO.getOrderItems().stream()
                .map(orderItemMapper::toEntity)
                .toList();

        CustomerOrder customerOrder = CustomerOrder.builder()
                .id(customerOrderDTO.getId())
                .client(client)
                .orderDate(customerOrderDTO.getOrderDate())
                .status(customerOrderDTO.getStatus())
                .orderItems(orderItems)
                .build();

        customerOrder.calculateTotalAmount();

        return customerOrder;
    }
}

