package com.pow.inv_manager.dto.mapper;

import com.pow.inv_manager.dto.OrderItemDTO;
import com.pow.inv_manager.model.Inventory;
import com.pow.inv_manager.model.OrderItem;
import com.pow.inv_manager.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemMapper {

    private final InventoryRepository inventoryRepository;

    public OrderItemMapper(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .inventoryId(orderItem.getInventory().getId())
                .inventoryName(orderItem.getInventory().getItem().getName())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .subtotal(orderItem.getSubtotal())
                .build();
    }

    public OrderItem toEntity(OrderItemDTO orderItemDTO) {
        Inventory inventory = inventoryRepository.findById(orderItemDTO.getInventoryId())
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + orderItemDTO.getInventoryId()));

        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .inventory(inventory)
                .quantity(orderItemDTO.getQuantity())
                .unitPrice(orderItemDTO.getUnitPrice())
                .subtotal(orderItemDTO.getSubtotal())
                .build();
    }
}
